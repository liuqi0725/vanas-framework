package com.liuqi.vanasframework.core.aspect;

import com.liuqi.vanasframework.core.adapter.VanasSaveLogAdapter;
import com.liuqi.vanasframework.core.adapter.VanasSaveOpLogAdapter;
import com.liuqi.vanasframework.core.annotation.SaveLog;
import com.liuqi.vanasframework.core.conf.norm.SaveLogType;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.lang.reflect.Method;


/**
 * 类说明 <br>
 *     定义一个切点,在使用注解方式使用 log 时调用函数
 * <p>
 * 构造说明 :
 * <pre>
 *   通过实现该类，子系统可以记录日志
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
@Aspect
@Component
@Log4j2
public class SaveLogAspect {

    /**
     * 第三方实现的 log 记录，通过 spring 注入
     * <pre>
     *  {@code
     *
     *
     *     <bean id="systemlog" class="com.fangxu.app.core.SystemLog" />
     *     <bean id="nunalog" class="com.liuqi.summer.core.log.SummerLogAspect">
     *         <property name="appLogs" ref="systemlog"/>
     *     </bean>
     *
     *     }
     * </pre>
     */
    private VanasSaveOpLogAdapter adapter = null;

    private ClassLoader classLoader = null;

    private final boolean logDebug = log.isDebugEnabled();

    public SaveLogAspect(VanasSaveOpLogAdapter adapter){
        this.adapter = adapter;

        try {
            this.classLoader = Thread.currentThread().getContextClassLoader();
            log.info("SaveLogAspect classLoader is [{}]",this.classLoader.toString());
        } catch (Throwable var3) {
            log.error("无法获取 ClassLoader");
        }
    }

    /**
     * 定义切点为所有使用 SummerLog 注解的时候
     */
    @Pointcut("@annotation(com.liuqi.vanasframework.core.annotation.SaveLog)")
    private void logPoint(){

    }

    /**
     * 事件完毕后的通知
     * @param jp 所有使用切点进入此切面
     * @param object 执行后的返回
     */
    @AfterReturning(pointcut = "logPoint()" , returning = "object")
    public void saveLog(JoinPoint jp, Object object){
        Assert.notNull(adapter , "无法完成数据库日志记录，切面缺失 VanasSaveLogAdapter 的实现类. 你需要在您的应用中去 实现 [VanasSaveLogAdapter] 适配器。");

        String targetClassName = jp.getTarget().getClass().getName();

        log.info("SaveLogAspect [{}] 记录日志.",targetClassName);

        //获取处理类
        Class targetClass = null;
        try {
            targetClass = this.getClassWithClassForName(targetClassName);
        } catch (ClassNotFoundException e) {
            try {
                targetClass = this.getClassWithClassLoader(targetClassName);
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
                log.error("SaveLogAspect load class `{}` with use `Class.forName` " +
                                "and `classLoader::{}` both Error . " +
                                "Make sure it can do this or check the class is exits .",
                        targetClassName, this.classLoader.toString(), ex);
            }
        }

        if(targetClass == null){
            return;
        }

        //通过反射找方法，并获取注解
        String methodName = jp.getSignature().getName();

        Method[] methods = targetClass.getMethods();
        // 获取request，response
        Object[] args = jp.getArgs();

        String operationDesc = "";
        SaveLogType operationType = null;

        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs!=null&&clazzs.length == args.length&&method.getAnnotation(SaveLog.class)!=null) {
                    operationDesc = method.getAnnotation(SaveLog.class).desc();
                    operationType = method.getAnnotation(SaveLog.class).type();
                    break;
                }
            }
        }

        if(logDebug){
            log.debug("SaveLogAspect [{}] type:{} , desc : {}",targetClassName , operationType,operationDesc);
        }

//        adapter.saveLog( targetClassName, methodName , operationType , operationDesc , object);

    }

    private Class getClassWithClassForName(String className) throws ClassNotFoundException {
        if(this.logDebug){
            log.debug("SaveLogAspect load class `{}` with Class.forName.", className);
        }
        return Class.forName(className);
    }

    private Class getClassWithClassLoader(String className) throws ClassNotFoundException{
        if(logDebug){
            log.debug("SaveLogAspect load class `{}` with `classLoader::{}`",className, this.classLoader.toString());
        }
        return this.classLoader.loadClass(className);
    }

}

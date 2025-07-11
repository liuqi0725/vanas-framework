package com.liuqi.vanasframework.core.aspect;

import com.liuqi.vanasframework.core.Vanas;
import com.liuqi.vanasframework.core.annotation.CacheWarmUp;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 类说明 <br>
 * <p>
 *     启动执行器 在 Spring 初始化完成后触发，扫描所有带 @CacheWarmUp 的方法 <br>
 *     SmartInitializingSingleton 所有bean 加载完后执行，不会破坏spring 生命周期，以及可能造成的 循环依赖
 * </p>
 *
 * @author : lau.Q
 * @version v1.0 , Create at 2025/6/18 15:44
 */
@Slf4j
@Aspect
@Component
public class CacheWarmUpAspect implements SmartInitializingSingleton {

    @Override
    public void afterSingletonsInstantiated() {
        // 容器加载完后，遍历所有 bean
        Map<String, Object> beans = Vanas.SpringContext.getBeansWithAnnotation(Component.class);

        for (Object bean : beans.values()) {
            Class<?> beanClass = AopUtils.getTargetClass(bean);
//            System.out.println("-------------------------------\n" + beanClass.getName());

            for (Method method : beanClass.getDeclaredMethods()) {
//                System.out.println("         " + method.getName() + "," + method.isAnnotationPresent(CacheWarmUp.class));

                if (method.isAnnotationPresent(CacheWarmUp.class)) {
                    try {
                        method.setAccessible(true);
                        method.invoke(bean);
                        log.info("Executed @CacheWarmUp method: {}.{}",
                                bean.getClass().getSimpleName(), method.getName());
                    } catch (Exception e) {
                        log.error("Failed to execute @CacheWarmUp method: {}.{}", bean.getClass().getSimpleName(), method.getName(), e);
                    }
                }
            }
        }
    }
}

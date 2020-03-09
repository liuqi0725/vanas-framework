package com.liuqi.vanasframework.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 类说明 <br>
 * <p>
 * 构造说明 :
 * <pre>
 *
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:39 PM 2020/2/27
 */
public class AnnotationUtil {

    private AnnotationUtil(){}

    public static AnnotationUtil getInstance(){
        return AnnotationUtilHook.instance;
    }

    private static class AnnotationUtilHook{
        private static AnnotationUtil instance = new AnnotationUtil();
    }

    /**
     * 验证 class 下是否有对应的注释
     * @param targetClass 目标类
     * @param annotationClass 注释类
     * @return 类是否带注释
     */
    public boolean validClass(Class targetClass , Class annotationClass){

        Annotation annotation = targetClass.getAnnotation(annotationClass);

        return annotation != null;
    }

    /**
     * 验证 class 下有指定注释的方法,并返回集合
     * @param targetClass 目标类
     * @param annotationClass 注释类
     * @return 带注释的方法
     */
    @SuppressWarnings("unchecked")
    public List<Method> validMethod(Class targetClass , Class annotationClass){

        Method[] methods = targetClass.getMethods();

        List<Method> methodList = new ArrayList<>();

        if(methods == null || methods.length == 0){
            return methodList;
        }

        Annotation annotation;

        for(Method m : methods){

            annotation = m.getAnnotation(annotationClass);

            if(annotation != null){
                methodList.add(m);
            }
        }

        return methodList;
    }

}

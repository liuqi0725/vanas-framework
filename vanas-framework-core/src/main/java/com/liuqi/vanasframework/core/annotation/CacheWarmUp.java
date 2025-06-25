package com.liuqi.vanasframework.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 类说明 <br>
 * <p>
 *     缓存加载注解。 带该注解的方法，启动会被加载
 * </p>
 *
 * @author : lau.Q
 * @version v1.0 , Create at 2025/6/17 10:37
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheWarmUp {
}

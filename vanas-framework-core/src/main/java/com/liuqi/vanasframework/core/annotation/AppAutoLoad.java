package com.liuqi.vanasframework.core.annotation;

import java.lang.annotation.*;

/**
 * 类说明 <br>
 *     随系统加载执行的注解
 *
 * @author : alexliu
 * @version v1.0 , Create at 7:58 PM 2020/2/27
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AppAutoLoad {

}

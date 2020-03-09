package com.liuqi.vanasframework.core.annotation;

import com.liuqi.vanasframework.core.conf.norm.SaveLogType;

import java.lang.annotation.*;

/**
 * 类说明 <br>
 *     自动记录日志注解，需要在自己的项目中 实现 VanasSaveLogAdapter
 *
 * @author : alexliu
 * @version v1.0 , Create at 1:13 PM 2020/3/6
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SaveLog {

    SaveLogType type();

    String desc() default "";

}

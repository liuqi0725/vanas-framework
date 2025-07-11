package com.liuqi.vanasframework.core;


import com.liuqi.vanasframework.core.conf.VanasCustomerConfig;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 类说明 <br>
 *     Vanas 全局控制, 需要在项目内 extends {@code AppWebConfig} 完成初始化
 *
 * @author : alexliu
 * @version v1.0 , Create at 3:27 PM 2020/3/7
 */
public class Vanas {

    public static ApplicationContext SpringContext;

    public static CacheManager cacheManager;

    public static VanasCustomerConfig customerConfig;

    public static RedisTemplate<String, Object> redisTemplate;
}

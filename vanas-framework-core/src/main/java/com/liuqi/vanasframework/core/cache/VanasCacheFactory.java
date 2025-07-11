package com.liuqi.vanasframework.core.cache;

import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;

/**
 * 类说明 <br>
 * <p>
 * </p>
 *
 * @author : lau.Q
 * @version v1.0 , Create at 2025/7/11 14:30
 */
public class VanasCacheFactory {

    public static CacheManager createCacheManager(String type, ApplicationContext context){
        switch (type) {
            case "redis":
                VanasRedisCacheManagerConfig redis = new VanasRedisCacheManagerConfig(context);
                return redis.getCacheManager();
            case "caffeine":
                VanasCaffeineCacheManagerConfig caffeine = new VanasCaffeineCacheManagerConfig();
                return caffeine.getCacheManager();
            default:
                throw new IllegalArgumentException("未知的缓存配置 '"+ type + "', 当前仅支持 redis、caffeine ");
        }
    }

}

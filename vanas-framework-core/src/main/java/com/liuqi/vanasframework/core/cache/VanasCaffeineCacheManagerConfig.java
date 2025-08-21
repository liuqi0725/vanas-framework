package com.liuqi.vanasframework.core.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * 类说明 <br>
 * <p>
 *     cache 配置
 * </p>
 *
 * @author : lau.Q
 * @version v1.0 , Create at 2025/5/6 15:07
 */
public class VanasCaffeineCacheManagerConfig implements VanasCacheManager{

    @Override
    @Primary
    public CacheManager getCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        Map<String, Duration> spec = new HashMap<>();
        spec.put(AppCacheNames.DATA_CACHE, Duration.ofHours(2));
        spec.put(AppCacheNames.SETTING_CACHE, Duration.ZERO);

        spec.forEach((name, duration) -> {
            Caffeine<Object, Object> builder = Caffeine.newBuilder()
                    .maximumSize(1000);
            if (!duration.isZero()) {
                builder.expireAfterWrite(duration);
            }
            cacheManager.registerCustomCache(name, builder.build());
        });

        return cacheManager;
    }
}

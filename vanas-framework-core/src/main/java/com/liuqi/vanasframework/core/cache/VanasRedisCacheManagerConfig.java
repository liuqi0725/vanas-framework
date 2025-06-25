package com.liuqi.vanasframework.core.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
@Configuration
@ConditionalOnClass(RedisConnectionFactory.class) // 确保 Redis 相关类存在
@ConditionalOnProperty(name = "vanas.cache-type", havingValue = "redis") // 根据配置决定
public class VanasRedisCacheManagerConfig {

//    @Autowired
//    private final RedisConnectionFactory redisConnectionFactory;
//
//    public VanasRedisCacheManagerConfig(RedisConnectionFactory redisConnectionFactory) {
//        this.redisConnectionFactory = redisConnectionFactory;
//    }


    @Bean
    @ConditionalOnBean(RedisConnectionFactory.class) // 仅在子项目配置了 Redis 连接后生效
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {

        // 配置redis 对象到json的序列化

        // 默认配置
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()));
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        // 创建缓存配置Map
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        // dataCache配置，缓存2小时
//        RedisCacheConfiguration dataCacheConfig = defaultCacheConfig.entryTtl(Duration.ofHours(2));

        // settingCache配置，永不过期
//        RedisCacheConfiguration settingCacheConfig = defaultCacheConfig.entryTtl(Duration.ZERO);

//        cacheConfigurations.put(AppCacheNames.DATA_CACHE, dataCacheConfig);
//        cacheConfigurations.put(AppCacheNames.SETTING_CACHE, settingCacheConfig);

        cacheConfigurations.put(AppCacheNames.DATA_CACHE, defaultCacheConfig.entryTtl(Duration.ofHours(2)));
        cacheConfigurations.put(AppCacheNames.SETTING_CACHE, defaultCacheConfig.entryTtl(Duration.ZERO));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }

}

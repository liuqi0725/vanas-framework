package com.liuqi.vanasframework.core.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuqi.vanasframework.core.Vanas;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
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
public class VanasRedisCacheManagerConfig implements VanasCacheManager{

    private final ApplicationContext applicationContext;

    public VanasRedisCacheManagerConfig(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    /**
     * 配置缓存管理器 配置好后， @Cacheable、@CacheEvict 等注解，也会使用 redis
     * @return redis cache springboot manager
     */
    @Override
    public CacheManager getCacheManager() {

        RedisConnectionFactory redisConnectionFactory = applicationContext.getBean(RedisConnectionFactory.class);

        ObjectMapper objectMapper = Vanas.SpringContext.getBean(ObjectMapper.class);

        // 配置redis 对象到json的序列化
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer(objectMapper);
        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        // 默认配置
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringSerializer))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new JdkSerializationRedisSerializer()));
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer));

        // 创建缓存配置Map
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        // dataCache配置，缓存2小时
        cacheConfigurations.put(AppCacheNames.DATA_CACHE, defaultCacheConfig.entryTtl(Duration.ofHours(2)));
        // settingCache配置，永不过期
        cacheConfigurations.put(AppCacheNames.SETTING_CACHE, defaultCacheConfig.entryTtl(Duration.ZERO));

        // 创建redisTemplate
        this.createTemplate(redisConnectionFactory, stringSerializer, jsonSerializer);

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }

    /**
     * 设置 redis
     * @param connectionFactory 工厂类
     */
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory){
//
//        return this.createTemplate(connectionFactory);
//    }

    private void createTemplate(final RedisConnectionFactory connectionFactory,
                                StringRedisSerializer stringSerializer, GenericJackson2JsonRedisSerializer jsonSerializer){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        // 支持redis事务
        redisTemplate.setEnableTransactionSupport(true);

        // key 和 hash key 用字符串
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);

        // value 和 hash value 用 JSON
        redisTemplate.setValueSerializer(jsonSerializer);
        redisTemplate.setHashValueSerializer(jsonSerializer);

        redisTemplate.afterPropertiesSet();

        Vanas.redisTemplate = redisTemplate;
    }


}

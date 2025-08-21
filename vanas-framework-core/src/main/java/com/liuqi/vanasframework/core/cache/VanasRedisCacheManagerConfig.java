package com.liuqi.vanasframework.core.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liuqi.vanasframework.core.Vanas;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

        ObjectMapper objectMapper = (ObjectMapper) Vanas.SpringContext.getBean("redisObjectMapper");

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

        // 打印所有缓存配置（调试用）
//        cacheConfigurations.forEach((name, config) -> {
//            System.out.println("VANAS REDIS CACHE SET: " + name + ", TTL: " + config.getTtl().getSeconds() + "秒");
//        });

        RedisCacheManager redisCacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .initialCacheNames(cacheConfigurations.keySet())    // ✅ 配置缓存key
                .cacheDefaults(defaultCacheConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
//                .transactionAware() // 支持事务
                .build();

        // RedisCacheManager 的 afterPropertiesSet() 方法里会把你设置的 initialCacheConfigurations 拷贝到内部结构
        // （如 cacheConfigurations、cacheMap）中，并初始化缓存实例。如果你不调用这个方法，getCache() 获取到的就是一个
        // 空配置的新缓存实例，ttl 等都不会生效
        //
        // 如果是通过 @bean 自动装配则无需如此，  afterPropertiesSet 主要用于通过new等方式创建。
        redisCacheManager.afterPropertiesSet();

        // 创建redisTemplate
        this.createTemplate(redisConnectionFactory, stringSerializer, jsonSerializer);

        return redisCacheManager;
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

package com.liuqi.vanasframework.util;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 类说明 <br>
 * <p>
 *  Redis 工具类，封装常用操作（String/List/Set/Hash/ZSet/事务）。
 *  所有方法均基于 Spring Data Redis 的 {@link RedisTemplate}。
 * </p>
 *
 * @author : lau.Q
 * @version v1.0 , Create at 2025/6/17 14:39
 */
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class VanasRedisCacheUtil {

    private final RedisTemplate redisTemplate;

    private final ObjectMapper objectMapper; // Spring Boot 默认已经配置好的 ObjectMapper

    public VanasRedisCacheUtil(RedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 判断key是否存在
     * @param key key
     * @return 返回bool
     */
    public boolean keyExists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    public <T> void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     *
     */
    public void expire(final String key, final long timeout) {
        expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     *
     */
    public void expire(final String key, final long timeout, final TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 获取对应key 剩余时限(秒)
     * @param key 键
     * @return 返回超时时间
     */
    public long getExpire(final String key) {
        Optional<Long> expire = Optional.ofNullable(redisTemplate.getExpire(key));
        return expire.orElse(0L); // 如果过期时间为null，则返回0，表示键不存在或没有设置过期时间
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    public <T> T getCacheObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    public <T> T getCacheObject(final String key, Class<T> clazz) {
        JSONObject jsonObject = getCacheObject(key);
        if(jsonObject == null){
            return null;
        }
        return jsonObject.toJavaObject(clazz);
    }

    /**
     * 删除单个对象
     * @param key key
     * @return bool
     */
    public boolean rmObject(final String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    /**
     * 删除集合对象
     * @param collection 多个对象
     * @return 数值
     */
    public long rmObject(final Collection collection) {
        if (CollectionUtils.isEmpty(collection)) {
            return 0L;
        }
        Long deletedCount = redisTemplate.delete(collection);
        return deletedCount != null ? deletedCount : 0L;
    }

    /**
     * 缓存List数据 (右插入)
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setCacheList(final String key, final List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 缓存list数据 (右插入)
     * @param key 键
     * @param data 数据
     * @return long
     * @param <T> 泛型数据
     */
    public <T> long setCacheList(final String key, final T data) {
        Long count = redisTemplate.opsForList().rightPush(key, data);
        return count == null ? 0 : count;
    }

    /**
     * 缓存list数据 (左插入)
     * @param key key
     * @param data  数据
     * @param <T> 泛型数据
     */
    public <T> void setCacheListOnLeft(final String key, final List<T> data) {
        redisTemplate.opsForList().leftPushAll(key, data);
    }

    /**
     * 缓存list数据 (左插入)
     * @param key key
     * @param data 数据
     * @param <T> 泛型数据
     */
    public <T> void setCacheListOnLeft(final String key, final T data) {
        redisTemplate.opsForList().leftPush(key, data);
    }

    /**
     * 清空列表
     * @param key key
     */
    public void clearCacheList(final String key) {
        redisTemplate.opsForList().trim(key, 1, 0); // 将列表截取为一个空列表
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(final String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    public <T> List<T> getCacheList(final String key, int size) {
        // 0 开始，所以 size -1
        return redisTemplate.opsForList().range(key, 0, size-1);
    }

    public <T> void rmCacheList(final String key, int size) {
        redisTemplate.opsForList().trim(key, size, size-1);
    }

    /**
     * 获取缓存的list对象分页数据
     * @param key key
     * @param page 页码
     * @param pageSize 每页数量
     * @return 分页数据
     * @param <T> 泛型
     */
    public <T> List<T> getCacheListPageData(final String key, int page, int pageSize) {
        int start = (page - 1) * pageSize;
        int end = start + pageSize - 1;
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     *  获取长度
     * @param key key
     * @return long
     */
    public long getListLength(final String key) {

        Long size = redisTemplate.opsForList().size(key);
        return size != null ? size : 0L; // 显式处理可能的 null
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        @SuppressWarnings("unchecked")
        T[] array = (T[]) dataSet.toArray();
        setOperation.add(array);
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key key
     * @return set
     */
    public <T> Set<T> getCacheSet(final String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     *
     * @param key key
     * @param dataMap map
     */
    public <T> void setCacheMap(final String key, final Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key key
     * @return map
     */
    public <T> Map<String, T> getCacheMap(final String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getCacheMapValue(final String key, final String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    public <T> void setCacheMapValue(final String key, final String hKey, final T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 删除Hash中的数据
     *
     * @param key Redis键
     * @param hkey Hash键
     */
    public void rmCacheMapValue(final String key, final String hkey) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, hkey);
    }

    /**
     * 删除hash表上的字段
     * @param key 表 key
     * @param hashKey hash key
     */
    public void rmCacheMapByhKey(final String key, final Object... hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 插入 zset
     * @param key key
     * @param value 值
     * @param score 设置分值。分值可以相同，通常可以用于过期时间计算
     * @param <T> 泛型
     */
    public <T> void setZSet(final String key, final T value, long score){
        redisTemplate.opsForZSet().add(key, value, score);
    }

    public <T> int getZSetScore(final String key, final T value){
        Optional<Double> expire = Optional.ofNullable(redisTemplate.opsForZSet().score(key, value));
        return expire.map(score -> Math.max(score.intValue(), 0)).orElse(0);
    }

    public <T> Set<T> getZSetList(final String key, final int start, final int end){
        ZSetOperations<String, T> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.range(key, start, end);
    }

    public <T> Set<T> getZSetListByScore(final String key, final long minScore, final long maxScore) {
        ZSetOperations<String, T> zSetOperations = redisTemplate.opsForZSet();
        return zSetOperations.rangeByScore(key, minScore, maxScore);
    }

    public <T> void rmZSetData(final String key, final T value){
        redisTemplate.opsForZSet().remove(key, value);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 原子递增（默认步长为1）
     * @param key redis key
     * @return 自增后的值
     */
    public long increment(String key) {
        Long value = redisTemplate.opsForValue().increment(key);
        if (value == null) {
            throw new IllegalStateException("Redis INCR 操作返回 null，可能 Redis 未连接或 key 序列化失败");
        }
        return value;
    }

    /**
     * 原子递增（指定步长）
     * @param key redis key
     * @param delta 自增步长
     * @return 自增后的值
     */
    public long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 原子递增（指定步长）
     * @param key redis key
     * @param expireTime 过期时间
     * @param unit 格式
     * @return 自增后的值
     */
    public long increment(String key, long expireTime, TimeUnit unit) {
        long value = increment(key);
        if (value == 1L) {
            expire(key, expireTime, unit);
        }
        return value;
    }

    /**
     * redis 事务开关， 打开监视. 一般与 exec 配合执行
     * @param key key
     */
    public void watch(final String key) {
        redisTemplate.watch(key);
    }

    /**
     * 开启多任务的事务
     */
    public void multi() {
        redisTemplate.multi();
    }

    /**
     * exec 执行事务， 使用watch打开事务，并执行增删改后，使用exec 执行
     */
    public List<Object> exec() {
        return redisTemplate.exec();
    }

    /**
     * 回滚事务
     */
    public void discard(){
        redisTemplate.discard();
    }
}

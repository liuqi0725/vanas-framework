package com.liuqi.vanasframework.core.cache.key;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 类说明 <br>
 * <p>
 *     缓存键注册中心  子类继承
 * </p>
 *
 * @author : lau.Q
 * @version v1.0 , Create at 2025/8/20 11:27
 */
public class CacheKeyRegistry {

    private static final Map<String, String> REGISTERED_KEYS = new HashMap<>();

    public static void register(String cacheName, String key) {
        String fullKey = cacheName + "::" + key;
        if (REGISTERED_KEYS.containsKey(fullKey)) {
            throw new IllegalArgumentException("重复的缓存key，无法注册。 key:  " + fullKey);
        }
        REGISTERED_KEYS.put(key, fullKey);
    }

    public static String get(String cacheName, String key) {
        return REGISTERED_KEYS.get(cacheName + "::" + key);
    }

    public static Collection<String> getAll() {
        return Collections.unmodifiableCollection(REGISTERED_KEYS.values());
    }
}

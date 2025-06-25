package com.liuqi.vanasframework.core.util;

import com.liuqi.vanasframework.core.cache.AppCacheNames;

/**
 * 类说明 <br>
 * <p>
 * </p>
 *
 * @author : lau.Q
 * @version v1.0 , Create at 2025/6/18 10:48
 */
public class FastUtils {

    public static String getRedisDataCacheKey(String dataKey){
        return getRedisCacheKey(AppCacheNames.DATA_CACHE, dataKey);
    }

    public static String getRedisSettingCacheKey(String dataKey){
        return getRedisCacheKey(AppCacheNames.SETTING_CACHE, dataKey);
    }

    public static String getRedisCacheKey(String cacheName, String dataKey){
        return cacheName + ":" + dataKey;
    }
}

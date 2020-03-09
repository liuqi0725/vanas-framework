package com.liuqi.vanasframework.util;


import com.liuqi.vanasframework.core.Vanas;

/**
 * 类说明 <br>
 *     cache 工具类
 *
 * @author : alexliu
 * @version v1.0 , Create at 5:28 PM 2020/3/7
 */
public class CacheUtil {

    public static void setVal(String cacheName, Object key , Object val){
        Vanas.cacheManager.getCache(cacheName).put(key , val);
    }

    public static Object getVal(String cacheName, Object key){
        return Vanas.cacheManager.getCache(cacheName).get(key);
    }

}

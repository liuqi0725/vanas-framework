package com.liuqi.vanasframework.util;


import com.liuqi.vanasframework.core.Vanas;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 类说明 <br>
 *     cache 工具类
 *
 * @author : alexliu
 * @version v1.0 , Create at 5:28 PM 2020/3/7
 */
public class CacheUtil {

    public static synchronized void setVal(String cacheName, Object key , Object val){
        Objects.requireNonNull(Vanas.cacheManager.getCache(cacheName)).put(key , val);
    }

    public static Object getVal(String cacheName, Object key){
        return getCacheData(cacheName,key);
    }

    public static <T> T getVal(String cacheName, Object key , Class<T> clazz){
        Object obj = getCacheData(cacheName,key);
        return TypeUtil.cast(obj,clazz);
    }

    public static <T> List<T> getValAsList(String cacheName, Object key , Class<T> clazz){
        List<T> var1 = new ArrayList<>();
        Object obj = getCacheData(cacheName,key);
        List var2 = TypeUtil.cast(obj,List.class);
        for(Object var3 : var2){
            var1.add(TypeUtil.cast(var3,clazz));
        }
        return var1;
    }




    private static Object getCacheData(String cacheName, Object key){
        return Objects.requireNonNull(Objects.requireNonNull(Vanas.cacheManager.getCache(cacheName)).get(key)).get();
    }
}

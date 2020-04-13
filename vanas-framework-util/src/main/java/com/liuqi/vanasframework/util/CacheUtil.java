package com.liuqi.vanasframework.util;


import com.liuqi.vanasframework.core.Vanas;
import com.liuqi.vanasframework.core.exception.AppException;

import javax.cache.Cache;
import javax.cache.CacheManager;
import java.util.*;

/**
 * 类说明 <br>
 *     cache 工具类
 *
 * @author : alexliu
 * @version v1.0 , Create at 5:28 PM 2020/3/7
 */
public class CacheUtil {

    /**
     * 不允许 new
     */
    private CacheUtil(){}

    public static CacheUtil getInstance(){
        return CacheUtilHook.instance;
    }

    private static class CacheUtilHook {
        private static CacheUtil instance = new CacheUtil();
    }

    /**
     * 设置缓存 【同步方法】
     * @param cacheName 缓存名称
     * @param key 存储对象 key
     * @param val 存储对象 值
     * @throws NullPointerException 空指针
     */
    public synchronized void setVal(String cacheName, Object key , Object val){
        Objects.requireNonNull(Vanas.cacheManager.getCache(cacheName)).put(key , val);
    }

    /**
     * 删除缓存<br>
     * 慎用！慎用！慎用！慎用！慎用！慎用！慎用！
     * @param cacheName 缓存名称
     * <pre>
     *     比如 缓存中存在 <br>
     *     {<br>
     *         "cache1" : [<br>
     *              {"item1":java.util.List},<br>
     *              {"item2":java.lang.String},<br>
     *         ],<br>
     *         <br>
     *         "cache2" : [<br>
     *              {"item1":"hello"},<br>
     *              {"item2":"world"},<br>
     *         ],<br>
     *     }<br>
     *
     *     {@code CacheUtil.getInstance().remove("cache2"); } 将删除 cache2 这个缓存。
     * </pre>
     */
    public synchronized void remove(String cacheName){

        try {
            CacheManager manager = (CacheManager)Vanas.cacheManager.getCacheManager();
            if(manager == null){
                throw new AppException("没有找到 cacheManager");
            }
            if(getCache(manager,cacheName) != null){
                manager.destroyCache(cacheName);
            }
        }catch (NullPointerException e){
        }
    }

    /**
     * 删除所有缓存 <br>
     * 慎用！慎用！慎用！慎用！慎用！慎用！慎用！
     * 原理同 {@link #remove(String)}
     */
    public synchronized void removeAll(){
        try {
            CacheManager manager = (CacheManager)Vanas.cacheManager.getCacheManager();
            if(manager == null){
                throw new AppException("没有找到 cacheManager");
            }

            Iterable<String> cacheNames = manager.getCacheNames();
            for(String name : cacheNames){
                manager.destroyCache(name);
            }

        }catch (NullPointerException e){
        }
    }

    /**
     * 删除缓存下所有存储内容
     * @param cacheName 缓存名称
     * <pre>
     *     比如 缓存中存在 <br>
     *     {<br>
     *         "cache1" : [<br>
     *              {"item1":java.util.List},<br>
     *              {"item2":java.lang.String},<br>
     *         ],<br>
     *         <br>
     *         "cache2" : [<br>
     *              {"item3":"hello"},<br>
     *              {"item4":"world"},<br>
     *         ],<br>
     *     }<br>
     *
     *     {@code CacheUtil.getInstance().removeCacheItemAll("cache2"); } 将删除 cache2 这个缓存下 item3,item4...等所有数据。单保留缓存 cache2<b>内容</b>。
     * </pre>
     */
    public synchronized void removeCacheItemAll(String cacheName){
        try {
            CacheManager manager = (CacheManager)Vanas.cacheManager.getCacheManager();
            if(manager == null){
                throw new AppException("没有找到 cacheManager");
            }
            Cache cache = manager.getCache(cacheName);
            cache.removeAll();
        }catch (NullPointerException e){
        }
    }



    /**
     * 删除缓存下某一个存储数据
     * @param cacheName 缓存名称
     * @param key 存储对象 key
     * <pre>
     *     比如 缓存中存在 <br>
     *     {<br>
     *         "cache1" : [<br>
     *              {"item1":java.util.List},<br>
     *              {"item2":java.lang.String},<br>
     *         ],<br>
     *         <br>
     *         "cache2" : [<br>
     *              {"item3":"hello"},<br>
     *              {"item4":"world"},<br>
     *         ],<br>
     *     }<br>
     *
     *     {@code CacheUtil.getInstance().removeCacheItem("cache2","item3"); } 将删除 cache2 这个缓存下 item3 的数据。。
     * </pre>
     */
    public synchronized void removeCacheItem(String cacheName , String key){

        try {
            CacheManager manager = (CacheManager)Vanas.cacheManager.getCacheManager();
            if(manager == null){
                throw new AppException("没有找到 cacheManager");
            }
            if(getCache(manager,cacheName) != null){
                manager.getCache(cacheName).remove(key);
            }
        }catch (NullPointerException e){
        }
    }

    /**
     * 添加一条数据到集合缓存数据中<br>
     * <p>
     * 通过泛型找到缓存中存储的集合，并向存储数据添加一个内容。<br>
     * 注意：
     *      1. 缓存对象必须是个集合
     *      2. 泛型必须正确
     * </p>
     * @param cacheName 缓存名称
     * @param key 存储对象 key
     * @param classz  缓存对象的 class
     * @param item  需要添加到缓存的存储对象
     * @param <T> 泛型
     */
    public <T> void addItemInCacheData(String cacheName, Object key , Class<T> classz , T item){
        List<T> cacheDataList = this.getValAsList(cacheName , key , classz);
        cacheDataList.add(item);
        this.setVal(cacheName , key , cacheDataList);
    }

    /**
     * 删除集合缓存数据中的某一个值 【需重写 equals 方法】
     * @param cacheName 缓存名称
     * @param key 存储对象 key
     * @param classz  缓存对象的 class
     * @param item 需要从缓存中删除的的对象
     * @param <T> 泛型
     * <pre>
     *     注意：<br>
     *     对比对象是否是同一个对象，是通过 equals 方法。你需要自己重新 equals 函数
     * </pre>
     */
    public <T> void removeItemFromCacheData(String cacheName, String key , Class<T> classz , T item){
        List<T> cacheDataList = this.getValAsList(cacheName,key,classz);
        for(int i=0; i<cacheDataList.size(); i++){
            if(cacheDataList.get(i).equals(item)){
                cacheDataList.remove(i);
                break;
            }
        }
        this.setVal(cacheName,key,cacheDataList);
    }


    /**
     * 替换集合缓存数据中的某一个值 【需重写 equals 方法】<br>
     * @param cacheName 缓存名称
     * @param key 存储对象 key
     * @param classz  缓存对象的 class
     * @param replaceItem 需要替换的对象，必须和集合泛型类一个类型
     * @param <T> 泛型
     * <pre>
     *     比如 缓存中存在 <br>
     *     {<br>
     *         "user-cache" : [<br>
     *              {"login-user":<br>
     *                  [<br>
     *                      {"name":"lilei","age":18},<br>
     *                      {"name":"hanmeimei","age":18},<br>
     *                  ]<br>
     *              }<br>
     *     }<br>
     *
     *     当你修改了 hanmeimei 不许重新设置集合，只需要传入修改后的 hanmeimei 即可。<br>
     *     {@code CacheUtil.getInstance().replace("user-cache","login-user", User.class , user); }<br>
     *
     *     注意：<br>
     *     对比对象是否是同一个对象，是通过 equals 方法。你需要自己重新 equals 函数
     * </pre>
     */
    public synchronized <T> void replace(String cacheName, Object key , Class<T> classz , T replaceItem){
        List<T> cacheDataList = this.getValAsList(cacheName , key , classz);

        for(int i=0; i<cacheDataList.size(); i++){
            if(cacheDataList.get(i).equals(replaceItem)){
                cacheDataList.set(i,replaceItem);
                break;
            }
        }
        this.setVal(cacheName , key , cacheDataList);
    }


    /**
     * 获取缓存对象
     * @param cacheName 缓存名称
     * @param key 存储对象 key
     * @return object 在外部转化类型
     * <pre>
     *     {@code User user = (User)CacheUtil.getInstance().getVal("cacheName","user-1"); }
     * </pre>
     */
    public Object getVal(String cacheName, Object key){
        return getCacheData(cacheName,key);
    }

    /**
     * 获取缓存对象
     * @param cacheName 缓存名称
     * @param key 存储对象 key
     * @param classz  缓存对象的 class
     * @param <T> 泛型
     * @return 泛型对象
     * <pre>
     *     {@code User user = CacheUtil.getInstance().getVal("cacheName","user-1", User.class); }
     * </pre>
     */
    public <T> T getVal(String cacheName, Object key , Class<T> classz){
        Object obj = getCacheData(cacheName,key);
        return TypeUtil.cast(obj,classz);
    }

    /**
     * 获取缓存的集合对象
     * @param cacheName 缓存名称
     * @param key 存储对象 key
     * @param classz  缓存对象的 class
     * @param <T> 泛型
     * @return 泛型集合
     * <pre>
     *     {@code List<User> userList = CacheUtil.getInstance().getValAsList("cacheName","user-1", User.class); }
     * </pre>
     */
    public <T> List<T> getValAsList(String cacheName, Object key , Class<T> classz){
        List<T> var1 = new ArrayList<>();
        Object obj = getCacheData(cacheName,key);
        List var2 = TypeUtil.cast(obj,List.class);
        for(Object var3 : var2){
            var1.add(TypeUtil.cast(var3,classz));
        }
        return var1;
    }

    /**
     * 获取缓存的所有数据 【一次不能取超过 200 条】
     * @param begin 开始的下标
     * @param end   结束的下标
     * @return 缓存的 map
     */
    public Map<String,List<Cache.Entry>> getCacheList(int begin , int end){

        if((end - begin) > 200){
            throw new AppException("获取缓存数据，超过一次性获取最大次数 200.");
        }

        Map<String,List<Cache.Entry>> cacheData = new HashMap<>();

        CacheManager manager = (CacheManager)Vanas.cacheManager.getCacheManager();

        if(manager == null){
            throw new AppException("没有找到 cacheManager");
        }
        Iterable<String> cacheNames = manager.getCacheNames();

        Cache cache;

        for(String name : cacheNames){
            cache = getCache(manager,name);
            if(cache == null){
                continue;
            }

            // 获取所有缓存 key
            cacheData.put(name,getCacheItemList(cache , begin, end));
        }

        return cacheData;
    }


    private List<Cache.Entry> getCacheItemList(Cache cache , int begin,int end){

        List<Cache.Entry> cacheItemList = new ArrayList<>();
        int index = 0;
        for (Cache.Entry<Object, Object> objectObjectEntry : (Iterable<Cache.Entry<Object, Object>>) cache) {

            if(index < begin){
                index++;
                continue;
            }

            cacheItemList.add(objectObjectEntry);

            if(index == end){
                break;
            }
            index++;
        }
        return cacheItemList;
    }

    private Cache getCache(CacheManager manager , String cacheName){
        return Objects.requireNonNull(Objects.requireNonNull(manager.getCache(cacheName)));
    }

    private Object getCacheData(String cacheName, Object key){
        return Objects.requireNonNull(Objects.requireNonNull(Vanas.cacheManager.getCache(cacheName)).get(key)).get();
    }

}

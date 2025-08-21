package com.liuqi.vanasframework.util;


import com.liuqi.vanasframework.core.Vanas;
import com.liuqi.vanasframework.util.lang.manager.ArrayListCacheDataManager;
import com.liuqi.vanasframework.util.lang.manager.LinkedListCacheDataManager;
import com.liuqi.vanasframework.util.lang.manager.SetCacheDataManager;
import org.springframework.cache.Cache;

import java.io.Serializable;
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
     * 获取 cache
     * @param cacheName 缓存名称
     * @return 缓存对象
     */
    private Cache getCache(String cacheName){
        Objects.requireNonNull(Objects.requireNonNull(Vanas.cacheManager));
        return Vanas.cacheManager.getCache(cacheName);
    }

    /**
     * 设置缓存 【同步方法】
     * @param cacheName 缓存名称
     * @param key 存储对象 key
     * @param val 存储对象 值
     * @throws NullPointerException 空指针
     */
    public void setVal(String cacheName, Object key , Object val){
        Objects.requireNonNull(Vanas.cacheManager.getCache(cacheName)).put(key , val);
    }

    /**
     * 判断 key 是否存在
     * @param cacheName
     * @param key
     * @return
     */
    public boolean isKeyPresent(String cacheName, Object key){
        try {
            CacheUtil.getInstance().getVal(cacheName,key);
        }catch (NullPointerException e){
            return false;
        }
        return true;
    }

    /**
     * 删除指定key值
     * @param cacheName 缓存名称
     * @param key 存储对象 key
     * @throws NullPointerException 空指针
     */
    public void evict(String cacheName, Object key){
        Objects.requireNonNull(Vanas.cacheManager.getCache(cacheName)).evictIfPresent(key);
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
     * 获取缓存中的 arrayList 集合对象管理器
     * @param cacheName 缓存名称
     * @param key 存储对象 key
     * @param <T> 泛型
     * @return ArrayList 缓存数据管理器。包含 get、add、remove、replace 方法。修改后直接修改缓存
     * <p>
     *     ArrayListCacheDataManager&lt;User&gt; listCacheManager = CacheUtil.getInstance().getListCacheDataManager("cacheName","userList");
     * </p>
     */
    public <T extends Serializable> ArrayListCacheDataManager<T> getArrayListCacheDataManager(String cacheName, Object key){
        return new ArrayListCacheDataManager<T>(cacheName,key);
    }

    /**
     * 获取缓存中的 HashSet 集合对象管理器
     * @param cacheName 缓存名称
     * @param key 存储对象 key
     * @param <T> 泛型
     * @return HashSet 缓存数据管理器。包含 get、add、remove、replace 方法。修改后直接修改缓存
     * <p>
     *     SetCacheDataManager&lt;User&gt; setCacheManager = CacheUtil.getInstance().getSetCacheDataManager("cacheName","userList");
     * </p>
     */
    public <T extends Serializable> SetCacheDataManager<T> getSetCacheDataManager(String cacheName, Object key){
        return new SetCacheDataManager<T>(cacheName,key);
    }

    /**
     * 获取缓存中的 LinkedList 集合对象管理器
     * @param cacheName 缓存名称
     * @param key 存储对象 key
     * @param <T> 泛型
     * @return LinkedList 缓存数据管理器。包含 get、add、remove、replace 方法。修改后直接修改缓存
     * <p>
     *     LinkedListCacheDataManager&lt;User&gt; linkedListCacheManager = CacheUtil.getInstance().getLinkedListCacheDataManager("cacheName","userList");
     * </p>
     */
    public <T extends Serializable> LinkedListCacheDataManager<T> getLinkedListCacheDataManager(String cacheName, Object key){
        return new LinkedListCacheDataManager<T>(cacheName,key);
    }

    /**
     * 【已废弃】
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
     * @deprecated 已废弃 推荐使用:<br>
     *     {@link #getArrayListCacheDataManager(String, Object)}<br>
     *     {@link #getSetCacheDataManager(String, Object)}<br>
     *     {@link #getLinkedListCacheDataManager(String, Object)}<br>
     */
    @Deprecated
    public <T> void addItemInCacheData(String cacheName, Object key , Class<T> classz , T item){
        List<T> cacheDataList = this.getValAsList(cacheName , key , classz);
        cacheDataList.add(item);
        this.setVal(cacheName , key , cacheDataList);
    }

    /**
     * 【已废弃】
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
     * @deprecated 已废弃 推荐使用:<br>
     *     {@link #getArrayListCacheDataManager(String, Object)}<br>
     *     {@link #getSetCacheDataManager(String, Object)}<br>
     *     {@link #getLinkedListCacheDataManager(String, Object)}<br>
     */
    @Deprecated
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
     * 【已废弃】
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
     * @deprecated 已废弃 推荐使用:<br>
     *     {@link #getArrayListCacheDataManager(String, Object)}<br>
     *     {@link #getSetCacheDataManager(String, Object)}<br>
     *     {@link #getLinkedListCacheDataManager(String, Object)}<br>
     */
    @Deprecated
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
     * 【已废弃】
     * 获取缓存的集合对象
     * @param cacheName 缓存名称
     * @param key 存储对象 key
     * @param classz  缓存对象的 class
     * @param <T> 泛型
     * @return 泛型集合
     * <pre>
     *     {@code List<User> userList = CacheUtil.getInstance().getValAsList("cacheName","user-1", User.class); }
     * </pre>
     * @deprecated 已废弃 推荐使用:<br>
     *     {@link #getArrayListCacheDataManager(String, Object)}<br>
     *     {@link #getSetCacheDataManager(String, Object)}<br>
     *     {@link #getLinkedListCacheDataManager(String, Object)}<br>
     */
    @Deprecated
    public <T> List<T> getValAsList(String cacheName, Object key , Class<T> classz){
        List<T> var1 = new ArrayList<>();
        Object obj = getCacheData(cacheName,key);
        List var2 = TypeUtil.cast(obj,List.class);
        for(Object var3 : var2){
            var1.add(TypeUtil.cast(var3,classz));
        }
        return var1;
    }

    private Object getCacheData(String cacheName, Object key){
        return Objects.requireNonNull(getCache(cacheName).get(key)).get();
    }


    /**
     * 清空某个缓存
     * @param cacheName 缓存名称
     */
    public void clearCache(String cacheName) {
        Cache cache = Vanas.cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }

    /**
     * 清空所有缓存
     */
    public void clearAll() {
        for (String cacheName : Vanas.cacheManager.getCacheNames()) {
            Cache cache = Vanas.cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.clear();
            }
        }
    }

}

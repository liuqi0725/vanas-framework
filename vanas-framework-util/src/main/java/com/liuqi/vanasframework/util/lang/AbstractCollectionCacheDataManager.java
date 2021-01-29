package com.liuqi.vanasframework.util.lang;

import com.liuqi.vanasframework.util.CacheUtil;

/**
 * 类说明 <br>
 * <p>
 * 构造说明 :
 * <pre>
 * 集合类缓存数据处理超类
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 14:54 2020-04-27
 */
public abstract class AbstractCollectionCacheDataManager<E> implements CollectionCacheData<E>{

    protected String cacheName;

    protected Object cacheKey;

    public AbstractCollectionCacheDataManager(String cacheName, Object cacheKey) {
        this.cacheName = cacheName;
        this.cacheKey = cacheKey;
        this.cacheInit();
    }

    public abstract Object get();

    /**
     * 初始化缓存
     */
    protected abstract void cacheInit();

    /**
     * 缓存是否存在
     * @return 是否存在
     */
    protected boolean hasCache(){
        Object o;
        try {
            o = CacheUtil.getInstance().getVal(cacheName,cacheKey);
        }catch (NullPointerException e){
            return false;
        }

        return o != null;
    }

    @Override
    public void destroy() {
//        CacheUtil.getInstance().removeCacheData(cacheName,cacheKey);
    }

    /**
     * 重新设置缓存
     * @param obj 存储对象
     */
    protected void reSetCacheData(Object obj){
        CacheUtil.getInstance().setVal(cacheName , cacheKey , obj);
    }
}

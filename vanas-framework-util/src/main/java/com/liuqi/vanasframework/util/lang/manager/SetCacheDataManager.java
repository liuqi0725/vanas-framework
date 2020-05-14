package com.liuqi.vanasframework.util.lang.manager;

import com.liuqi.vanasframework.util.lang.AbstractCollectionCacheDataManager;
import com.liuqi.vanasframework.util.CacheUtil;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 类说明 <br>
 * <p>
 * 构造说明 :
 * <pre>
 *
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 14:54 2020-04-27
 */
public class SetCacheDataManager<E extends Serializable> extends AbstractCollectionCacheDataManager<E> {

    private Set<E> elementData;

    public SetCacheDataManager(String cacheName, Object cacheKey) {
        super(cacheName, cacheKey);
    }

    @Override
    public Set<E> get() {
        return elementData;
    }

    /**
     * 添加一条数据到集合缓存数据中<br>
     * <p>
     * 通过泛型找到缓存中存储的集合，并向存储数据添加一个内容。<br>
     * 注意：
     *      1. 缓存对象必须是个集合
     *      2. 泛型必须正确
     * </p>
     * @param element  需要添加到缓存的存储对象
     */
    @Override
    public void add(E element) {
        this.elementData.add(element);
        // 改变缓存
        this.reSetCacheData(this.elementData);
    }

    @Override
    public void addAll(Collection<? extends E> elementList) {
        this.elementData.addAll(elementList);
        // 改变缓存
        this.reSetCacheData(this.elementData);
    }

    @Override
    public void remove(E element) {
        boolean reSet = false;

        for (E next : this.elementData) {
            if (next.equals(element)) {
                this.elementData.remove(next);
                reSet = true;
                break;
            }
        }
        if(reSet)
            this.reSetCacheData(this.elementData);
    }

    @Override
    public void clear() {
        this.elementData = new HashSet<>();
        this.reSetCacheData(this.elementData);
    }

    @Override
    public void replace(E element) {
        boolean reSet = false;

        for (E next : this.elementData) {
            if (next.equals(element)) {
                this.elementData.remove(next);
                this.elementData.add(element);
                reSet = true;
                break;
            }
        }
        if(reSet)
            this.reSetCacheData(this.elementData);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void cacheInit() {
        if(hasCache()){
            elementData = (Set<E>) CacheUtil.getInstance().getVal(cacheName,cacheKey);
        }else{
            elementData = new HashSet<>();
            this.reSetCacheData(this.elementData);
        }
    }


}

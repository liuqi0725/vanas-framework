package com.liuqi.vanasframework.util.lang.manager;

import com.liuqi.vanasframework.util.lang.AbstractCollectionCacheDataManager;
import com.liuqi.vanasframework.util.CacheUtil;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

/**
 * 类说明 <br>
 * <p>
 * 构造说明 :
 * <pre>
 *  针对链表缓存的数据处理
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 14:54 2020-04-27
 */
public class LinkedListCacheDataManager<E extends Serializable> extends AbstractCollectionCacheDataManager<E> {

    private LinkedList<E> elementData;

    public LinkedListCacheDataManager(String cacheName, Object cacheKey) {
        super(cacheName, cacheKey);
    }

    @Override
    public LinkedList<E> get() {
        return elementData;
    }

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

        for(int i=0; i<this.elementData.size(); i++){
            if(this.elementData.get(i).equals(element)){
                this.elementData.remove(i);
                reSet = true;
                break;
            }
        }
        if(reSet)
            this.reSetCacheData(this.elementData);
    }

    @Override
    public void clear() {
        this.elementData = new LinkedList<>();
        this.reSetCacheData(this.elementData);
    }

    @Override
    public void replace(E element) {
        boolean reSet = false;

        for(int i=0; i<this.elementData.size(); i++){
            if(this.elementData.get(i).equals(element)){
                this.elementData.set(i,element);
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
            elementData = (LinkedList<E>) CacheUtil.getInstance().getVal(cacheName,cacheKey);
        }else{
            elementData = new LinkedList<>();
            this.reSetCacheData(this.elementData);
        }
    }


}

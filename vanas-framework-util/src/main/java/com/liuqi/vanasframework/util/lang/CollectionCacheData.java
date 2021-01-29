package com.liuqi.vanasframework.util.lang;

import java.util.Collection;

/**
 * 类说明 <br>
 * <p>
 * 构造说明 :
 * <pre>
 * 针对集合的 cache 数据处理
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 14:54 2020-04-27
 */
public interface CollectionCacheData<E>{


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
    public void add(E element);

    /**
     * 添加多条数据到集合缓存数据中<br>
     * <p>
     * 通过泛型找到缓存中存储的集合，并向存储数据添加多个内容。<br>
     * 注意：
     *      1. 缓存对象必须是个集合
     *      2. 泛型必须正确
     * </p>
     * @param elementList  需要添加到缓存的存储对象
     */
    public void addAll(Collection<? extends E> elementList);

    /**
     * 集合缓存数据中删除某条数据<br>
     * <p>
     *     注意：<br>
     *     对比对象是否是同一个对象，是通过 equals 方法。你需要自己重新 equals 函数
     * </p>
     * @param element  需要删除的对象
     */
    public void remove(E element);

    /**
     * 删除缓存
     */
    public void destroy();

    /**
     * 清空缓存
     */
    public void clear();

    /**
     * 替换集合缓存数据中的某一个值 【需重写 equals 方法】<br>
     * @param element 需要替换的对象，必须和集合泛型类一个类型
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
    public void replace(E element);
}

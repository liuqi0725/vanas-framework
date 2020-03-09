package com.liuqi.vanasframework.core.mvc.base;


import com.liuqi.vanasframework.core.mvc.res.PageDataResult;

import java.io.Serializable;
import java.util.List;

/**
 * 类说明 <br>
 *     全局 service（业务）事件监听接口
 * <p>
 *
 *
 *
 *
 *
 * @author : alexliu
 * @version v1.0 , Create at 9:32 PM 2020/2/19
 */
public interface BaseService<Entity,PK extends Serializable> {

    /**
     * 保存数据
     * @param entity 保存的对象
     */
    void add(Entity entity);

    /**
     * 通过 ID删除数据
     * @param id id 删除
     */
    void delete(PK id);

    /**
     * 修改数据
     * @param entity 对象
     */
    void update(Entity entity);

    /**
     * 通过 ID 查询
     * @param id 数据 id
     * @return 返回对象
     */
    Entity selectOne(PK id);

    /**
     * 分页查询
     * @param entity 对象
     * @return 封装的对象 {@link PageDataResult}
     */
    PageDataResult selectPageList(Entity entity);

    /**
     * 查询 object 的 list 数据
     * @param entity 对象
     * @return 封装的对象
     */
    List<Entity> selectList(Entity entity);

    /**
     * 查询全部
     * @return List
     */
    List<Entity> selectAll();
}

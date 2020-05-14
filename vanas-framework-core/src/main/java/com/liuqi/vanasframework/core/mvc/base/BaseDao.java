package com.liuqi.vanasframework.core.mvc.base;

import java.io.Serializable;
import java.util.List;

/**
 * 类说明 <br>
 *     顶级 Base
 * <p>
 *
 *
 *
 *
 *
 * @author : alexliu
 * @version v1.0 , Create at 9:32 PM 2020/2/19
 */
public class BaseDao<Entity, PK extends Serializable> extends BaseDaoNoDefault<Entity, PK>{

    private static final long serialVersionUID = 3674013335380598212L;

    private final String PKG_SEP = ".";

    private final String _INSERT = ".insert";

    private final String _DELETE_BY_PK = ".deleteByPK";

    private final String _UPDATE_BY_SELECTIVE = ".updateSelective";

    private final String _SELECT_BY_SELECTIVE = ".selectSelective";

    private final String _SELECT_LIST_BY_SELECTIVE = ".selectListSelective";

    private final String _SELECT_BY_PK = ".selectByPK";

    private final String _SELECT_COUNT_BY_SELECTIVE = ".selectCountSelective";

    private final String _SELECT_ALL = ".selectAll";

    private String getNameSpace() {

        String daoPkg = this.getClass().getPackage().getName();
        String daoName = this.getClass().getSimpleName();

        return daoPkg + PKG_SEP + daoName;
    }

    /**
     * 插入一条数据
     * @param entity {@link Entity} 反射对象
     * @return 影响行数
     */
    public int insert(Entity entity) {
        return getSqlSession().insert(this.getNameSpace() + _INSERT, entity);
    }

    /**
     * 根据主键 ID 删除数据【物理删除】
     * @param id {@link PK} 主键
     * @return 影响行数
     */
    public int deleteByPK(PK id) {
        return getSqlSession().delete(this.getNameSpace() + _DELETE_BY_PK, id);
    }

    /**
     * 根据传入的对象，有条件的的筛选变更数据
     * @param entity {@link Entity} 反射对象
     * @return 影响行数
     */
    public int updateBySelective(Entity entity) {
        return getSqlSession().update(this.getNameSpace() + _UPDATE_BY_SELECTIVE, entity);
    }

    /**
     * 根据主键 ID 查询唯一数据
     * @param id {@link PK} 主键
     * @return {@link Entity}
     */
    public Entity selectByPK(PK id) {
        return getSqlSession().selectOne(this.getNameSpace() + _SELECT_BY_PK, id);
    }

    /**
     * 根据传入的对象，有条件的查询唯一的数据
     * @param entity {@link Entity} 反射对象
     * @return {@link Entity}
     */
    public Entity selectSelective(Entity entity) {
        return getSqlSession().selectOne(this.getNameSpace() + _SELECT_BY_SELECTIVE, entity);
    }

    /**
     * 根据传入的对象，有条件的查询数据集合
     * @param entity {@link Entity} 反射对象
     * @return {@link List &lt;Entity&gt; }
     */
    public List<Entity> selectListSelective(Entity entity) {
        return getSqlSession().selectList(this.getNameSpace() + _SELECT_LIST_BY_SELECTIVE, entity);
    }

    /**
     * 根据传入的对象，有条件的查询数据 行数
     * @param entity {@link Entity} 反射对象
     * @return {@link Integer }
     */
    public int selectCountSelective(Entity entity) {
        return getSqlSession().selectOne(this.getNameSpace() + _SELECT_COUNT_BY_SELECTIVE, entity);
    }

    /**
     * 查询所有数据
     * @return {@link List &lt;Entity&gt; }
     */
    public List<Entity> selectAll() {
        return getSqlSession().selectList(this.getNameSpace() + _SELECT_ALL);
    }

}

package com.liuqi.vanasframework.core.mvc.base;

import com.liuqi.vanasframework.core.conf.norm.ExceptionErrorCode;
import com.liuqi.vanasframework.core.exception.AppException;
import com.liuqi.vanasframework.core.mvc.res.PageDataResult;
import org.springframework.cache.annotation.Cacheable;

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
public abstract class BaseServiceAdapter<Entity,PK extends Serializable> implements BaseService<Entity,PK>{

    private BaseDao<Entity , PK> baseDao;

    public void setBaseDao(BaseDao<Entity , PK> baseDao) {
        this.baseDao = baseDao;
    }

    public BaseDao<Entity, PK> getBaseDao() {
        return baseDao;
    }

    @Override
    public void add(Entity entity) {
        this.baseDao.insert(entity);
    }

    @Override
    public void delete(PK id) {
        this.baseDao.deleteByPK(id);
    }

    @Override
    public void update(Entity entity) {
        this.baseDao.updateBySelective(entity);
    }

    @Override
    public Entity selectOne(PK id) {

        if(id == null){
            throw new AppException(ExceptionErrorCode.NULL_ERROR.getCode() , "查询数据 ID 不能为空");
        }

        return this.baseDao.selectByPK(id);
    }

    @Override
    public PageDataResult selectPageList(Entity entity) {

        PageDataResult res = new PageDataResult();

        res.setPageData(this.baseDao.selectListSelective(entity));
        res.setTotal(this.baseDao.selectCountSelective(entity));

        return res;
    }

    @Override
    public List<Entity> selectList(Entity entity) {
        return this.baseDao.selectListSelective(entity);
    }

    @Override
    public List<Entity> selectAll() {
        return this.baseDao.selectAll();
    }
}

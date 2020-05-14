package com.liuqi.vanasframework.core.mvc.base;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * 类说明 <br>
 *     顶级 Base ,没有默认实现
 * <p>
 *
 *
 *
 *
 *
 * @author : alexliu
 * @version v1.0 , Create at 9:32 PM 2020/2/19
 */
@Repository
public class BaseDaoNoDefault<Entity, PK extends Serializable> extends SqlSessionDaoSupport implements Serializable {

    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }
}

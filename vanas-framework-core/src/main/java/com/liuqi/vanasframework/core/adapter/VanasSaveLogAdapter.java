package com.liuqi.vanasframework.core.adapter;

import com.liuqi.vanasframework.core.conf.norm.SaveLogType;
import com.liuqi.vanasframework.core.mvc.base.BaseService;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 类说明 <br>
 *     日志注解记录，外部实现适配器。<br>
 *     当调用带有  {@code SaveLog} 注解的方法时，切面会调用实现类，完成日志记录
 *     外部系统需要实现该类
 * <p>
 *
 * @author : alexliu
 * @version v1.0 , Create at 2:31 PM 2020/3/6
 */
public interface VanasSaveLogAdapter<Entity,PK extends Serializable> extends BaseService<Entity,PK>{

    public void saveLog(String className, String methodName , SaveLogType type, String desc , Object responseResult);
}

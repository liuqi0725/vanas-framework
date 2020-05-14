package com.liuqi.vanasframework.security.ex;


import com.liuqi.vanasframework.core.conf.norm.ExceptionErrorCode;
import com.liuqi.vanasframework.core.exception.AppException;

/**
 * 类说明 <br>
 *  登陆异常，[系统级错误]，比如 数据库异常、io 异常等，会统一处理返回给用户的信息
 *
 * @author : alexliu
 * @version v1.0 , Create at 9:06 PM 2020/3/2
 */
public class BadPasswordError extends AppException {

    public BadPasswordError(String msg) {
        super(ExceptionErrorCode.SECURITY_ERROR , msg);
    }

    public BadPasswordError(String msg , Throwable t) {
        super(ExceptionErrorCode.SECURITY_ERROR , msg, t);
    }
}

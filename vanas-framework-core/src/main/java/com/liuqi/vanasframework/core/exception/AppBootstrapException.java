package com.liuqi.vanasframework.core.exception;


import com.liuqi.vanasframework.core.conf.norm.ExceptionErrorCode;

/**
 * 类说明 <br>
 * 系统启动加载错误
 *
 * @author : alexliu
 * @version v1.0 , Create at 9:06 PM 2020/3/2
 */
public class AppBootstrapException extends AppException{

    public AppBootstrapException(String msg) {
        super(ExceptionErrorCode.SYSTEM_ERROR.getCode() , msg);
    }

    public AppBootstrapException(String msg , Throwable t) {
        super(ExceptionErrorCode.SYSTEM_ERROR.getCode(), msg, t);
    }
}

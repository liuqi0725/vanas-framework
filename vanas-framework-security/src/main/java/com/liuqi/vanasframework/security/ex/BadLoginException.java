package com.liuqi.vanasframework.security.ex;


import com.liuqi.vanasframework.core.conf.norm.ExceptionErrorCode;
import com.liuqi.vanasframework.core.exception.AppException;

/**
 * 类说明 <br>
 *  登陆异常，用户级错误。比如没有权限、角色、密码不正确等。此错误适用于登陆相关错误，具体错误可以通过 msg 传入。并显示给前台 <br>
 *  V1.0.0.5 以上推荐使用。
 *
 *
 * @author : alexliu
 * @version v1.0 , Create at 9:06 PM 2020/3/2
 */
public class BadLoginException extends AppException {

    public BadLoginException(String msg) {
        super(ExceptionErrorCode.LOGIN_ERROR, msg);
    }

    public BadLoginException(ExceptionErrorCode code , String msg) {
        super(code, msg);
    }

    public BadLoginException(String msg , Throwable t) {
        super(msg, t);
    }
}

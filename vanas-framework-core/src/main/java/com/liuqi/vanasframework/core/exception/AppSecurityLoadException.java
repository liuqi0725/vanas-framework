package com.liuqi.vanasframework.core.exception;


import com.liuqi.vanasframework.core.conf.norm.ExceptionErrorCode;

/**
 * 类说明 <br>
 * <p>
 *
 *
 *
 *
 *
 * @author : alexliu
 * @version v1.0 , Create at 9:06 PM 2020/3/2
 */
public class AppSecurityLoadException extends AppException{

    public AppSecurityLoadException(String msg) {
        super(ExceptionErrorCode.NORMARL_ERROR , msg);
    }

}
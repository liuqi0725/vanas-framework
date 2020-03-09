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
 * @version v1.0 , Create at 9:55 PM 2019/12/10
 */
public class AppException extends RuntimeException{

    private static final long serialVersionUID = -9025344064191826787L;

    private Object data;

    /**
     *  错误码
     **/
    private final ExceptionErrorCode exceptionErrorCode;


    public AppException(Throwable t) {
        super(t);
        this.exceptionErrorCode = ExceptionErrorCode.BAD_REQUEST;
    }




    public AppException(ExceptionErrorCode exceptionErrorCode) {
        super(exceptionErrorCode.getDesc());
        this.exceptionErrorCode = exceptionErrorCode;
    }

    public AppException(ExceptionErrorCode exceptionErrorCode, String detailMessage) {
        super(detailMessage);
        this.exceptionErrorCode = exceptionErrorCode;
    }

    public AppException(ExceptionErrorCode exceptionErrorCode, String detailMessage , Throwable t) {
        super(detailMessage , t);
        this.exceptionErrorCode = exceptionErrorCode;
    }





    public AppException(String detailMessage) {
        super(detailMessage);
        this.exceptionErrorCode = ExceptionErrorCode.BAD_REQUEST;
    }

    public AppException(String detailMessage , Throwable t) {
        super(detailMessage , t);
        this.exceptionErrorCode = ExceptionErrorCode.BAD_REQUEST;
    }





    public AppException(Object data) {
        super(ExceptionErrorCode.PARAM_ERROR.getDesc());
        this.data = data;
        this.exceptionErrorCode = ExceptionErrorCode.PARAM_ERROR;
    }

    public AppException(ExceptionErrorCode exceptionErrorCode, Object data) {
        super(exceptionErrorCode.getDesc());
        this.data = data;
        this.exceptionErrorCode = exceptionErrorCode;
    }

    public AppException(ExceptionErrorCode exceptionErrorCode, String detailMessage , Object data) {
        super(detailMessage);
        this.data = data;
        this.exceptionErrorCode = exceptionErrorCode;
    }

    public AppException(ExceptionErrorCode exceptionErrorCode, String detailMessage , Throwable t ,  Object data) {
        super(detailMessage , t);
        this.data = data;
        this.exceptionErrorCode = exceptionErrorCode;
    }





    public ExceptionErrorCode getExceptionErrorCode() {
        return exceptionErrorCode;
    }

    public Object getData() {
        return data;
    }

}

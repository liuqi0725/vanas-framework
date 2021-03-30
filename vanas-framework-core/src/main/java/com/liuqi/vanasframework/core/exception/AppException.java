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
//    private final ExceptionErrorCode exceptionErrorCode;

    private String errorCode;

    private String errorMsg;


    public AppException(Throwable t) {
        super(t);
        this.errorCode = ExceptionErrorCode.BAD_REQUEST.getCode();
        this.errorMsg = ExceptionErrorCode.BAD_REQUEST.getDesc();
//        this.exceptionErrorCode = ExceptionErrorCode.BAD_REQUEST;
    }

//    public AppException(ExceptionErrorCode exceptionErrorCode) {
//        super(exceptionErrorCode.getDesc());
//        this.errorCode = exceptionErrorCode.getCode();
//        this.errorMsg = exceptionErrorCode.getDesc();
////        this.exceptionErrorCode = exceptionErrorCode;
//    }
//
//    public AppException(ExceptionErrorCode exceptionErrorCode, String errorMsg) {
//        super(errorMsg);
//        this.errorCode = exceptionErrorCode.getCode();
//        this.errorMsg = errorMsg;
////        this.exceptionErrorCode = exceptionErrorCode;
//    }
//
//    public AppException(ExceptionErrorCode exceptionErrorCode, String errorMsg , Throwable t) {
//        super(errorMsg , t);
//        this.errorCode = exceptionErrorCode.getCode();
//        this.errorMsg = errorMsg;
////        this.exceptionErrorCode = exceptionErrorCode;
//    }
//
    public AppException(String errorMsg) {
        super(errorMsg);
        this.errorCode = ExceptionErrorCode.BAD_REQUEST.getCode();
        this.errorMsg = errorMsg;
    }
//
//    public AppException(String errorMsg , Throwable t) {
//        super(errorMsg , t);
//        this.errorCode = ExceptionErrorCode.BAD_REQUEST.getCode();
//        this.errorMsg = errorMsg;
////        this.exceptionErrorCode = ExceptionErrorCode.BAD_REQUEST;
//    }
//
//    public AppException(Object data) {
//        super(ExceptionErrorCode.PARAM_ERROR.getDesc());
//        this.data = data;
//        this.errorCode = ExceptionErrorCode.PARAM_ERROR.getCode();
//        this.errorMsg = ExceptionErrorCode.PARAM_ERROR.getDesc();
////        this.exceptionErrorCode = ExceptionErrorCode.PARAM_ERROR;
//    }
//
//    public AppException(ExceptionErrorCode exceptionErrorCode, Object data) {
//        super(exceptionErrorCode.getDesc());
//        this.data = data;
//        this.errorCode = exceptionErrorCode.getCode();
//        this.errorMsg = exceptionErrorCode.getDesc();
////        this.exceptionErrorCode = exceptionErrorCode;
//    }
//
//    public AppException(ExceptionErrorCode exceptionErrorCode, String errorMsg , Object data) {
//        super(errorMsg);
//        this.data = data;
//        this.errorCode = exceptionErrorCode.getCode();
//        this.errorMsg = errorMsg;
////        this.exceptionErrorCode = exceptionErrorCode;
//    }
//
//    public AppException(ExceptionErrorCode exceptionErrorCode, String errorMsg , Throwable t ,  Object data) {
//        super(errorMsg , t);
//        this.data = data;
//        this.errorCode = exceptionErrorCode.getCode();
//        this.errorMsg = errorMsg;
////        this.exceptionErrorCode = exceptionErrorCode;
//    }


    public AppException(String errorCode, String errorMsg) {
        super(String.format("[%s] %s",errorCode,errorMsg));
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public AppException(String errorCode, String errorMsg, Throwable t) {
        super(String.format("[%s] %s",errorCode,errorMsg), t);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }


    public String getExceptionErrorCode() {
        return this.errorCode;
    }

    public String getExceptionErrorMsg() {
        return this.errorMsg;
    }

    public Object getData() {
        return data;
    }

}

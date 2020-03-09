package com.liuqi.vanasframework.core.mvc.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 类说明 <br>
 *     封装统一的错误信息
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:29 AM 2020/3/9
 */
public class ErrorMap implements Serializable{

    private static final long serialVersionUID = 819565842757113989L;

    /**
     * 错误的请求地址
     */
    private String path;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 错误名称【异常名称】
     */
    private String error;

    /**
     * 错误编码
     */
    private String errorCode;

    /**
     * 错误发生时间
     */
    private Date timestamp;

    /**
     * 异常，用于获取 trace
     */
    private Throwable t;

    /**
     * 引发错误的参数
     */
    private Object errorData;

    public ErrorMap(){}

    public ErrorMap(Map<String,Object> springErrorMap){
        this.setPath((String)springErrorMap.get("path"));
        this.setError((String)springErrorMap.get("error"));
        this.setErrorMsg((String)springErrorMap.get("message"));
        this.setError((String)springErrorMap.get("status"));
        this.setErrorCode((String)springErrorMap.get("error"));
        this.setTimestamp((Date)springErrorMap.get("timestamp"));
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        if(errorMsg.length() < 300){
            this.errorMsg = errorMsg;
        }else{
            this.errorMsg = errorMsg.substring(0,300) + " \r\n 详情查看错误日志";
        }

        this.errorMsg = errorMsg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Throwable getT() {
        return t;
    }

    public void setT(Throwable t) {
        this.t = t;
    }

    public Object getErrorData() {
        return errorData;
    }

    public void setErrorData(Object errorData) {
        this.errorData = errorData;
    }
}

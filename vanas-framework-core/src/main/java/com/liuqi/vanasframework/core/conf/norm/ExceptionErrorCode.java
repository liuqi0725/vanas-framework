package com.liuqi.vanasframework.core.conf.norm;

/**
 * 类说明 <br>
 * <p>
 *
 *
 *
 *
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:08 PM 2019/12/10
 */
public enum ExceptionErrorCode {

    BAD_REQUEST("500","服务器错误，请联系管理员"),

    NORMARL_ERROR("1000","系统错误"),

    PARAM_ERROR("1001","参数错误"),

    NULL_ERROR("1002","空值错误"),

    NOT_SUPPORT_TYPE("1003","不支持的数据类型"),

    DATABASE_ERROR("1004","数据库异常");


    private String code;

    private String desc;

    private ExceptionErrorCode(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static Boolean contains(String code){
        for(ExceptionErrorCode e : ExceptionErrorCode.values()){
            if(e.getCode().equals(code)){
                return true;
            }
        }

        return false;
    }
}

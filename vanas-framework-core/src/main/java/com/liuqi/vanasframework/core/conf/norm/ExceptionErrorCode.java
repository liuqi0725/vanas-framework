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


    LOGIN_ERROR("10010","登陆异常"),

    LOGIN_USER_NOT_FOUND("10011","用户不存在"),

    LOGIN_PASSWORD_ERROR("10012","密码错误"),

    LOGIN_USER_IS_STOP("10013","用户已被停用"),

    LOGIN_USER_NO_ROLE("10014","用户没有角色"),

    LOGIN_USER_NO_PERMISSION("10015","用户没有权限"),


    SYSTEM_ERROR("10020","系统错误，请联系管理员"),

    SECURITY_ERROR("10021","系统安全异常"),

    SECURITY_NO_PERMISSION("10022","用户没有权限"),


    PARAM_ERROR("10030","参数错误"),

    NULL_ERROR("10031","空值错误"),

    NOT_SUPPORT_TYPE("10032","不支持的数据类型"),


    DATABASE_NOT_FOUND("10090","数据库查询不到对应数据");



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

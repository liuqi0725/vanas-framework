package com.liuqi.vanasframework.core.conf;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 类说明 <br>
 * <p>
 * 构造说明 :
 * <pre>
 *
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 09:41 2020-03-17
 */
@Configuration
@ConfigurationProperties(prefix = "vanas" , ignoreUnknownFields=true)
public class VanasCustomerConfig implements Serializable {

    private static final long serialVersionUID = 2821054520945565401L;

    /**
     * 系统当前环境
     */
    @NotEmpty
    private String environment;

    /**
     * 是否允许 vc 登录
     */
    @Value("${vanas.vc-enabled:false}")
    private boolean vcEnabled;

    /**
     * 模板引擎
     */
    @Value("${vanas.view-engine:freemarker}")
    private String viewEngine;

    /**
     * 超级管理员角色
     */
    @NotEmpty
    private String superAdminRole;

    /**
     * 白名单 "," 分割; 白名单中的地址不用认证用户
     */
    @NotEmpty
    @Value("${vanas.security.permit-url}")
    private String[] permitUrl;

    /**
     * 是否开启 csrf 验证
     */
    @Value("${vanas.security.csrf-enabled:true}")
    private boolean csrfEnabled;

    /**
     * 白名单 "," 分割; 白名单中的地址form 提交不验证 csrf; 当 csrf-enabled 为 false 时不生效
     */
    @Value("${vanas.security.csrf-permit-url}")
    private String[] csrfPermitUrl;

    /**
     * 非必填】登陆 url GET 返回登录视图 ，【默认 /login】
     */
    @Value("${vanas.security.login-page-url:/login}")
    private String loginUrl;

    /**
     * 【非必填】登陆 url POST 登陆提交的 form，在前端from action 中的值。默认提交 vanas.security 用户认证 【默认 /login】
     */
    @Value("${vanas.security.login-form-url:/login}")
    private String loginFormUrl;

    /**
     * 【非必填】登录成功跳转的地址 【默认 /dashboard】
     */
    @Value("${vanas.security.login-success-url:/dashboard}")
    private String loginSuccessUrl;

    /**
     * 【非必填】登录失败跳转的地址 【默认 /login?error】
     */
    @Value("${vanas.security.login-failure-url:/login?error}")
    private String loginFailureUrl;

    /**
     * 【非必填】退出登录后跳转的地址 【默认 /login】
     */
    @Value("${vanas.security.login-success-url:/login}")
    private String loginOutSuccessUrl;

    /**
     * 【非必填】是否开启 cookie【默认 true】
     */
    @Value("${vanas.security.cookie-enabled:true}")
    private boolean cookieEnabled;

    /**
     * 【非必填】cookie 有效期，单位：秒 【默认 2 天】
     */
    @Value("${vanas.security.cookie-valid-seconds:172800}")
    private Integer cookieValidSeconds;

    public String[] getPermitUrl() {
        return permitUrl;
    }

    public void setPermitUrl(String permitUrl) {
        this.permitUrl = getStringArray(permitUrl);
    }

    public boolean isCsrfEnabled() {
        return csrfEnabled;
    }

    public void setCsrfEnabled(boolean csrfEnabled) {
        this.csrfEnabled = csrfEnabled;
    }

    public String[] getCsrfPermitUrl() {
        return csrfPermitUrl;
    }

    public void setCsrfPermitUrl(String csrfPermitUrl) {
        this.csrfPermitUrl = getStringArray(csrfPermitUrl);
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getLoginFormUrl() {
        return loginFormUrl;
    }

    public void setLoginFormUrl(String loginFormUrl) {
        this.loginFormUrl = loginFormUrl;
    }

    public String getLoginSuccessUrl() {
        return loginSuccessUrl;
    }

    public void setLoginSuccessUrl(String loginSuccessUrl) {
        this.loginSuccessUrl = loginSuccessUrl;
    }

    public String getLoginFailureUrl() {
        return loginFailureUrl;
    }

    public void setLoginFailureUrl(String loginFailureUrl) {
        this.loginFailureUrl = loginFailureUrl;
    }

    public String getLoginOutSuccessUrl() {
        return loginOutSuccessUrl;
    }

    public void setLoginOutSuccessUrl(String loginOutSuccessUrl) {
        this.loginOutSuccessUrl = loginOutSuccessUrl;
    }

    public boolean isCookieEnabled() {
        return cookieEnabled;
    }

    public void setCookieEnabled(boolean cookieEnabled) {
        this.cookieEnabled = cookieEnabled;
    }

    public Integer getCookieValidSeconds() {
        return cookieValidSeconds;
    }

    public void setCookieValidSeconds(Integer cookieValidSeconds) {
        this.cookieValidSeconds = cookieValidSeconds;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public boolean isVcEnabled() {
        return vcEnabled;
    }

    public void setVcEnabled(boolean vcEnabled) {
        this.vcEnabled = vcEnabled;
    }

    public String getViewEngine() {
        return viewEngine;
    }

    public void setViewEngine(String viewEngine) {
        this.viewEngine = viewEngine;
    }

    public String getSuperAdminRole() {
        return superAdminRole;
    }

    public void setSuperAdminRole(String superAdminRole) {
        this.superAdminRole = superAdminRole;
    }

    private String[] getStringArray(String attrVal){
        if(StringUtils.isEmpty(attrVal)){
            return null;
        }
        String[] attrArray = attrVal.split(",");

        List<String> finalList = new ArrayList<>();
        // 排除空值，前后去空格
        for(String attr : attrArray){
            if(StringUtils.isEmpty(attr)){
                continue;
            }
            finalList.add(attr.trim());
        }
        return (String[])finalList.toArray();
    }
}

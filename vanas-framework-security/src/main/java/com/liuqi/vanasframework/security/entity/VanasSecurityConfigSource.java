package com.liuqi.vanasframework.security.entity;

import com.liuqi.vanasframework.security.VanasSecurityConfig;
import com.liuqi.vanasframework.security.crypto.VanasPasswordEncoder;
import com.liuqi.vanasframework.security.crypto.VanasPasswordVoter;
import com.liuqi.vanasframework.security.service.VanasSecurityDaoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

/**
 * 类说明 <br>
 * <p>
 * 构造说明 :
 * <pre>
 *
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 15:04 2020-03-16
 */
@Log4j2
public class VanasSecurityConfigSource {

    /**
     * 登陆页响应地址
     */
    private String loginURL;

    /**
     * 登陆 form 提交的地址
     */
    private String loginFormURL;

    /**
     * 登陆 成功跳转地址
     */
    private String loginSuccessURL;

    /**
     * 登陆错误地址
     */
    private String loginErrorURL;

    /**
     * 退出登录后的地址
     */
    private String loginOutSuccessURL;

    /**
     * 设置 cookie 有效期，单位：秒 默认 2 天
     */
    private int cookieValidSeconds;

    /**
     * 是否根据 vcid 登录
     */
    private boolean isLoginByVC;

    /**
     * 白名单
     */
    private String[] permitURL;

    /**
     * 是否打开 csrf
     */
    private boolean closeCsrf;

    private boolean closeCookie;

    /**
     * csrf 过去 form 地址，当关闭 csrf 时，不起作用
     */
    private String[] csrfIgnoringURL;

    /**
     * 密码加解密方式
     */
    private PasswordEncoder passwordEncoder;

    /**
     * 第三方实现 dao service
     */
    private VanasSecurityDaoService  customerDaoService;


    /**
     * 不能通过 new 创建实例
     */
    private VanasSecurityConfigSource(){

    }

    public static VanasSecurityConfigSource getInstance(){
        return VanasSecurityConfigSourceHook.instance;
    }

    private static class VanasSecurityConfigSourceHook{
        private static final VanasSecurityConfigSource instance = new VanasSecurityConfigSource();
    }

    public void init(VanasSecurityConfig vanasSecurityConfig) {
        this.loginURL = vanasSecurityConfig.getLoginURL();
        this.loginFormURL = vanasSecurityConfig.getLoginFormURL();
        this.loginErrorURL = vanasSecurityConfig.getLoginErrorURL();
        this.loginSuccessURL = vanasSecurityConfig.getLoginSuccessURL();

        this.loginOutSuccessURL = vanasSecurityConfig.getLoginOutSuccessURL();

        this.permitURL = vanasSecurityConfig.getPermitURL();

        this.isLoginByVC = vanasSecurityConfig.getLoginByVC();

        this.closeCookie = vanasSecurityConfig.closeCookie();
        this.cookieValidSeconds = vanasSecurityConfig.getCookieValidSeconds();

        this.closeCsrf = vanasSecurityConfig.closeCsrf();
        this.csrfIgnoringURL = vanasSecurityConfig.getCsrfIgnoringURL();


        this.setCustomerPasswordEncoder(vanasSecurityConfig.getCustomerPasswordVoter());
        this.setCustomerDaoService(vanasSecurityConfig.getVanasSecurityDaoService());

    }

    public boolean isLoginByVC() {
        return isLoginByVC;
    }

    public String[] getPermitURL() {
        return permitURL;
    }

    public boolean isCloseCsrf() {
        return closeCsrf;
    }

    public boolean isCloseCookie() {
        return closeCookie;
    }

    public String[] getCsrfIgnoringURL() {
        return csrfIgnoringURL;
    }

    public VanasSecurityDaoService getCustomerDaoService() {
        return customerDaoService;
    }

    public String getLoginURL() {
        return loginURL;
    }

    public String getLoginFormURL() {
        return loginFormURL;
    }

    public String getLoginSuccessURL() {
        return loginSuccessURL;
    }

    public String getLoginErrorURL() {
        return loginErrorURL;
    }

    public String getLoginOutSuccessURL() {
        return loginOutSuccessURL;
    }

    public int getCookieValidSeconds() {
        return cookieValidSeconds;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    private void setCustomerDaoService(VanasSecurityDaoService customerDaoService) {
        log.info("安全组件配置 >> 获取登陆认证客户 与 VanasSecurity 的数据库实现层");

        Assert.notNull(customerDaoService , "VanasSecurityDaoService 不能为空。请在系统中实现 VanasSecurityDaoService，并在 VanasSecurityConfig 实现的初始化中注入。");
        this.customerDaoService = customerDaoService;
    }

    /**
     * 设置密码生成策略.
     * @return {@link PasswordEncoder}
     */
    private void setCustomerPasswordEncoder(VanasPasswordVoter customerPasswordVoter){

        log.info("安全组件配置 >> 获取登陆安全认证的密码认证器");

        if(customerPasswordVoter == null){
            log.info("安全组件配置 >> 获取登陆安全认证的密码认证器 ， 使用系统默认 BCryptPasswordEncoder");

            /*
             * Spring Security 提供了BCryptPasswordEncoder类,实现Spring的PasswordEncoder接口使用BCrypt强哈希方法来加密密码。
             * 即 密码 + 盐
             */
            this.passwordEncoder = new BCryptPasswordEncoder();
        }else{
            log.info("安全组件配置 >> 获取登陆安全认证的密码认证器 ， 使用系统自定义 VanasPasswordEncoder");
            // 自定义密码加密方式
            this.passwordEncoder = new VanasPasswordEncoder(customerPasswordVoter);
        }
    }
}

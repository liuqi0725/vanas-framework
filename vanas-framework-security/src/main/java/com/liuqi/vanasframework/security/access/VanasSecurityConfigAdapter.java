package com.liuqi.vanasframework.security.access;

import com.liuqi.vanasframework.security.crypto.VanasPasswordVoter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 类说明 <br>
 * 通过@EnableWebSecurity注解开启Spring Security的功能
 * 继承 WebSecurityConfigurerAdapter，并重写它的方法来设置一些web安全的细节
 * configure(HttpSecurity http)方法，
 *      通过authorizeRequests()定义哪些URL需要被保护、哪些不需要被保护。
 *      例如以上代码指定了/和/home不需要任何认证就可以访问，其他的路径都必须通过身份验证。
 *      通过formLogin()定义当需要用户登录时候，转到的登录页面。
 * <p>
 *
 *
 *
 *
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:13 PM 2020/3/4
 */
public abstract class VanasSecurityConfigAdapter{

    /**
     * 登陆页响应地址
     */
    private String loginURL = "/login";

    /**
     * 登陆 form 提交的地址
     */
    private String loginFormURL = "/login";

    /**
     * 登陆 成功跳转地址
     */
    private String loginSuccessURL = "/dashboard";

    /**
     * 登陆错误地址
     */
    private String loginErrorURL = "/login?error";

    /**
     * 退出登录后的地址
     */
    private String loginOutSuccessURL = "/login";

    /**
     * 设置 cookie 有效期，单位：秒 默认 2 天
     */
    private int cookieValidSeconds = 60 * 60 * 24 * 2;

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

    public void setLoginURL(String loginURL) {
        this.loginURL = loginURL;
    }

    public void setLoginFormURL(String loginFormURL) {
        this.loginFormURL = loginFormURL;
    }

    public void setLoginSuccessURL(String loginSuccessURL) {
        this.loginSuccessURL = loginSuccessURL;
    }

    public void setLoginErrorURL(String loginErrorURL) {
        this.loginErrorURL = loginErrorURL;
    }

    public void setLoginOutSuccessURL(String loginOutSuccessURL) {
        this.loginOutSuccessURL = loginOutSuccessURL;
    }

    public void setCookieValidSeconds(int cookieValidSeconds) {
        this.cookieValidSeconds = cookieValidSeconds;
    }


    /*
      以下抽象接口提供给实现
     */

    /**
     * 获取过滤地址，不能为空
     * @return String[] 所有过滤的 url
     */
    public abstract String[] getPermitURL();

    /**
     * 注入 全局只能有一个
     * @return {@link VanasSecurityDataSourceAdapter} 适配器
     */
    @Autowired
    public abstract VanasSecurityDataSourceAdapter getVanasSecurityAdapter();

    /**
     * 用户自定义密码处理,默认返回 null，执行 spring-security 默认的+盐密码加密方式
     * @return {@link VanasPasswordVoter} 自定义密码处理工具
     */
    public abstract VanasPasswordVoter getVanasPasswordVoter();

    /**
     * 关闭跨域请求伪造防护功能，所有 post 需要传递 _csrf值，不建议关闭，关闭会造成不可控的安全隐患。
     * @return 默认关闭
     */
    public abstract boolean closeCsrf();

    /**
     * 设置 cookie 开启，默认开启
     * @return 默认关闭
     */
    public abstract boolean closeCookie();

}

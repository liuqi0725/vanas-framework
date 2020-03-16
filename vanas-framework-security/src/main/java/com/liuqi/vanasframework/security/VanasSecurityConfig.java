package com.liuqi.vanasframework.security;

import com.liuqi.vanasframework.security.crypto.VanasPasswordVoter;
import com.liuqi.vanasframework.security.service.VanasSecurityDaoService;
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
 * @author : alexliu
 * @version v1.0 , Create at 10:13 PM 2020/3/4
 */
public abstract class VanasSecurityConfig {

    /**
     * 获取登录 GET url ，一般是返回视图的 url
     * @return 地址
     */
    public String getLoginURL() {
        return "/login";
    }

    /**
     * 获取 form 表单登录提交地址
     * @return
     */
    public String getLoginFormURL() {
        return "/login";
    }

    /**
     * 获取登录成功跳转的地址
     * @return 地址
     */
    public String getLoginSuccessURL() {
        return "/dashboard";
    }

    /**
     * 获取 登录错误地址
     * @return 地址
     */
    public String getLoginErrorURL() {
        return "/login?error";
    }

    /**
     * 获取 退出登录成功后的地址
     * @return 默认返回登录页面
     */
    public String getLoginOutSuccessURL() {
        return getLoginURL();
    }

    /**
     * 设置 cookie 有效期，单位：秒
     * @return 默认 2 天
     */
    public int getCookieValidSeconds() {
        return 60 * 60 * 24 * 2;
    }


    /**
     * 获取过滤地址，不能为空
     * @return String[] 所有过滤的 url
     */
    public abstract String[] getPermitURL();

    /**
     * 是否根据 vcid 登录
     * @return 默认 false ，只根据用户名、密码登陆
     */
    public abstract boolean getLoginByVC();

    /**
     * 注入 全局只能有一个
     * @return {@link VanasSecurityDaoService} 适配器
     */
    @Autowired
    public abstract VanasSecurityDaoService getVanasSecurityDaoService();

    /**
     * 用户自定义密码处理,默认返回 null，执行 spring-security 默认的+盐密码加密方式
     * @return {@link VanasPasswordVoter} 自定义密码处理工具
     */
    public abstract VanasPasswordVoter getCustomerPasswordVoter();

    /**
     * 关闭跨域请求伪造防护功能，所有 post 需要传递 _csrf值，不建议关闭，关闭会造成不可控的安全隐患。
     * @return 默认不关闭 【false】
     */
    public abstract boolean closeCsrf();

    /**
     * 获取 csrf 的过滤 form 提交 url。 当closeCsrf 关闭时，此项不生效
     * @return 当closeCsrf form 表单提交过滤 csrf
     */
    public abstract String[] getCsrfIgnoringURL();

    /**
     * 设置 cookie 开启，默认开启
     * @return 默认不关闭 【false】
     */
    public abstract boolean closeCookie();

}

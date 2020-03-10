package com.liuqi.vanasframework.security;

import com.liuqi.vanasframework.security.access.VanasSecurityConfigAdapter;
import com.liuqi.vanasframework.security.access.VanasUserDetailService;
import com.liuqi.vanasframework.security.crypto.VanasPasswordEncoder;
import com.liuqi.vanasframework.security.filter.LoginAutenticationProvider;
import com.liuqi.vanasframework.security.filter.SecurityDecisionManager;
import com.liuqi.vanasframework.security.filter.SecurityInterceptor;
import com.liuqi.vanasframework.security.service.SecurityMetadataSource;
import com.liuqi.vanasframework.security.service.VanasUserDetailServiceImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.util.Assert;

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
@EnableWebSecurity
public class VanasSecurityConfig extends WebSecurityConfigurerAdapter {

    VanasSecurityConfigAdapter vanasSecurityConfigAdapter;

    public VanasSecurityConfig(VanasSecurityConfigAdapter vanasSecurityConfigAdapter){
        this.vanasSecurityConfigAdapter = vanasSecurityConfigAdapter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        Assert.notNull(vanasSecurityConfigAdapter.getPermitURL(),"the PermitURL is require");

        http.addFilterAfter(getInterceptor(), FilterSecurityInterceptor.class);

        http.authorizeRequests()
                .antMatchers(vanasSecurityConfigAdapter.getPermitURL()).permitAll()
                .anyRequest().authenticated() // 其他地址的访问均需验证权限
                .and()
                .formLogin()
                .loginPage(vanasSecurityConfigAdapter.getLoginURL())
                .loginProcessingUrl(vanasSecurityConfigAdapter.getLoginFormURL())       // 登录action 提交的值
                .defaultSuccessUrl(vanasSecurityConfigAdapter.getLoginSuccessURL())     // 登录成功页面
                .failureUrl(vanasSecurityConfigAdapter.getLoginErrorURL()).permitAll();

        if(!vanasSecurityConfigAdapter.closeCookie()){
            http.rememberMe().tokenValiditySeconds(vanasSecurityConfigAdapter.getCookieValidSeconds());
        }

        if(vanasSecurityConfigAdapter.closeCsrf()){
            http.csrf().disable();
        }

        http.logout().permitAll().logoutSuccessUrl(vanasSecurityConfigAdapter.getLoginOutSuccessURL());    // 退出登录成功页面
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        VanasUserDetailService service = new VanasUserDetailServiceImpl(vanasSecurityConfigAdapter.getVanasSecurityAdapter());
        DaoAuthenticationConfigurer authenticationConfigurer =
                auth.userDetailsService(service);

        if(vanasSecurityConfigAdapter.getVanasPasswordVoter() == null){
            /*
             * Spring Security 提供了BCryptPasswordEncoder类,实现Spring的PasswordEncoder接口使用BCrypt强哈希方法来加密密码。
             * 即 密码 + 盐
             */
            authenticationConfigurer.passwordEncoder(new BCryptPasswordEncoder());
        }else{
            // 自定义密码加密方式
            authenticationConfigurer.passwordEncoder(new VanasPasswordEncoder(vanasSecurityConfigAdapter.getVanasPasswordVoter()));
        }
    }

    /**
     * 创建 security 的拦截器
     * @return 拦截器
     */
    private SecurityInterceptor getInterceptor(){
        SecurityInterceptor interceptor = new SecurityInterceptor();
        interceptor.setSecurityMetadataSource(new SecurityMetadataSource(vanasSecurityConfigAdapter.getVanasSecurityAdapter()));
        interceptor.setSystemAccessDecisionManager(new SecurityDecisionManager());
        return interceptor;
    }

}

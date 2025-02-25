package com.liuqi.vanasframework.security;

import com.liuqi.vanasframework.core.Vanas;
import com.liuqi.vanasframework.security.authentication.VanasUserLoginAuthenticationFilter;
import com.liuqi.vanasframework.security.authentication.VanasUserLoginAuthenticationProvider;
import com.liuqi.vanasframework.security.access.SecurityDecisionManager;
import com.liuqi.vanasframework.security.access.SecurityInterceptor;
import com.liuqi.vanasframework.security.access.SecurityMetadataSource;
import com.liuqi.vanasframework.security.entity.VanasSecurityConfigSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collections;

/**
 * 类说明 <br>
 * 通过@EnableWebSecurity注解开启Spring Security的功能
 * 继承 WebSecurityConfigurerAdapter，并重写它的方法来设置一些web安全的细节
 * configure(HttpSecurity http)方法，
 *      通过authorizeRequests()定义哪些URL需要被保护、哪些不需要被保护。
 *      例如以上代码指定了/和/home不需要任何认证就可以访问，其他的路径都必须通过身份验证。
 *      通过formLogin()定义当需要用户登录时候，转到的登录页面。
 *
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:13 PM 2020/3/4
 */
@EnableWebSecurity
@Log4j2
public class VanasSecurityWebConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    public void setVanasSecurityDaoService(VanasSecurityDaoService vanasSecurityDaoService) {
//        VanasSecurityConfigSource.getInstance().setCustomerDaoService(vanasSecurityDaoService);
//    }
//
//    @Autowired
//    public void setPasswordVoter(VanasPasswordVoter passwordVoter) {
//        VanasSecurityConfigSource.getInstance().setCustomerPasswordEncoder(passwordVoter);
//    }

//    public VanasSecurityWebConfig(VanasSecurityConfig vanasSecurityConfig){
//        // 转化为 security 的配置常量
//        VanasSecurityConfigSource.getInstance().init(vanasSecurityConfig);
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 转化为 security 的配置常量

        Assert.notNull(Vanas.customerConfig.getSecurity().getPermitUrl(),"the PermitURL is require");

        http.addFilterAfter(getInterceptor(), FilterSecurityInterceptor.class);

        http.addFilterAt(getUserLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        if(Vanas.customerConfig.getSecurity().getXFrameEnabled()){
            // 允许 frame
            setFrameAllow(http);
        }

        http.authorizeRequests()
                .antMatchers(Vanas.customerConfig.getSecurity().getPermitUrl()).permitAll()
                .anyRequest().authenticated() // 其他地址的访问均需验证权限
                .and()
                .formLogin()
                .loginPage(Vanas.customerConfig.getSecurity().getLoginUrl())
                .loginProcessingUrl(Vanas.customerConfig.getSecurity().getLoginFormUrl())       // 登录action 提交的值
                .defaultSuccessUrl(Vanas.customerConfig.getSecurity().getLoginSuccessUrl())     // 登录成功页面
                .failureUrl(Vanas.customerConfig.getSecurity().getLoginFailureUrl()).permitAll();

        if(!Vanas.customerConfig.getSecurity().isCookieEnabled()){
            http.rememberMe().tokenValiditySeconds(Vanas.customerConfig.getSecurity().getCookieValidSeconds());
        }

        if(!Vanas.customerConfig.getSecurity().isCsrfEnabled()){
            http.csrf().disable();
        }else{
            // 不关闭 csrf  开启 druid csrf 过滤
            http.csrf()
                    .ignoringAntMatchers(Vanas.customerConfig.getSecurity().getCsrfPermitUrl())
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
        }

        http.logout().permitAll().logoutSuccessUrl(Vanas.customerConfig.getSecurity().getLoginOutSuccessUrl());    // 退出登录成功页面
    }

    private void setFrameAllow(HttpSecurity http) throws Exception {

        if(Vanas.customerConfig.getSecurity().getXFrameOptions().equals("SAMEORIGIN")){
            // 仅允许本域名
            http.headers().frameOptions().sameOrigin();
        }else if(Vanas.customerConfig.getSecurity().getXFrameOptions().equals("FROMURI")){
            //disable 默认策略。 这一句不能省。
            http.headers().frameOptions().disable();
            //新增新的策略。
            http.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(
                    new WhiteListedAllowFromStrategy(Arrays.asList(Vanas.customerConfig.getSecurity().getXFrameAllowUri()))
            ));
        }else{
            throw new Exception("未知的 XFrameOptions 。仅支持 SAMEORIGIN , FROMURI");
        }


    }

    /**
     * 创建 security 的拦截器
     * @return 拦截器
     */
    private SecurityInterceptor getInterceptor(){
        log.info("安全组件 >> 获取全局拦截器");

        SecurityInterceptor interceptor = new SecurityInterceptor();
        interceptor.setSecurityMetadataSource(new SecurityMetadataSource(VanasSecurityConfigSource.getInstance().getCustomerDaoService()));
        interceptor.setSystemAccessDecisionManager(new SecurityDecisionManager());
        return interceptor;
    }

    /**
     * 创建 security Login 过滤器
     * @return 过滤器
     */
    private VanasUserLoginAuthenticationFilter getUserLoginAuthenticationFilter(){
        log.info("安全组件 >> 获取登陆安全认证过滤器");

        VanasUserLoginAuthenticationFilter vanasUserLoginAuthenticationFilter = new VanasUserLoginAuthenticationFilter();

        ProviderManager providerManager = new ProviderManager(Collections.singletonList(getUserLoginAuthenticationProvider()));
        vanasUserLoginAuthenticationFilter.setAuthenticationManager(providerManager);
        return vanasUserLoginAuthenticationFilter;
    }

    /**
     * 创建 security Login 处理登陆数据的 Provider
     * @return 拦截器
     */
    private VanasUserLoginAuthenticationProvider getUserLoginAuthenticationProvider(){
        log.info("安全组件 >> 获取登陆安全认证处理器");
        return new VanasUserLoginAuthenticationProvider();
    }

}

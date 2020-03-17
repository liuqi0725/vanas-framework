package com.liuqi.vanasframework.security.authentication;

import com.liuqi.vanasframework.core.Vanas;
import com.liuqi.vanasframework.core.exception.AppSecurityException;
import com.liuqi.vanasframework.security.entity.SecurityUser;
import com.liuqi.vanasframework.security.entity.VanasSecurityConfigSource;
import com.liuqi.vanasframework.security.service.VanasSecurityDaoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 类说明 <br>
 *     1. 用户登录的自定义认证的处理<br>
 *     2. 获取 Filter 封装的 token 信息<br>
 *     3. 通过 UserDetailService 获取用户信息【数据库】<br>
 *     4. 判断是否通过验证<br>
 *     5. 通过则重新创建一个新的 token ，设置已经通过验证，并返回给 manager<br>
 *
 * @author : alexliu
 * @version v1.0 , Create at 2:11 PM 2020/3/10
 */
@Log4j2
public class VanasUserLoginAuthenticationProvider implements AuthenticationProvider {

    private VanasSecurityDaoService daoService;

    private PasswordEncoder passwordEncoder;

    /**
     * 构造函数传第 自定义 userDetailService
     */
    public VanasUserLoginAuthenticationProvider(){
        daoService = VanasSecurityConfigSource.getInstance().getCustomerDaoService();
        passwordEncoder = VanasSecurityConfigSource.getInstance().getPasswordEncoder();
    }

    /**
     * 认证
     * @param authentication 用户登录的认证信息
     * @return 认证结果
     * @throws AuthenticationException 认证异常
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取过滤器封装的token信息
        VanasUserLoginAuthenticationToken authenticationToken = (VanasUserLoginAuthenticationToken) authentication;

        SecurityUser user = null;
        try {
            if(Vanas.customerConfig.isVcEnabled()){
                user = daoService.loadUserByUsername((String) authenticationToken.getPrincipal(),(String)authenticationToken.getVc());
            }else{
                user = daoService.loadUserByUsername((String) authenticationToken.getPrincipal());
            }
        }catch (AppSecurityException e){
            throw new BadCredentialsException("系统异常，请稍后再试。");
        }

        //不通过
        if (user == null) {
            throw new BadCredentialsException("帐号不存在。");
        }

        // 检验密码
        String inputPassoword = authenticationToken.getCredentials().toString();

        if(!passwordEncoder.matches(inputPassoword , user.getPassword())){
            log.debug("Authentication failed: password does not match stored value");

            throw new BadCredentialsException("密码错误。");
        }

        // 密码通过 获取用户权限组成认证用户
        //获取用户信息（数据库认证）
        UserDetails userDetails = null;
        try {
            userDetails = daoService.getUserAuthorities(user);
        }catch (AppSecurityException e){
            throw new BadCredentialsException("获取用户权限错误，请稍后再试。");
        }

        //通过
        VanasUserLoginAuthenticationToken authenticationResult = new VanasUserLoginAuthenticationToken(userDetails, userDetails.getAuthorities());

        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    /**
     * 由 提供给 spring-security AuthenticationManager <br>
     * 根据 token 类型，判断使用那个 Provider
     * @param authentication 提供manager 识别认证器
     * @return 是否匹配
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return VanasUserLoginAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

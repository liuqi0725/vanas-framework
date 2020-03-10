package com.liuqi.vanasframework.security.filter;

import com.liuqi.vanasframework.security.access.VanasUserDetailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 类说明 <br>
 * <p>
 * 构造说明 :
 * <pre>
 *
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 2:11 PM 2020/3/10
 */
@Log4j2
public class LoginAutenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    VanasUserDetailService userDetailsService;

    public LoginAutenticationProvider(VanasUserDetailService userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        // 获取表单中所有值
        usernamePasswordAuthenticationToken.getDetails();
    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

        userDetailsService.loadUserByUsername("","");

        return null;
    }
}

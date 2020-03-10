package com.liuqi.vanasframework.security.filter;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类说明 <br>
 * <p>
 * 构造说明 :
 * <pre>
 *
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 3:30 PM 2020/3/10
 */
public class VanasUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

//        if (postOnly && !request.getMethod().equals("POST")) {
//            throw new AuthenticationServiceException(
//                    "Authentication method not supported: " + request.getMethod());
//        }
//
//        String username = obtainUsername(request);
//        String password = obtainPassword(request);
//        String vcid = obtainVcid(request);
//
//        if (username == null) {
//            username = "";
//        }
//
//        if (password == null) {
//            password = "";
//        }
//
//        if (vcid == null) {
//            vcid = "";
//        }
//
//        username = username.trim();
//        password = password.trim();
//        vcid = vcid.trim();
//
//        username = vcid + USERNAME_LOGINID_SPLIT + username; // vcid 与 username 链接
//
//        //主要通过这个token来决定使用哪个userDetailService.
//        //UserDetailsAuthenticationProvider里面有个supports方法,主要用来验证指定的token是否符合.
//        //可以通过指定不同类型的token来决定使用哪个userDetailService.
//        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
//        setDetails(request, authRequest);
//        return this.getAuthenticationManager().authenticate(authRequest);

        return super.attemptAuthentication(request, response);
    }
}

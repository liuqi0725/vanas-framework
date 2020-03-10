package com.liuqi.vanasframework.security.filter;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 类说明 <br>
 *  用用户名，账号密码登陆
 *
 * @author : alexliu
 * @version v1.0 , Create at 1:59 PM 2020/3/10
 */
public class UsernameVcidAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
    private static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    private static final String SPRING_SECURITY_FORM_VCID_KEY = "vcid";

    private static final String USERNAME_LOGINID_SPLIT = "|";

    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private String vcidParameter = SPRING_SECURITY_FORM_VCID_KEY;
    private boolean postOnly = true;

    public UsernameVcidAuthenticationFilter() {
        super("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String vcid = obtainVcid(request);

        if (username == null) {
            username = "";
        }

        if (password == null) {
            password = "";
        }

        if (vcid == null) {
            vcid = "";
        }

        username = username.trim();
        password = password.trim();
        vcid = vcid.trim();

        username = vcid + USERNAME_LOGINID_SPLIT + username; // vcid 与 username 链接

        //主要通过这个token来决定使用哪个userDetailService.
        //UserDetailsAuthenticationProvider里面有个supports方法,主要用来验证指定的token是否符合.
        //可以通过指定不同类型的token来决定使用哪个userDetailService.
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }


    /**
     * Enables subclasses to override the composition of the username, such as by including additional values
     * and a separator.
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the username that will be presented in the <code>Authentication</code> request token to the
     *         <code>AuthenticationManager</code>
     */
    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter(usernameParameter);
    }

    /**
     * Enables subclasses to override the composition of the password, such as by including additional values
     * and a separator.<p>This might be used for example if a postcode/zipcode was required in addition to the
     * password. A delimiter such as a pipe (|) should be used to separate the password and extended value(s). The
     * <code>AuthenticationDao</code> will need to generate the expected password in a corresponding manner.</p>
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the password that will be presented in the <code>Authentication</code> request token to the
     *         <code>AuthenticationManager</code>
     */
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(passwordParameter);
    }

    protected String obtainVcid(HttpServletRequest request){
        return request.getParameter(vcidParameter);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication request's details
     * property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details set
     */
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

}

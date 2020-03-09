package com.liuqi.vanasframework.security;

import com.liuqi.vanasframework.security.entity.SecurityUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 类说明 <br>
 * <p>
 *
 *
 *
 *
 *
 * @author : alexliu
 * @version v1.0 , Create at 3:59 PM 2020/3/2
 */
public class SecurityUtil {

    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * 用 spring-security BCryptPasswordEncoder 加密密码
     * @param var1 密码
     * @return 加密后的密码
     */
    public static String encode(String var1){
        return bCryptPasswordEncoder.encode(var1);
    }

    /**
     * 是否登陆
     * @return 是否登陆
     */
    public static boolean isLogin(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return auth instanceof AnonymousAuthenticationToken;
    }

    /**
     * 获取用户信息
     * @return User 对象 extends SecurityUser
     */
    public static Object getUserInfo(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 用户是否有权限
     * @param permissionKey 权限 key
     * @return 是否有权限
     */
    public static boolean hasPermission(String permissionKey){

        for (GrantedAuthority authority :SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
            if(authority.getAuthority().equals(permissionKey)){
                return true;
            }
        };

        return false;
    }
}

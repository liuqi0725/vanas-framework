package com.liuqi.vanasframework.security.access;

import com.liuqi.vanasframework.security.entity.SecurityUser;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 类说明 <br>
 * <p>
 *
 *
 *
 *
 *
 * @author : alexliu
 * @version v1.0 , Create at 9:09 PM 2020/3/1
 */
public interface VanasUserDetailService extends UserDetailsService {

    SecurityUser loadUserByUsername(String userName, String vcid);

}

package com.liuqi.vanasframework.security.service;

import com.liuqi.vanasframework.security.access.VanasSecurityDataSourceAdapter;
import com.liuqi.vanasframework.security.entity.SecurityUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

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
public class UserDetailService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(UserDetailService.class);

    private VanasSecurityDataSourceAdapter vanasSecurityAdapter;

    /**
     * 由外部系统实现后注入,外部系统，需要实现 {@code VanasSecurityAdapter} 让安全组件获取判断数据
     * @param vanasSecurityAdapter 安全组件适配器
     */
    public UserDetailService(VanasSecurityDataSourceAdapter vanasSecurityAdapter){
        Assert.notNull(vanasSecurityAdapter,"an vanasSecurityAdapter is require!");
        this.vanasSecurityAdapter = vanasSecurityAdapter;
    }

    @Override
    public SecurityUser loadUserByUsername(String userName) throws UsernameNotFoundException {
        return vanasSecurityAdapter.loadUserByUsername(userName);
    }

}

package com.liuqi.vanasframework.security.access;

import com.liuqi.vanasframework.security.entity.SecurityPermission;
import com.liuqi.vanasframework.security.entity.SecurityUser;

import java.util.List;

/**
 * 类说明 <br>
 * <p>
 *
 *
 *
 *
 *
 * @author : alexliu
 * @version v1.0 , Create at 9:18 PM 2020/3/4
 */
public interface VanasSecurityDataSourceAdapter {

    <T extends SecurityPermission> List<T> getSecurityFilterNeedPermissions();

    SecurityUser loadUserByUsername(String userName);

}

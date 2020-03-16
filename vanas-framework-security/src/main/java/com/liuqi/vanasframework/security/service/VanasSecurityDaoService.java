package com.liuqi.vanasframework.security.service;

import com.liuqi.vanasframework.security.entity.SecurityPermission;
import com.liuqi.vanasframework.security.entity.SecurityUser;

import java.util.List;

/**
 * 类说明 <br>
 *     Vanas 与数据库相关的业务。由第三方实现
 *
 * @author : alexliu
 * @version v1.0 , Create at 9:18 PM 2020/3/4
 */
public interface VanasSecurityDaoService {

    <T extends SecurityPermission> List<T> getAllPermission();

    SecurityUser loadUserByUsername(String userName);

    SecurityUser loadUserByUsername(String userName,String vcid);
}

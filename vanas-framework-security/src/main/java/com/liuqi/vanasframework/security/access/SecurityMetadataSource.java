package com.liuqi.vanasframework.security.access;

import com.liuqi.vanasframework.security.entity.SecurityPermission;
import com.liuqi.vanasframework.security.service.VanasSecurityDaoService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类说明 <br>
 *     自定义过滤规则,将用户request请求的url 与数据库权限进行，
 *     有匹配则将该 url 及 url所需要的权限 返回给 decide()【决策】方法，不存在则返回空
 *
 * @author : alexliu
 * @version v1.0 , Create at 7:56 PM 2020/3/2
 */
public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private Map<AntPathRequestMatcher,Collection<ConfigAttribute>> permissionMap;

    private VanasSecurityDaoService vanasSecurityAdapter;

    public SecurityMetadataSource(VanasSecurityDaoService vanasSecurityAdapter){
        Assert.notNull(vanasSecurityAdapter,"an vanasSecurityAdapter is require!");
        this.vanasSecurityAdapter = vanasSecurityAdapter;
    }

    private void loadPermissionMap(){
        List<? extends SecurityPermission> list = vanasSecurityAdapter.getAllPermission();

        permissionMap = new HashMap<>();
        for(SecurityPermission p : list){
            // 如果权限 url 不为空 添加 url
            if(!StringUtils.isEmpty(p.getUrl())){
                permissionMap.put(new AntPathRequestMatcher(p.getUrl()) , SecurityConfig.createList(p.getUnKey()));
            }
        }

    }

    /**
     * 获取对比结果
     * @param o 可以包含了本次请求的所有内容，包含 HttpRequest 等
     * @return URL访问需要的认证集合
     * @throws IllegalArgumentException 参数错误
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {

        if(permissionMap == null){
            // 读取所有权限
            loadPermissionMap();
        }

        Assert.notNull(permissionMap , "PermissionMap can`t be Null.check your code");

        FilterInvocation fi = (FilterInvocation)o;
        for(AntPathRequestMatcher permissionURLMatcher : permissionMap.keySet()){

            if(permissionURLMatcher.matches(fi.getRequest())){
                // 返回 要访问此 URL 的权限及其他判断条件
                return permissionMap.get(permissionURLMatcher);
            }
        }
        // 没匹配到返回 null
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}

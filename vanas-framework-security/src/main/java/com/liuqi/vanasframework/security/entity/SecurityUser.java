package com.liuqi.vanasframework.security.entity;

import com.liuqi.vanasframework.core.mvc.entity.PageBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
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
 * @version v1.0 , Create at 11:37 PM 2020/3/1
 */
public abstract class SecurityUser extends PageBean implements UserDetails {

    private static final long serialVersionUID = 7366180833051764814L;

    protected String username;

    protected String password;

    protected List<Integer> roleIds;

    protected boolean isSuperAdmin;

    protected Collection<? extends GrantedAuthority> authorities;

    protected Collection<? extends SecurityPermission> permissions;


    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public boolean isSuperAdmin() {
        return isSuperAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }

    /**
     * 转化用户拥有的权限为 springsecurity 权限的对象
     * @param userGrantedPermissionList 用户权限集合
     */
    public void appendUserGrantedPermissions(List<? extends SecurityPermission> userGrantedPermissionList){
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        List<SecurityPermission> pList = new ArrayList<>();

        for(SecurityPermission p : userGrantedPermissionList) {
            list.add(new SimpleGrantedAuthority(p.getUnKey()));
            pList.add(p);
        }
        this.permissions = pList;
        this.authorities = list;
    }

    @SuppressWarnings("unchecked")
    public void appendUserGrantedPermission(SecurityPermission userGrantedPermission) {
        List<SimpleGrantedAuthority> list;
        List<SecurityPermission> pList;

        if(getAuthorities() == null){
            list = new ArrayList<>();
            pList = new ArrayList<>();
        }else{
            list = (List<SimpleGrantedAuthority>)getAuthorities();
            pList = (List<SecurityPermission>)getPermission();
        }
        SimpleGrantedAuthority auth = new SimpleGrantedAuthority(userGrantedPermission.getUnKey());
        list.add(auth);
        pList.add(userGrantedPermission);

        this.authorities = list;
        this.permissions = pList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public Collection<? extends SecurityPermission> getPermission(){
        return permissions;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }



    /**
     * 是否过期，不添加，redis 无法反序列化
     */
    private boolean accountNonExpired;

    /**
     * 是否锁定，不添加，redis 无法反序列化
     */
    private boolean accountNonLocked;

    /**
     * 密码是否过期，不添加，redis 无法反序列化
     */
    private boolean credentialsNonExpired;

    /**
     * 是否激活，不添加，redis 无法反序列化
     */
    private boolean enabled;

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    @Override
    public boolean isAccountNonExpired() {
        // 账号是否 未过期
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 账号是否 未锁定
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 密码是 否未过期
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        // 是否激活
        return this.enabled;
    }
}

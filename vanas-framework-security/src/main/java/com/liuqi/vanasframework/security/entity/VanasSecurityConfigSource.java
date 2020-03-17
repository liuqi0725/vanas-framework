package com.liuqi.vanasframework.security.entity;

import com.liuqi.vanasframework.security.crypto.VanasPasswordEncoder;
import com.liuqi.vanasframework.security.crypto.VanasPasswordVoter;
import com.liuqi.vanasframework.security.service.VanasSecurityDaoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

/**
 * 类说明 <br>
 *     安全组件与第三方链接 source 单例类
 *
 * @author : alexliu
 * @version v1.0 , Create at 15:04 2020-03-16
 */
@Log4j2
public class VanasSecurityConfigSource {

    /**
     * 密码加解密方式
     */
    private PasswordEncoder passwordEncoder;

    /**
     * 第三方实现 dao service
     */
    private VanasSecurityDaoService  customerDaoService;


    /**
     * 不能通过 new 创建实例
     */
    private VanasSecurityConfigSource(){

    }

    public static VanasSecurityConfigSource getInstance(){
        return VanasSecurityConfigSourceHook.instance;
    }

    private static class VanasSecurityConfigSourceHook{
        private static final VanasSecurityConfigSource instance = new VanasSecurityConfigSource();
    }

    public VanasSecurityDaoService getCustomerDaoService() {
        return customerDaoService;
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    /**
     * 获取登陆认证客户 与 VanasSecurity 的数据库实现层
     * @param customerDaoService {@link VanasSecurityDaoService} 由第三方实现
     */
    public void setCustomerDaoService(VanasSecurityDaoService customerDaoService) {
        log.info("安全组件配置 >> 获取登陆认证客户 与 VanasSecurity 的数据库实现层");

        Assert.notNull(customerDaoService , "VanasSecurityDaoService 不能为空。请在系统中实现 VanasSecurityDaoService，并在 VanasSecurityConfig 实现的初始化中注入。");
        this.customerDaoService = customerDaoService;
    }

    /**
     * 设置密码生成策略
     * @param customerPasswordVoter {@link PasswordEncoder} 由第三方实现，可为空
     */
    public void setCustomerPasswordEncoder(VanasPasswordVoter customerPasswordVoter){

        log.info("安全组件配置 >> 获取登陆安全认证的密码认证器");

        if(customerPasswordVoter == null || !customerPasswordVoter.enabled()){
            log.info("安全组件配置 >> 获取登陆安全认证的密码认证器 ， 使用系统默认 BCryptPasswordEncoder");

            /*
             * Spring Security 提供了BCryptPasswordEncoder类,实现Spring的PasswordEncoder接口使用BCrypt强哈希方法来加密密码。
             * 即 密码 + 盐
             */
            this.passwordEncoder = new BCryptPasswordEncoder();
        }else{
            log.info("安全组件配置 >> 获取登陆安全认证的密码认证器 ， 使用系统自定义 VanasPasswordEncoder");
            // 自定义密码加密方式
            this.passwordEncoder = new VanasPasswordEncoder(customerPasswordVoter);
        }
    }
}

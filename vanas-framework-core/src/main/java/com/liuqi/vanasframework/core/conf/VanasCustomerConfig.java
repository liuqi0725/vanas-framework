package com.liuqi.vanasframework.core.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 类说明 <br>
 *     用户自定义准字行
 *
 * @author : alexliu
 * @version v1.0 , Create at 09:41 2020-03-17
 */
@Component
@Validated
@Data
@ConfigurationProperties(prefix = "vanas" , ignoreUnknownFields=true)
public class VanasCustomerConfig implements Serializable {

    private static final long serialVersionUID = 2821054520945565401L;

    /**
     * 系统当前环境
     */
    @NotEmpty
    private String environment;

    /**
     * 是否允许 vc 登录
     */
    private boolean vcEnabled = Boolean.FALSE;

    /**
     * 模板引擎
     */
    private String viewEngine = "freemarker";

    /**
     * 超级管理员角色
     */
    @NotEmpty
    private String superAdminRole;

    /**
     * 安全配置
     */
    @NestedConfigurationProperty
    private VanasSecurityConfig security;
}

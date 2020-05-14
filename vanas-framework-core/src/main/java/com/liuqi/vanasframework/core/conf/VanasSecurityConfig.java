package com.liuqi.vanasframework.core.conf;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 类说明 <br>
 *     用户自定义准字行
 *
 * @author : alexliu
 * @version v1.0 , Create at 09:41 2020-03-17
 */
@Data
public class VanasSecurityConfig implements Serializable {

    private static final long serialVersionUID = 2821054520945565401L;

    /**
     * 白名单 "," 分割; 白名单中的地址不用认证用户
     */
    @NotEmpty
    private String[] permitUrl;

    /**
     * 是否开启 csrf 验证
     */
    private boolean csrfEnabled = Boolean.TRUE;

    /**
     * 白名单 "," 分割; 白名单中的地址form 提交不验证 csrf; 当 csrf-enabled 为 false 时不生效
     */
    private String[] csrfPermitUrl;

    /**
     * 非必填】登陆 url GET 返回登录视图 ，【默认 /login】
     */
    private String loginUrl = "/login";

    /**
     * 【非必填】登陆 url POST 登陆提交的 form，在前端from action 中的值。默认提交 vanas.security 用户认证 【默认 /login】
     */
    private String loginFormUrl = "/login";

    /**
     * 【非必填】登录成功跳转的地址 【默认 /dashboard】
     */
    private String loginSuccessUrl = "/dashboard";

    /**
     * 【非必填】登录失败跳转的地址 【默认 /login?error】
     */
    private String loginFailureUrl = "/login?error";

    /**
     * 【非必填】退出登录后跳转的地址 【默认 /login】
     */
    private String loginOutSuccessUrl = "/login";

    /**
     * 【非必填】是否开启 cookie【默认 true】
     */
    private boolean cookieEnabled = Boolean.TRUE;

    /**
     * 【非必填】cookie 有效期，单位：秒 【默认 2 天】
     */
    private Integer cookieValidSeconds = 172800;

    public void setPermitUrl(String permitUrl) {
        this.permitUrl = getStringArray(permitUrl);
    }

    public void setCsrfPermitUrl(String csrfPermitUrl) {
        this.csrfPermitUrl = getStringArray(csrfPermitUrl);
    }

    private String[] getStringArray(String attrVal){
        if(StringUtils.isEmpty(attrVal)){
            return null;
        }
        String[] attrArray = attrVal.split(",");

        List<String> finalList = new ArrayList<>();
        // 排除空值，前后去空格
        for(String attr : attrArray){
            if(StringUtils.isEmpty(attr)){
                continue;
            }
            finalList.add(attr.trim());
        }
        return (String[])finalList.toArray();
    }
}

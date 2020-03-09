package com.liuqi.vanasframework.security.crypto;


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
public interface VanasPasswordVoter {

    /**
     * 加密
     * @param password 用户输入密码
     * @return 加密后字符串
     */
    public String encode(CharSequence password);

    /**
     * 对比密码是否相等
     * @param password 用户输入的密码
     * @param encodedPassword 已加密的密码
     * @return 是否相等
     */
    public boolean matches(CharSequence password, String encodedPassword);

}

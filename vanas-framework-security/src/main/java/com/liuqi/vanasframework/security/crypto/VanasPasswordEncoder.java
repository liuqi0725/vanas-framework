package com.liuqi.vanasframework.security.crypto;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 类说明 <br>
 *     自定义密码处理解析
 * <p>
 *
 *
 *
 *
 *
 * @author : alexliu
 * @version v1.0 , Create at 9:49 PM 2020/3/1
 */
public class VanasPasswordEncoder implements PasswordEncoder {

    private VanasPasswordVoter voter;

    public VanasPasswordEncoder(VanasPasswordVoter voter){
        this.voter = voter;
    }

    /**
     * 密码加密
     * @param password 用户输入的密码
     * @return 加密后的字符串
     */
    @Override
    public String encode(CharSequence password) {
        // 加密
        return voter.encode(password);
    }

    /**
     * 密码对比
     * @param password 用户输入的密码
     * @param encodedPassword 已加密的密码
     * @return 是否相等
     */
    @Override
    public boolean matches(CharSequence password , String encodedPassword) {
        return voter.matches(password,encodedPassword);
    }
}

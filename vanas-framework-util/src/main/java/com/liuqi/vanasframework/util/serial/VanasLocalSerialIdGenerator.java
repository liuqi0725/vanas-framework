package com.liuqi.vanasframework.util.serial;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 类说明 <br>
 * <p>
 *     完全依赖本地创建唯一编码，不依赖内存，无任何IO操作
 * </p>
 *
 * @author : lau.Q
 * @version v1.0 , Create at 2025/6/25 10:25
 */
@Component
public class VanasLocalSerialIdGenerator implements VanasSerialGenerator{

    @Override
    public String nextId(String bizType) {
        return this.nextId(bizType, 3);
    }

    @Override
    public String nextId(String bizType, int prefixLength) {
        return this.nextId(bizType, 3, "yyyyMMddHHmmssSSS");
    }

    @Override
    public String nextId(String bizType, int prefixLength, String dateFormat) {
        return this.nextId(bizType, 3, "yyyyMMddHHmmssSSS", 6);
    }

    private String nextId(String bizType, int prefixLength, String dateFormat, int serialLength) {
        if(StringUtils.isEmpty(bizType)){
            throw new NullPointerException("生成序列号，业务类型不能为空");
        }

        // 1. 处理业务前缀（只有长度>prefixLength时才截取）
        String bizPrefix = bizType.length() > prefixLength
                ? StrUtil.subPre(bizType, prefixLength).toUpperCase()
                : bizType.toUpperCase();

        // 2. 获取当前时间戳(精确到毫秒)
        String timestamp = DateUtil.format(new Date(), dateFormat);

        // 3. 生成随机后缀
        String randomSuffix = RandomUtil.randomNumbers(serialLength);

        // 4. 组合成完整ID
        return StrUtil.format("{}{}{}", bizPrefix, timestamp, randomSuffix);
    }
}

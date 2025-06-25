package com.liuqi.vanasframework.util.serial;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.liuqi.vanasframework.util.VanasRedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 类说明 <br>
 * <p>
 * </p>
 *
 * @author : lau.Q
 * @version v1.0 , Create at 2025/6/25 10:41
 */
@Component
public class VanasRedisSerialIdGenerator implements VanasSerialGenerator{

    private final VanasRedisCacheUtil redisCacheUtil;

    @Autowired
    public VanasRedisSerialIdGenerator(VanasRedisCacheUtil redisCacheUtil) {
        this.redisCacheUtil = redisCacheUtil;
    }


    @Override
    public String nextId(String bizType) {
        return nextId(bizType, 3);
    }

    @Override
    public String nextId(String bizType, int prefixLength) {
        return nextId(bizType, prefixLength, "yyyyMMddHHmmssSSS");
    }

    @Override
    public String nextId(String bizType, int prefixLength, String dateFormat) {
        return nextId(bizType, prefixLength, dateFormat, 6); // 默认4位数
    }

    private String nextId(String bizType, int prefixLength, String dateFormat, int serialLength) {
        if (StrUtil.isEmpty(bizType)) {
            throw new NullPointerException("生成序列号，业务类型不能为空");
        }

        // 1. 截取前缀
        String bizPrefix = bizType.length() > prefixLength
                ? StrUtil.subPre(bizType, prefixLength).toUpperCase()
                : bizType.toUpperCase();

        // 2. 时间戳
        String timestamp = DateUtil.format(new Date(), dateFormat);

        // 3. Redis 自增键
        String redisKey = StrUtil.format("vanas:serial:data_{}", bizPrefix);
        // 设置过期时间为 2 天，防止 Redis 永久增长
        long serial = redisCacheUtil.increment(redisKey, 2, TimeUnit.DAYS);

        // 4. 格式化序列号（不足位补0）
        String serialStr = StrUtil.padPre(String.valueOf(serial), serialLength, '0');

        // 5. 拼接完整 ID
        return StrUtil.format("{}{}{}", bizPrefix, timestamp, serialStr);
    }
}

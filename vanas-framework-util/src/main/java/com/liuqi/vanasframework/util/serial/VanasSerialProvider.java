package com.liuqi.vanasframework.util.serial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 类说明 <br>
 * <p>
 *     序列号提供者，工厂模式
 * </p>
 *
 * @author : lau.Q
 * @version v1.0 , Create at 2025/6/25 10:23
 */
@Component
public class VanasSerialProvider {

    private final VanasRedisSerialIdGenerator redisSerialIdGenerator;

    private final VanasLocalSerialIdGenerator localSerialIdGenerator;

    @Autowired
    public VanasSerialProvider(VanasLocalSerialIdGenerator localSerialIdGenerator,
                               VanasRedisSerialIdGenerator redisSerialIdGenerator){
        this.localSerialIdGenerator = localSerialIdGenerator;
        this.redisSerialIdGenerator = redisSerialIdGenerator;
    }


    public VanasSerialGenerator getRedisIdGenerator(){
        return this.redisSerialIdGenerator;
    }

    public VanasSerialGenerator getLocalIdGenerator(){
        return this.localSerialIdGenerator;
    }

}

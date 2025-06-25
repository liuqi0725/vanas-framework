package com.liuqi.vanasframework.util.serial;

/**
 * 类说明 <br>
 * <p>
 *     序列号生成接口
 * </p>
 *
 * @author : lau.Q
 * @version v1.0 , Create at 2025/6/25 10:23
 */
public interface VanasSerialGenerator {

    /**
     * <p>
     * 生成无状态唯一ID.   <br/>
     * 业务前缀长度为 3    <br/>
     * 序列号长度： 6 位     <br/>
     * 日期格式为 yyyyMMddHHmmssSSS
     * </P>
     * @param bizType 业务类型(如: ORDER)
     * @return 例如: ORD202406241530452934567
     */
    String nextId(String bizType);

    /**
     * <p>
     * 生成无状态唯一ID.  <br/>
     * 序列号长度： 6 位     <br/>
     * 日期格式为 yyyyMMddHHmmssSSS
     * </P>
     * @param bizType 业务类型(如: ORDER)
     * @param prefixLength 业务前缀长度(如: 3 则会截取 bizType 前3位)
     * @return 例如: ORD202406241530452934567
     */
    String nextId(String bizType, int prefixLength);

    /**
     * <p>
     * 生成无状态唯一ID.  <br/>
     * 序列号长度： 6 位     <br/>
     * </p>
     * @param bizType 业务类型(如: ORDER)
     * @param prefixLength 业务前缀长度(如: 3 则会截取 bizType 前3位)
     * @param dateFormat 日期格式(如: yyyyMMddHHmmssSSS)
     * @return 例如: ORD202406241530452934567
     */
    String nextId(String bizType, int prefixLength, String dateFormat);

}

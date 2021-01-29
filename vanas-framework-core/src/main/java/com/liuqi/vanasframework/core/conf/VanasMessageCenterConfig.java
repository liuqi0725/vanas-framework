package com.liuqi.vanasframework.core.conf;

import lombok.Data;

import java.io.Serializable;


/**
 * 类说明 <br>
 *     消息中心，用户自定义
 *
 * @author : alexliu
 * @version v1.0 , Create at 09:41 2020-03-17
 */
@Data
public class VanasMessageCenterConfig implements Serializable {

    private static final long serialVersionUID = 6749745481728589061L;

    /**
     * 启用消息中心
     */
    private Boolean enable = Boolean.FALSE;

    /**
     * 检查新消息的间隔时间
     */
    private Integer checkNewMsgEachTime = 2*60*1000;

    /**
     * 检查新消息 URL
     */
    private String checkNewMsgURL = "";
}

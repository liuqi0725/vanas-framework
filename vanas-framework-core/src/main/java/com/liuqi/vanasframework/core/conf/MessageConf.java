package com.liuqi.vanasframework.core.conf;

/**
 * 类说明 <br>
 * <p>
 *
 *
 *
 *
 *
 * @author : alexliu
 * @version v1.0 , Create at 5:27 PM 2019/12/11
 */
public enum MessageConf {

    /**
     * 通知
     */
    NOTICE(1 , "通知"),

    /**
     * 私信
     */
    PRIVATE(2 , "私信"),


    /**
     * 未读
     */
    NOT_READ(1 , "未读"),

    /**
     * 已读
     */
    HAS_READ(2, "已读")
    ;


    private Integer val;

    private String description;

    private MessageConf(Integer val ,String description){
        this.val = val;
        this.description = description;
    }

    public Integer getVal() {
        return val;
    }

    public String getDescription() {
        return description;
    }

    public static String getDescription(Integer val) {

        for(MessageConf conf : MessageConf.values()){
            if(val.equals(conf.val)){
                return conf.description;
            }
        }

        return null;
    }
}

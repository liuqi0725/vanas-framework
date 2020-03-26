package com.liuqi.vanasframework.core.conf.norm;

/**
 * 类说明 <br>
 * 状态值标准
 *
 * 用户可继承该类，以扩展状态类型
 *
 * @author : alexliu
 * @version v1.0 , Create at 1:26 PM 2020/3/6
 */
public class DataStatus {

    /**
     * 数据库配置状态-正常 default value is 1
     */
    public static final Integer STATUS_NORMAL = 1;

    /**
     * 数据库配置状态-停用 default value is 98
     */
    public static final Integer STATUS_STOP = 98;

    /**
     * 数据库配置状态-废弃[软删] default value is 99
     */
    public static final Integer STATUS_DELETE = 99;

    /**
     * 数据库配置状态-完结 default value is 100
     */
    public static final Integer STATUS_OVER = 100;




    /**
     * 是否值的状态值 ： 是 default value is 1
     */
    public static final Integer BOOLEAN_STATUS_YES = 1;

    /**
     * 是否值的状态值 ： 否 default value is 1
     */
    public static final Integer BOOLEAN_STATUS_NO = 0;
}

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
 * @version v1.0 , Create at 2:55 PM 2019/12/6
 */
public class CommonConf {

    /**
     * 文本换行符 End of Line
     */
    public static String EOL = System.getProperty("line.separator");

    /**
     * 路劲分割符  End of FilePath
     */
    public static String EOF = System.getProperties().getProperty("file.separator");

    /**
     * 请求返回页面
     */
    public static final String REQUEST_RETURN_VIEW = "method_return_is_view";

}

package com.liuqi.vanasframework.core.util;

import cn.hutool.core.util.IdUtil;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * 类说明 <br>
 * <p>
 *  无
 *
 * @author : alexliu
 * @version v1.0 , Create at 2:00 PM 2019/12/9
 */
public class WebUtils {


    private static ServletRequestAttributes getServletRequestAttributes(){
        return (ServletRequestAttributes)getRequestAttributes();
    }

    private static RequestAttributes getRequestAttributes(){
        return RequestContextHolder.getRequestAttributes();
    }

    /**
     * 获取 request
     * @return {@link HttpServletRequest}
     */
    public static HttpServletRequest getRequest(){
        return getServletRequestAttributes().getRequest();
    }

    /**
     * 获取 response
     * @return {@link HttpServletResponse}
     */
    public static HttpServletResponse getResponse(){
        return getServletRequestAttributes().getResponse();
    }

    /**
     * 获取 session
     * @return {@link HttpSession}
     */
    public static HttpSession getSession(){
        return getRequest().getSession();
    }

    /**
     * 获取 session id
     * @return {@link String}
     */
    public static String getSessionId(){
        return getRequest().getSession().getId();
    }

    /**
     * 设置session属性
     * @param key 键
     * @param data 值
     */
    public static void setSessionAttribute(String key , Object data) {
        getSession().setAttribute(key, data);
    }

    /**
     * 设置session属性
     * @param key 键
     * @param data 值
     */
    public static void setSessionAttribute(Enum<?> key , Object data) {
        getSession().setAttribute(key.name(), data);
    }

    /**
     * 获取session属性
     * @param key 键
     * @return {@link Object}
     */
    public static Object getSessionAttribute(String key) {
        return getSession().getAttribute(key);
    }

    /**
     * 获取session属性
     * @param key 键
     * @return {@link Object}
     */
    public static Object getSessionAttribute(Enum<?> key) {
        return getSession().getAttribute(key.name());
    }

    /**
     * 根据用户 id 创建 session key
     * @param userId 用户 id
     * @param keyName key 名称
     * @return {String} SESSION_USER_[userId]_[keyName] 结构的 sessionKEY
     */
    public static String createUserSessionKey(Integer userId , String keyName){

        return "SESSION_USER_" + userId + "_" + keyName;
    }

    /**
     * 创建唯一的 session key
     * 使用 UUID
     */
    private static String createUniqueSessionKey(){
        return UUID.randomUUID().toString();
    }

    private final static String REQUEST_ID_KEY = "REQUEST_ID";

    private final static String REQUEST_START_TIME_KEY = "REQUEST_START_TIME";

    public static void setRequestId(){
        getRequest().setAttribute(REQUEST_ID_KEY, IdUtil.simpleUUID());
    }

    public static String getRequestId(){
        try {
            // 尝试获取 request 对象并从中提取 REQUEST_ID_KEY 属性
            return (String) getRequest().getAttribute(REQUEST_ID_KEY);
        } catch (NullPointerException e) {
            // 若发生 NullPointerException，则返回空字符串
            return "";
        }
    }

    public static void setRequestStartTime(){
        getRequest().setAttribute(REQUEST_START_TIME_KEY, System.currentTimeMillis());
    }

    public static long getRequestTime(){
        long start = (long) getRequest().getAttribute(REQUEST_START_TIME_KEY);
        return System.currentTimeMillis() - start;
    }
}

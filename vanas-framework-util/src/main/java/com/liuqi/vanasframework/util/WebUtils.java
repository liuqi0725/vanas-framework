package com.liuqi.vanasframework.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;

/**
 * 类说明 <br>
 * <p>
 *  无
 *
 * @author : alexliu
 * @version v1.0 , Create at 2:00 PM 2019/12/9
 */
public class WebUtils {

    /**
     * 获取 request
     * @return {@link HttpServletRequest}
     */
    public static HttpServletRequest getRequest(){
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) attributes;
        return requestAttributes.getRequest();
    }

    /**
     * 获取 response
     * @return {@link HttpServletResponse}
     */
    public static HttpServletResponse getResponse(){
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) attributes;
        return requestAttributes.getResponse();
    }

    /**
     * 获取 session
     * @return {@link HttpSession}
     */
    public static HttpSession getSession(){
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) attributes;
        return requestAttributes.getRequest().getSession();
    }

    /**
     * 获取 session id
     * @return {@link String}
     */
    public static String getSessionId(){
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) attributes;
        return requestAttributes.getSessionId();
    }

    /**
     * 设置session属性
     * @param key 键
     * @param data 值
     */
    public static void setSessionAttribute(String key , Object data) {
        HttpServletRequest request = getRequest();
        request.getSession().setAttribute(key, data);
    }

    /**
     * 设置session属性
     * @param key 键
     * @param data 值
     */
    public static void setSessionAttribute(Enum<?> key , Object data) {
        HttpServletRequest request = getRequest();
        String keyStr = key.name() + key.hashCode();
        request.getSession().setAttribute(key.name(), data);
    }

    /**
     * 获取session属性
     * @param key 键
     * @return {@link Object}
     */
    public static Object getSessionAttribute(String key) {
        HttpServletRequest request = getRequest();
        return request.getSession().getAttribute(key);
    }

    /**
     * 获取session属性
     * @param key 键
     * @return {@link Object}
     */
    public static Object getSessionAttribute(Enum<?> key) {
        HttpServletRequest request = getRequest();
        String keyStr = key.name() + key.hashCode();
        return request.getSession().getAttribute(key.name());
    }

    /**
     * 获取 session 的 key <br>
     *     根据key+用户 id 组中 session key
     * @param key 键
     * @return {@link String}
     */
    private static String getSessionAttributeKey(String key){

//        String userKey = key + "_" + SecurityUtil.getUserInfo();
//        StringBuilder sessionKey = new StringBuilder("");
//
//        MessageDigest md5;
//        try {
//            md5 = MessageDigest.getInstance("MD5");
//            md5.update((userKey).getBytes("UTF-8"));
//            byte b[] = md5.digest();
//
//            int i;
//            for(int offset=0; offset<b.length; offset++){
//                i = b[offset];
//                if(i<0){
//                    i+=256;
//                }
//                if(i<16){
//                    sessionKey.append("0");
//                }
//                sessionKey.append(Integer.toHexString(i));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }

        return null;//sessionKey.toString();
    }
}

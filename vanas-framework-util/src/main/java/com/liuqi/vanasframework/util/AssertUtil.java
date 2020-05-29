package com.liuqi.vanasframework.util;

import com.alibaba.fastjson.JSONObject;
import com.liuqi.vanasframework.core.conf.norm.ExceptionErrorCode;
import com.liuqi.vanasframework.core.exception.AppException;

import java.util.Map;

/**
 * 类说明 <br>
 *    断言工具
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:02 PM 2019/8/12
 */
public class AssertUtil {

    /**
     * 不能为空
     * @param str 字符串
     * @return 不为空返回对象，为空抛出 AppException
     */
    public static String notNull(String str){
        return notNull(str, "String类型 参数为空。");
    }

    /**
     * 不能为空
     * @param var 字符串
     * @param errorMsg 错误信息
     * @return 不为空返回对象，为空抛出 AppException
     */
    public static String notNull(String var, String errorMsg){
        if(var == null || var.equals("")){
            throw new AppException(ExceptionErrorCode.NULL_ERROR , errorMsg);
        }
        return var;
    }

    /**
     * 不能为空
     * @param var integer 对象
     * @return 不为空返回对象，为空抛出 AppException
     */
    public static Integer notNull(Integer var){
        return notNull(var, "Integer类型 参数为空。");
    }

    /**
     * 不能为空
     * @param var  integer 对象
     * @param errorMsg 错误信息
     * @return 不为空返回对象，为空抛出 AppException
     */
    public static Integer notNull(Integer var, String errorMsg){
        if(var == null){
            throw new AppException(ExceptionErrorCode.NULL_ERROR , errorMsg);
        }

        return var;
    }

    /**
     * json 值不能为空
     * @param jsonObject json 对象
     * @param key 值 key
     * @param classz 值类型
     * @param <T> class
     * @return 不为空返回对象，为空抛出 AppException
     */
    public static <T> T notNull(JSONObject jsonObject, String key , Class<T> classz){
        if(!jsonObject.containsKey(key)){
            throw new AppException(ExceptionErrorCode.NULL_ERROR , "参数 "+key+"，不能为空");
        }
        return TypeUtil.cast(jsonObject.get(key) , classz);
    }

    /**
     * 不能为空
     * @param map map 对象
     * @param key 值 key
     * @param classz  值类型
     * @param <T> class
     * @return 不为空返回对象，为空抛出 AppException
     */
    public static <T> T notNull(Map map, String key , Class<T> classz){
        if(!map.containsKey(key)){
            throw new AppException(ExceptionErrorCode.NULL_ERROR , "参数 "+key+"，不能为空");
        }
        return TypeUtil.cast(map.get(key) , classz);
    }

    /**
     * 不能为空
     * @param obj 对象
     * @param <T> 任意类型
     * @return 不为空返回对象，为空抛出 AppException
     */
    public static <T> T notNull(T obj){
        return notNull(obj," 对象不能为空。");
    }

    /**
     * 不能为空
     * @param obj 对象
     * @param errorMsg 错误信息
     * @param <T> 任意类型
     * @return 不为空返回对象，为空抛出 AppException
     */
    public static <T> T notNull(T obj , String errorMsg){
        if (obj == null)
            throw new AppException(ExceptionErrorCode.NULL_ERROR , errorMsg);
        return obj;
    }

    /**
     * 是否正数
     * @param val integer 对象
     * @return 不为空返回对象，为空抛出 AppException
     */
    public static Integer isPositiveNumber(Integer val){
        if(val < 0){
            throw new AppException(ExceptionErrorCode.NULL_ERROR , "不能执行，参数错误，数字型参数必须为正数。");
        }

        return val;
    }

    /**
     * 是否正数
     * @param val integer 对象
     * @param errorMsg 错误信息
     * @return 不为空返回对象，为空抛出 AppException
     */
    public static Integer isPositiveNumber(Integer val, String errorMsg){
        if(val < 0){
            throw new AppException(ExceptionErrorCode.NULL_ERROR , errorMsg);
        }

        return val;
    }

}

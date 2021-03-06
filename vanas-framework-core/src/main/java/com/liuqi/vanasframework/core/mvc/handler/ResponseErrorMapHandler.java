package com.liuqi.vanasframework.core.mvc.handler;

import com.liuqi.vanasframework.core.conf.norm.ExceptionErrorCode;
import com.liuqi.vanasframework.core.exception.AppException;
import com.liuqi.vanasframework.core.mvc.entity.ErrorMap;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 类说明 <br>
 * <p>
 *
 *
 *
 *
 *
 * @author : alexliu
 * @version v1.0 , Create at 4:35 PM 2020/3/3
 */
public class ResponseErrorMapHandler {

    /**
     * 生成 {@link ErrorMap}
     * @param springErrorMap spring 的 errorMap 对象,必须包含 timestamp status error message path 字段
     * @return ErrorMap 错误对象
     */
    public static ErrorMap createErrorMap(Map<String, Object> springErrorMap){
        return new ErrorMap(springErrorMap);
    }

    /**
     * 生成 {@link ErrorMap}
     * @param request HttpServletRequest
     * @param e 异常
     * @param errorCode 错误编码
     * @param errorMsg 错误信息
     * @param <T> 继承 Exception
     * @return ErrorMap 错误对象
     */
    public static <T extends Exception> ErrorMap createErrorMap(HttpServletRequest request ,
                                                                             T e ,
                                                                String errorCode,
                                                                String errorMsg){

        ErrorMap map = new ErrorMap();
        map.setPath(request.getRequestURI());
        if(StringUtils.isEmpty(errorMsg)){
            map.setErrorMsg(e.getMessage());
        }else{
            map.setErrorMsg(errorMsg);
        }
        map.setError(e.getClass().getSimpleName());
        map.setErrorCode(errorCode);
        map.setTimestamp(new Date());
        if(e instanceof AppException){
            map.setErrorData(((AppException)e).getData());
        }
        return map;
    }


}

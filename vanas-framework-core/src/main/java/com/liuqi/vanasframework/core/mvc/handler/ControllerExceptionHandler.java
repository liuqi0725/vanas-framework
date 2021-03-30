package com.liuqi.vanasframework.core.mvc.handler;


import com.liuqi.vanasframework.core.conf.norm.ExceptionErrorCode;
import com.liuqi.vanasframework.core.conf.CommonConf;
import com.liuqi.vanasframework.core.exception.AppException;
import com.liuqi.vanasframework.core.mvc.entity.ErrorMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 类说明 <br>
 *     统一 controller 异常处理
 *
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:34 AM 2019/12/10
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LogManager.getLogger(ControllerExceptionHandler.class);

    private final String ERROR_PAGE = "error/5xx";

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception e, HttpServletRequest request){
        return exceptionHandlerProcess(request ,
                ExceptionErrorCode.BAD_REQUEST.getCode(),
                ExceptionErrorCode.BAD_REQUEST.getDesc(),
                e
        );
    }

    @ExceptionHandler(NullPointerException.class)
    public ModelAndView nullPointerExceptionHandler(NullPointerException e, HttpServletRequest request , Model model) {
        return exceptionHandlerProcess(request ,
                ExceptionErrorCode.NULL_ERROR.getCode(),
                ExceptionErrorCode.NULL_ERROR.getDesc(),
                e
        );
    }

    @ExceptionHandler(AppException.class)
    public ModelAndView customerExceptionHandler(AppException e, HttpServletRequest request){

        return exceptionHandlerProcess(request ,
                e.getExceptionErrorCode(),
                e.getExceptionErrorMsg(),
                e
        );
    }


    private <T extends Exception> ModelAndView exceptionHandlerProcess(HttpServletRequest request ,
                                                     String errorCode,
                                                     String errorMsg,
                                                     T exception){

        // 打印日志
        logger.error(errorCode,exception);

        ErrorMap errorMap = ResponseErrorMapHandler.createErrorMap(request, exception, errorCode, errorMsg);

        ModelAndView mv;
        if(isView(request)){
            mv = new ModelAndView(ERROR_PAGE);
            mv.addObject("errorMap",errorMap);
        }else{
            mv = ResponseJsonHandler.getInstance().failureRenderModelView(errorMap , "请求错误，详情查看 errorInfo");
        }

        return mv;
    }

    private boolean isView(HttpServletRequest request){

        return (boolean)request.getAttribute(CommonConf.REQUEST_RETURN_VIEW);
    }

}

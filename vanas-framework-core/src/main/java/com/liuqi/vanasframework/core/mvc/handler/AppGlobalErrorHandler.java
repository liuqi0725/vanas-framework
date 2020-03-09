package com.liuqi.vanasframework.core.mvc.handler;

import com.liuqi.vanasframework.core.mvc.entity.ErrorMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 类说明 <br>
 *     统一 Error 异常处理
 * <p>
 *
 *
 *
 *
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:34 AM 2019/12/10
 */
@Controller
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class AppGlobalErrorHandler extends AbstractErrorController {

    private final Logger logger = LogManager.getLogger(AppGlobalErrorHandler.class);

    private static final String ERROR_PATH_4XX = "/error/4xx";

    private static final String ERROR_PATH_5XX = "/error/5xx";

    private final ErrorProperties errorProperties;

    public AppGlobalErrorHandler(ErrorAttributes errorAttributes , ServerProperties serverProperties) {
        super(errorAttributes);
        this.errorProperties=serverProperties.getError();
    }

    @Override
    public String getErrorPath() {
        return errorProperties.getPath();
    }


    @RequestMapping(produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request,
                                  HttpServletResponse response) {
        ModelAndView modelAndView=new ModelAndView("error");
        Map<String, Object> springErrorMap = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        if(springErrorMap != null) {

            ErrorMap errorMap = ResponseErrorMapHandler.createErrorMap(springErrorMap);
            modelAndView.addObject("errorMap",errorMap);
            logHandler(errorMap);
        }

        HttpStatus code = this.getStatus(request);
        if(code == HttpStatus.NOT_FOUND){
            modelAndView.addObject("reason","资源不存在");
            modelAndView.setViewName(ERROR_PATH_4XX);
        }else if(code == HttpStatus.FORBIDDEN){
            modelAndView.addObject("reason","禁止访问");
            modelAndView.setViewName(ERROR_PATH_4XX);
        }else{
            modelAndView.setViewName(ERROR_PATH_5XX);
        }

        return modelAndView;
    }

    @RequestMapping
    @ResponseBody
    public Map<String,Object> error(HttpServletRequest request) {
        Map<String, Object> springErrorMap = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.APPLICATION_JSON));
        ErrorMap errorMap = ResponseErrorMapHandler.createErrorMap(springErrorMap);
        logHandler(errorMap);
        return ResponseJsonHandler.getInstance().failureRenderMap(errorMap, "请求错误，详情查看 errorInfo");
    }

    private void logHandler(ErrorMap errorMap) {

        logger.error("Request Error >> path:{},errorCode:{},time:{},errorMsg:{}",
                errorMap.getPath(),
                errorMap.getErrorCode(),
                errorMap.getTimestamp(),
                errorMap.getErrorMsg());
    }

    private boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {

        ErrorProperties.IncludeStacktrace include = getErrorProperties().getIncludeStacktrace();
        if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
            return true;
        }
        if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
            return getTraceParameter(request);
        }
        return false;
    }
    private ErrorProperties getErrorProperties() {
        return this.errorProperties;
    }


}

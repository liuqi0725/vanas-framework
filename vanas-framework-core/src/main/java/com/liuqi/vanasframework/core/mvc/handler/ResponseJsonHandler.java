package com.liuqi.vanasframework.core.mvc.handler;


import com.liuqi.vanasframework.core.conf.norm.ExceptionErrorCode;
import com.liuqi.vanasframework.core.exception.AppException;
import com.liuqi.vanasframework.core.mvc.entity.ErrorMap;
import com.liuqi.vanasframework.core.mvc.res.DataResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.io.Serializable;
import java.util.*;

/**
 * 类说明 <br>
 *  controller 、Error  JSON 统一返回数据
 * <p>
 *
 *
 *   {@code
 *   DataResult ret = new DataResult();
 *   }
 *
 *
 * @author : alexliu
 * @version v1.0 , Create at 12:53 PM 2018/11/9
 */
public class ResponseJsonHandler implements Serializable{

    private static final long serialVersionUID = -3403931445772288525L;

    private String ATTR_SUCCESS = "success";

    private String ATTR_DATA = "data";

    private String ATTR_ERRORINFO = "errorInfo";

    private String ATTR_MSG = "msg";

    private String DEFAULT_SUCCESS_MSG = "操作成功";


    private boolean success = true;

    private String msg;

    private ErrorMap errorInfo;

    private Object data;

    public static ResponseJsonHandler getInstance(){
        return ResponseJsonHandlerHook.instance;
    }

    private static class ResponseJsonHandlerHook{
        private static ResponseJsonHandler instance = new ResponseJsonHandler();
    }

    public Map<String,Object> successRenderMap(String msg){
        return this.successRenderMap(null , DEFAULT_SUCCESS_MSG);
    }

    public Map<String,Object> successRenderMap(Object data){
        return this.successRenderMap(data , DEFAULT_SUCCESS_MSG);
    }

    public Map<String,Object> successRenderMap(Object data , String msg){
        this.msg = msg;
        this.data = data;
        this.success = true;
        return this.renderJSON();
    }

    public ModelAndView successRenderModelView(String msg){
        return this.successRenderModelView(null,msg);
    }

    public ModelAndView successRenderModelView(Object data){
        return this.successRenderModelView(data , DEFAULT_SUCCESS_MSG);
    }

    public ModelAndView successRenderModelView(Object data , String msg){
        this.msg = msg;
        this.data = data;
        this.success = true;
        return this.renderModelAndViewJSON();
    }


    public Map<String,Object> failureRenderMap(String msg){
        return this.failureRenderMap(null, msg);
    }

    public Map<String,Object> failureRenderMap(ErrorMap errorInfo){
        return this.failureRenderMap(errorInfo , errorInfo.getErrorMsg());
    }

    public Map<String,Object> failureRenderMap(ErrorMap errorInfo , String msg){
        this.success = false;
        this.msg = msg;
        this.errorInfo = errorInfo;
        return this.renderJSON();
    }


    public ModelAndView failureRenderModelView(String msg){
        return this.failureRenderModelView(null, msg);
    }

    public ModelAndView failureRenderModelView(ErrorMap errorInfo){
        return this.failureRenderModelView(errorInfo , errorInfo.getErrorMsg());
    }

    public ModelAndView failureRenderModelView(ErrorMap errorInfo , String msg){
        this.success = false;
        this.msg = msg;
        this.errorInfo = errorInfo;
        return this.renderModelAndViewJSON();
    }

//    public <T extends Exception> void error(HttpServletRequest request , String errorMsg){
//        this.error(request,new AppException(errorMsg),ExceptionErrorCode.SYSTEM_ERROR);
//    }
//
//    public <T extends Exception> void error(HttpServletRequest request , ExceptionErrorCode exceptionErrorCode){
//        this.error(request,new AppException(exceptionErrorCode),exceptionErrorCode);
//    }
//
//    public <T extends Exception> void error(HttpServletRequest request , T e ){
//        this.error(request,e,ExceptionErrorCode.SYSTEM_ERROR);
//    }
//
//    public <T extends Exception> void error(HttpServletRequest request , T e , ExceptionErrorCode exceptionErrorCode){
//        success = false;
//        msg = exceptionErrorCode.getDesc();
//        errorInfo = ResponseErrorMapHandler.createErrorResponseBodyData(request,e,exceptionErrorCode);
//    }

    private ModelAndView renderModelAndViewJSON(){
        ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
        modelAndView.addObject(ATTR_SUCCESS, success);
        if(success){
            modelAndView.addObject(ATTR_DATA, data);
        }else{
            modelAndView.addObject(ATTR_ERRORINFO, errorInfo);
        }
        modelAndView.addObject(ATTR_MSG, msg);
        return modelAndView;
    }

    private Map<String,Object> renderJSON(){
        Map<String, Object> map = new HashMap<String,Object>();

        map.put(ATTR_SUCCESS , success);

        if(success){
            map.put(ATTR_DATA , data);
        }else{
            map.put(ATTR_ERRORINFO , errorInfo);
        }

        map.put(ATTR_MSG , msg);

        return map;
    }

    public Map<String,Object> renderJSON(DataResult result){

        Map<String, Object> map = new HashMap<String,Object>();

        map.put(ATTR_SUCCESS , result.getStatus());

        if(result.getStatus()){
            map.put(ATTR_DATA , result.getData());
        }else{
            if(StringUtils.isEmpty(result.getMsg())){
                throw new AppException(ExceptionErrorCode.NULL_ERROR, "返回 json 对象的 msg 为空。当Request 业务出现 Error时，返回消息不能为空。");
            }
            map.put(ATTR_ERRORINFO , result.getMsg());
        }

        map.put(ATTR_MSG , result.getMsg());

        return map;
    }
}

package com.liuqi.vanasframework.core.mvc.base;

import com.liuqi.vanasframework.core.conf.norm.ExceptionErrorCode;
import com.liuqi.vanasframework.core.conf.norm.ResultStatus;
import com.liuqi.vanasframework.core.exception.AppException;
import com.liuqi.vanasframework.core.tuple.ex.RequestMsgResultTuple;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import javax.validation.constraints.NotNull;
import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 类说明 <br>
 *     顶级 controller
 * 构造说明 :
 *      无
 *
 * @author : alexliu
 * @version v1.0 , Create at 11:23 2020-04-29
 */
public class AbstractController {

    protected final String RETURN_CLIENT_MSG_KEY_NAME = "msg";

    protected final String RETURN_CLIENT_MSG_TYPE = "msg_type";

    /**
     * 返回成功消息元祖
     * @param msg 消息内容
     * @return 成功的消息元祖，2 个值，消息类型，消息内容
     */
    protected RequestMsgResultTuple resultSuccessMsg(String msg){
        return new RequestMsgResultTuple(ResultStatus.SUCCESS , msg);
    }

    /**
     * 返回警告消息元祖
     * @param msg 消息内容
     * @return 成功的消息元祖，2 个值，消息类型，消息内容
     */
    protected RequestMsgResultTuple resultWarningMsg(String msg){
        return new RequestMsgResultTuple(ResultStatus.WARN , msg);
    }

    /**
     * 返回失败消息元祖
     * @param msg 消息内容
     * @return 成功的消息元祖，2 个值，消息类型，消息内容
     */
    protected RequestMsgResultTuple resultErrorMsg(String msg){
        return new RequestMsgResultTuple(ResultStatus.ERROR , msg);
    }

    /**
     * 渲染返回 ModelAndView
     * @param params 可变参数
     * <pre>
     *          以下参数均为非必填内容，但是在调用时，至少包含一个。<br>
     *          1. modelAndView spring 返回视图的封装对象，类型{@link ModelAndView}，如果不传，则由函数新建 <br>
     *          2. viewName 视图路由，类型{@link String} ，只能是 viewName，其他值无法识别 <br>
     *          3. dataMap 需要返回页面的参数 Map，类型{@link Map} <br>
     *          4. msgResult 返回页面的消息，类型 {@link RequestMsgResultTuple}，通过以下方法创建： <br>
     *                  {@link #resultSuccessMsg(String)} <br>
     *                  {@link #resultWarningMsg(String)} <br>
     *                  {@link #resultErrorMsg(String)} <br>
     * </pre>
     * @return {@link ModelAndView}
     */
    @SuppressWarnings("unchecked")
    protected ModelAndView forward(Object ...params){

        String errorMsg = "The params type only support [ModelAndView , String , Map, RequestMsgResultTuple]. but [%s] given.";

        ModelAndView modelAndView = null;
        String viewName = "";
        Map<String,Object> dataMap = null;
        RequestMsgResultTuple msgResult = null;

        for(Object obj : params){
            if(obj instanceof ModelAndView){
                modelAndView = (ModelAndView) obj;
            }else if(obj instanceof String){
                viewName = (String) obj;
            }else if(obj instanceof Map){
                dataMap = (Map<String,Object>) obj;
            }else if(obj instanceof RequestMsgResultTuple){
                msgResult = (RequestMsgResultTuple) obj;
            }else{
                throw new AppException(ExceptionErrorCode.PARAM_ERROR.getCode(), String.format(errorMsg , obj.getClass().getName()) );
            }
        }

        if(modelAndView == null){
            modelAndView = new ModelAndView();
        }

        if(StringUtils.isNotEmpty(viewName)){
            modelAndView.setViewName(viewName);
        }

        this.setForwardModelObjectWithMap(modelAndView,dataMap);
        this.setForwardModelObjectMessage(modelAndView,msgResult);

        return modelAndView;
    }

    /**
     * 渲染返回 ModelAndView
     * @param modelAndView spring 返回视图的封装对象
     * @param viewName 视图路由
     * @param dataMap 需要返回页面的参数 Map
     * @param msgResult 类型 {@link RequestMsgResultTuple}，通过以下方法创建： <br>
     *                  <pre>
     *                   {@link #resultSuccessMsg(String)} <br>
     *                   {@link #resultWarningMsg(String)} <br>
     *                   {@link #resultErrorMsg(String)} <br>
     *                  </pre>
     * @return ModelAndView
     */
    private ModelAndView forward(ModelAndView modelAndView , String viewName , Map<String,Object> dataMap ,  RequestMsgResultTuple msgResult){
        modelAndView.setViewName(viewName);
        this.setForwardModelObjectWithMap(modelAndView,dataMap);
        this.setForwardModelObjectMessage(modelAndView,msgResult);
        return modelAndView;
    }

    /**
     * 渲染返回 ModelAndView
     * @param modelAndView spring 返回视图的封装对象
     * @param viewName 视图路由
     * @param msgResult 类型 {@link RequestMsgResultTuple}，通过以下方法创建： <br>
     *                  <pre>
     *                   {@link #resultSuccessMsg(String)} <br>
     *                   {@link #resultWarningMsg(String)} <br>
     *                   {@link #resultErrorMsg(String)} <br>
     *                  </pre>
     * @return ModelAndView
     */
    private ModelAndView forward(ModelAndView modelAndView , String viewName , RequestMsgResultTuple msgResult){
        modelAndView.setViewName(viewName);
        this.setForwardModelObjectMessage(modelAndView,msgResult);
        return modelAndView;
    }

    /**
     * 渲染返回 ModelAndView
     * @param modelAndView spring 返回视图的封装对象
     * @param viewName 视图路由
     * @param dataMap 需要返回页面的参数 Map
     * @return ModelAndView
     */
    private ModelAndView forward(ModelAndView modelAndView , String viewName , Map<String,Object> dataMap){
        modelAndView.setViewName(viewName);
        this.setForwardModelObjectWithMap(modelAndView,dataMap);
        return modelAndView;
    }

    /**
     * 渲染返回 ModelAndView
     * @param modelAndView spring 返回视图的封装对象
     * @param dataMap 需要返回页面的参数 Map
     * @return ModelAndView
     */
    private ModelAndView forward(ModelAndView modelAndView , Map<String,Object> dataMap){
        this.setForwardModelObjectWithMap(modelAndView,dataMap);
        return modelAndView;
    }

    /**
     * 渲染返回 ModelAndView
     * @param modelAndView spring 返回视图的封装对象
     * @param msgResult 类型 {@link RequestMsgResultTuple}，通过以下方法创建： <br>
     *                  <pre>
     *                   {@link #resultSuccessMsg(String)} <br>
     *                   {@link #resultWarningMsg(String)} <br>
     *                   {@link #resultErrorMsg(String)} <br>
     *                  </pre>
     * @return ModelAndView
     */
    private ModelAndView forward(ModelAndView modelAndView , RequestMsgResultTuple msgResult){
        this.setForwardModelObjectMessage(modelAndView,msgResult);
        return modelAndView;
    }

    /**
     * 渲染返回 ModelAndView
     * @param viewName 视图路由
     * @param dataMap 需要返回页面的参数 Map
     * @return ModelAndView
     */
    private ModelAndView forward(String viewName , Map<String,Object> dataMap){
        return this.forward(new ModelAndView() , viewName, dataMap);
    }

    /**
     * 渲染返回 ModelAndView
     * @param viewName 视图路由
     * @param msgResult 类型 {@link RequestMsgResultTuple}，通过以下方法创建： <br>
     *                  <pre>
     *                   {@link #resultSuccessMsg(String)} <br>
     *                   {@link #resultWarningMsg(String)} <br>
     *                   {@link #resultErrorMsg(String)} <br>
     *                  </pre>
     * @return ModelAndView
     */
    private ModelAndView forward(String viewName , RequestMsgResultTuple msgResult){
        return this.forward(new ModelAndView() , viewName, msgResult);
    }

    /**
     * 渲染返回 ModelAndView
     * @param viewName 视图路由
     * @return ModelAndView
     */
    private ModelAndView forward(String viewName){
        return new ModelAndView(viewName);
    }

    /**
     * 渲染返回 ModelAndView
     * @param dataMap 需要返回页面的参数 Map
     * @return ModelAndView
     */
    private ModelAndView forward(Map<String,Object> dataMap){
        return this.forward(new ModelAndView(),dataMap);
    }

    /**
     * 渲染返回 ModelAndView
     * @param msgResult 类型 {@link RequestMsgResultTuple}，通过以下方法创建： <br>
     *                  <pre>
     *                   {@link #resultSuccessMsg(String)} <br>
     *                   {@link #resultWarningMsg(String)} <br>
     *                   {@link #resultErrorMsg(String)} <br>
     *                  </pre>
     * @return ModelAndView
     */
    private ModelAndView forward(RequestMsgResultTuple msgResult){
        return this.forward(new ModelAndView(),msgResult);
    }

    /**
     * 设置 Forward 消息
     * @param modelAndView spring 返回视图的封装对象
     * @param msgResult 类型 {@link RequestMsgResultTuple}，通过以下方法创建： <br>
     *                  <pre>
     *                   {@link #resultSuccessMsg(String)} <br>
     *                   {@link #resultWarningMsg(String)} <br>
     *                   {@link #resultErrorMsg(String)} <br>
     *                  </pre>
     */
    private void setForwardModelObjectMessage(ModelAndView modelAndView , RequestMsgResultTuple msgResult){
        if(msgResult != null){
            modelAndView.addObject(RETURN_CLIENT_MSG_TYPE , msgResult.var1);
            modelAndView.addObject(RETURN_CLIENT_MSG_KEY_NAME , msgResult.var2);
        }
    }

    /**
     * 设置 Forward 返回参数
     * @param modelAndView spring 返回视图的封装对象
     * @param dataMap 需要返回页面的参数 Map
     */
    private void setForwardModelObjectWithMap(ModelAndView modelAndView , Map<String,Object> dataMap){
        if(dataMap != null){
            for(String k : dataMap.keySet()){
                modelAndView.addObject(k,dataMap.get(k));
            }
        }
    }

    /**
     * 重定向到指定路径<br>
     * 渲染返回 ModelAndView
     * @param params 可变参数
     * <ul>
     *     <li>{@link ModelAndView} modelAndView spring 返回视图的封装对象，如果不传，则由函数新建 </li>
     *     <li>{@link String} redirectUrl 重定向 URL <b>{@link NotNull}</b>。<br>
     *                    <b>比如:</b> <br>
     *                    /user/list <br> /user/info/1 <br> /user/info?id=1&amp;name=admin;
     *     </li>
     *     <li>{@link RedirectAttributesModelMap} spring 在 Request期间创建的重定向包装对象。【当重定向后需要返回消息，参数时，此参数不能为空】</li>
     *     <li>{@link Map} dataMap 需要返回页面的参数。【需要返回数据时，redirectModelMap 不能为空 】</li>
     *     <li>{@link RequestMsgResultTuple} msgResult 返回页面的消息。【需要返回数据时，redirectModelMap 不能为空 】<br>
     *        <b>通过以下方法创建:</b> <br>
     *             {@link #resultSuccessMsg(String)} <br>
     *             {@link #resultWarningMsg(String)} <br>
     *             {@link #resultErrorMsg(String)}
     *     </li>
     * </ul>
     * @return {@link ModelAndView}
     * @throws AppException 参数错误异常
     */
    protected ModelAndView redirect(Object... params){
        String errorMsg = "The params type only support [ModelAndView , RedirectAttributesModelMap , String , Map, RequestMsgResultTuple]. but [%s] given.";

        ModelAndView modelAndView = null;
        RedirectAttributesModelMap redirectModelMap = null;
        String redirectUrl = "";
        Map<String,Object> dataMap = null;
        RequestMsgResultTuple msgResult = null;

        for(Object obj : params){
            if(obj instanceof ModelAndView){
                modelAndView = (ModelAndView)obj;
            }else if(obj instanceof RedirectAttributesModelMap){
                redirectModelMap = (RedirectAttributesModelMap) obj;
            }else if(obj instanceof String){
                redirectUrl = (String) obj;
            }else if(obj instanceof Map){
                dataMap = (Map<String,Object>) obj;
            }else if(obj instanceof RequestMsgResultTuple){
                msgResult = (RequestMsgResultTuple) obj;
            }else{
                throw new AppException(ExceptionErrorCode.PARAM_ERROR.getCode(), String.format(errorMsg , obj.getClass().getName()) );
            }
        }

        if(StringUtils.isEmpty(redirectUrl)){
            throw new AppException(ExceptionErrorCode.NULL_ERROR.getCode() , "Not found redirect URL." );
        }

        if(modelAndView == null){
            modelAndView = new ModelAndView();
        }

        this.setRedirectModelObjectMessage(redirectModelMap, msgResult);
        this.setRedirectModelObjectWithMap(redirectModelMap, dataMap);

        modelAndView.setViewName("redirect:" + redirectUrl);

        return modelAndView;
    }

    /**
     * 设置 Redirect 消息
     * @param redirectModelMap spring 重定向返回的封装
     * @param msgResult 类型 {@link RequestMsgResultTuple}，通过以下方法创建： <br>
     *                  <pre>
     *                   {@link #resultSuccessMsg(String)} <br>
     *                   {@link #resultWarningMsg(String)} <br>
     *                   {@link #resultErrorMsg(String)} <br>
     *                  </pre>
     */
    private void setRedirectModelObjectMessage(RedirectAttributesModelMap redirectModelMap , RequestMsgResultTuple msgResult){
        if(redirectModelMap != null && msgResult != null){
            redirectModelMap.addFlashAttribute(RETURN_CLIENT_MSG_TYPE , msgResult.var1);
            redirectModelMap.addFlashAttribute(RETURN_CLIENT_MSG_KEY_NAME , msgResult.var2);
        }
    }

    /**
     * 设置 Redirect 返回参数
     * @param redirectModelMap spring 重定向返回的封装
     * @param dataMap 需要返回页面的参数 Map
     */
    private void setRedirectModelObjectWithMap(RedirectAttributesModelMap redirectModelMap , Map<String,Object> dataMap){
        if(redirectModelMap != null && dataMap != null){
            for(String k : dataMap.keySet()){
                redirectModelMap.addFlashAttribute(k,dataMap.get(k));
            }
        }
    }

    /**
     * 过滤前台直接传 bean 参数处理
     * @param binder {@link WebDataBinder}
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        dateFormat.setLenient(false);

        //第二个参数是控制是否支持传入的值是空，这个值很关键，如果指定为false，那么如果前台没有传值的话就会报错
        binder.registerCustomEditor(Date.class, new AppDateEditor());
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true)); // 字符串 null 不填充为"" 还是为null

    }

    private class AppDateEditor extends PropertyEditorSupport {

        private final List<String> dateFormatAllow = new ArrayList<String>(){{
            add("yyyy-MM-dd HH:mm:ss");
            add("yyyyMMddHHmmss");
            add("yyyy-MM-dd");
            add("yyyyMMdd");
        }};

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            //通过两次异常的处理可以，绑定两次日期的格式
            Date date = null;
            boolean convertSuccess = Boolean.FALSE;

            for (String s : dateFormatAllow) {
                if (convert(text, s)) {
                    convertSuccess = Boolean.TRUE;
                    break;
                }
            }

            if(!convertSuccess){
                throw new AppException("转化时间类型字段错误。 传入值: "+text);
            }
        }

        private Boolean convert(String dateText , String formatText){
            SimpleDateFormat format = new SimpleDateFormat(formatText);
            try {
                setValue(format.parse(dateText));
            } catch (ParseException ignore) {
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
        }
    }
}

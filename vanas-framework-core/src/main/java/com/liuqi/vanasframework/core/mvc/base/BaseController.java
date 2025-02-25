package com.liuqi.vanasframework.core.mvc.base;

import com.liuqi.vanasframework.core.conf.norm.ExceptionErrorCode;
import com.liuqi.vanasframework.core.conf.norm.ResultStatus;
import com.liuqi.vanasframework.core.exception.AppException;
import com.liuqi.vanasframework.core.mvc.handler.ResponseJsonHandler;
import com.liuqi.vanasframework.core.mvc.res.DataResult;
import com.liuqi.vanasframework.core.mvc.res.PageDataResult;
import com.liuqi.vanasframework.core.mvc.res.PageDataVO;
import com.liuqi.vanasframework.core.tuple.ex.RequestMsgResultTuple;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 类说明 <br>
 *     controller 基类
 * <p>
 * 构造说明 :
 * <pre>
 *  new DataResultException();
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:32 AM 2019/4/26
 */
public class BaseController extends AbstractController{

    /**
     * 返回成功的 json 消息
     * <p>
     *     成功的 rest 请求可以快速返回成功的消息。<br>
     *     失败的，必须带有错误信息，请使用 <br>
     *         {@link #renderJSON(DataResult)} <br>
     *         {@link #renderJSONError(String)} <br>
     * </p>
     * @return Map 对象
     */
    protected Map<String, Object> renderJSONSuccess(){
        return ResponseJsonHandler.getInstance().renderJSON(new DataResult());
    }

    /**
     * 返回 json 消息
     * @param msg 错误消息
     * @return Map 对象
     */
    protected Map<String, Object> renderJSONError(String msg){
        DataResult result = new DataResult();
        result.error(msg);
        return ResponseJsonHandler.getInstance().renderJSON(result);
    }

    /**
     * 返回 json 消息
     * @param msg 错误消息
     * @param errorCode 错误编码
     * @return Map 对象
     */
    protected Map<String, Object> renderJSONError(String msg, String errorCode){
        DataResult result = new DataResult();
        result.error(msg);
        result.setCode(errorCode);
        return ResponseJsonHandler.getInstance().renderJSON(result);
    }

    /**
     * 返回 json 消息
     * @param result {@link DataResult} 返回对象
     * @return Map 对象
     */
    protected Map<String, Object> renderJSON(DataResult result){
        return ResponseJsonHandler.getInstance().renderJSON(result);
    }

    /**
     * 返回 layui table 的数据类型
     * @param res 分页查询返回值 {@link PageDataResult}
     * @return map
     */
    protected Map<String, Object> renderLayuiTableData(PageDataResult res){
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", res.getStatus() ? 0 : 1);
        ret.put("msg", res.getMsg());
        ret.put("count", res.getTotal() == null ? 0 : res.getTotal());
        ret.put("data", res.getPageData());
        return ret;
    }

    /**
     * 返回 layui table 的数据类型
     * @param status 是否成功
     * @param res 分页查询返回值 {@link PageDataVO}
     * @return map
     */
    protected <T> Map<String, Object> renderLayuiTableData(Boolean status, PageDataVO<T> res){
        return this.renderLayuiTableData(status, null, res);
    }

    /**
     * 返回 layui table 的数据类型
     * @param status 是否成功
     * @param msg 错误信息
     * @param res 分页查询返回值 {@link PageDataVO}
     * @return map
     */
    protected <T> Map<String, Object> renderLayuiTableData(Boolean status, String msg, PageDataVO<T> res){
        Map<String, Object> ret = new HashMap<>();
        ret.put("code", status ? 0 : 1);
        ret.put("msg", msg);
        ret.put("count", res != null ? res.getTotal() : 0);
        ret.put("data", res != null ? res.getRecords() : null);
        return ret;
    }

    /**
     * 重定向到指定路径,【不建议使用方法】
     *
     * @param redirectUrl <b>不能为空</b>。<br>
     *                   <b>比如:</b> <br>
     *                   /user/list <br> /user/info/1 <br> /user/info?id=1&amp;name=admin;
     * @return 重定向的路径
     * @deprecated 返回字符串会被异常处理拦截为返回 json 对象，如果返回的是视图。建议使用 {@link #redirect}
     */
    protected String redirectURL(String redirectUrl){
        return "redirect:"+redirectUrl;
    }



    /**
     * 设置重定向后的消息
     * @param model 重定向 map【已废弃】
     * @param msg_type 消息类型 {@link ResultStatus}
     * @param msg spring mvc 返回消息
     * @deprecated 已废弃，推荐使用{@link #redirect(Object...)}
     */
    protected void setRediectMessage(RedirectAttributesModelMap model, ResultStatus msg_type , String msg){

        if(msg_type == ResultStatus.SUCCESS){
            this.setRediectSuccessMessage(model,msg);
        }else if(msg_type == ResultStatus.ERROR){
            this.setRediectErrorMessage(model,msg);
        }else if(msg_type == ResultStatus.WARN){
            this.setRediectWarnMessage(model,msg);
        }
    }
    /**
     * 设置跨重定向的 spring mvc 返回消息【已废弃】
     * @param model 重定向 map
     * @param msg 消息
     * @deprecated 已废弃，推荐使用{@link #redirect(Object...)}
     */
    protected void setRediectErrorMessage(RedirectAttributesModelMap model, String msg){
        model.addFlashAttribute(RETURN_CLIENT_MSG_KEY_NAME, msg);
        model.addFlashAttribute(RETURN_CLIENT_MSG_TYPE, ResultStatus.ERROR);
    }

    /**
     * 设置跨重定向的 spring mvc 返回消息
     * @param model 重定向 map
     * @param msg 消息
     * @deprecated 已废弃，推荐使用{@link #redirect(Object...)}
     */
    protected void setRediectSuccessMessage(RedirectAttributesModelMap model, String msg ){
        model.addFlashAttribute(RETURN_CLIENT_MSG_KEY_NAME, msg);
        model.addFlashAttribute(RETURN_CLIENT_MSG_TYPE, ResultStatus.SUCCESS);
    }

    /**
     * 设置跨重定向的 spring mvc 返回消息
     * @param model 重定向 map
     * @param msg 消息
     * @deprecated 已废弃，推荐使用{@link #redirect(Object...)}
     */
    protected void setRediectWarnMessage(RedirectAttributesModelMap model, String msg){
        model.addFlashAttribute(RETURN_CLIENT_MSG_KEY_NAME, msg);
        model.addFlashAttribute(RETURN_CLIENT_MSG_TYPE, ResultStatus.WARN);
    }

    /**
     * 设置视图 spring mvc 返回消息
     * @param mav map
     * @param msg_type 消息类型 {@link ResultStatus}
     * @param msg 消息体
     * @deprecated 已废弃，推荐使用 <br>
     *     {@link #forward(ModelAndView, String, Map, RequestMsgResultTuple)} <br>
     *     {@link #forward(String, RequestMsgResultTuple)}<br>
     *     {@link #forward(ModelAndView, String, RequestMsgResultTuple)}<br>
     *     {@link #forward(RequestMsgResultTuple)}<br>
     */
    protected void setForwardSuccessMessage(ModelAndView mav , ResultStatus msg_type , String msg){

        if(msg_type == ResultStatus.SUCCESS){
            setForwardSuccessMessage(mav,msg);
        }else if(msg_type == ResultStatus.ERROR){
            setForwardErrorMessage(mav,msg);
        }else if(msg_type == ResultStatus.WARN){
            setForwardWarningMessage(mav,msg);
        }
    }

    /**
     * 设置视图 spring mvc 返回消息
     * @param mav map
     * @param msg 消息
     * @deprecated 已废弃，推荐使用 <br>
     *     {@link #forward(ModelAndView, String, Map, RequestMsgResultTuple)} <br>
     *     {@link #forward(String, RequestMsgResultTuple)}<br>
     *     {@link #forward(ModelAndView, String, RequestMsgResultTuple)}<br>
     *     {@link #forward(RequestMsgResultTuple)}<br>
     */
    protected void setForwardSuccessMessage(ModelAndView mav , String msg){
        mav.addObject(RETURN_CLIENT_MSG_KEY_NAME,msg);
        mav.addObject(RETURN_CLIENT_MSG_TYPE,ResultStatus.SUCCESS);
    }

    /**
     * 设置视图 spring mvc 返回消息
     * @param mav map
     * @param msg 消息
     * @deprecated 已废弃，推荐使用 <br>
     *     {@link #forward(ModelAndView, String, Map, RequestMsgResultTuple)} <br>
     *     {@link #forward(String, RequestMsgResultTuple)}<br>
     *     {@link #forward(ModelAndView, String, RequestMsgResultTuple)}<br>
     *     {@link #forward(RequestMsgResultTuple)}<br>
     */
    protected void setForwardWarningMessage(ModelAndView mav , String msg){
        mav.addObject(RETURN_CLIENT_MSG_KEY_NAME,msg);
        mav.addObject(RETURN_CLIENT_MSG_TYPE,ResultStatus.WARN);
    }

    /**
     * 设置视图 spring mvc 返回消息
     * @param mav map
     * @param msg 消息
     * @deprecated 已废弃，推荐使用 <br>
     *     {@link #forward(ModelAndView, String, Map, RequestMsgResultTuple)} <br>
     *     {@link #forward(String, RequestMsgResultTuple)}<br>
     *     {@link #forward(ModelAndView, String, RequestMsgResultTuple)}<br>
     *     {@link #forward(RequestMsgResultTuple)}<br>
     */
    protected void setForwardErrorMessage(ModelAndView mav , String msg){
        mav.addObject(RETURN_CLIENT_MSG_KEY_NAME,msg);
        mav.addObject(RETURN_CLIENT_MSG_TYPE,ResultStatus.ERROR);
    }

    /**
     * 验证参数
     * @param bindingResult
     */
    public void validParams(BindingResult bindingResult){
        List<ObjectError> allErrors = bindingResult.getAllErrors();

        if(!allErrors.isEmpty()) {
            List<String> errors = allErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
            throw new AppException(ExceptionErrorCode.PARAM_ERROR.getCode(), String.join(",", errors));
        }
    }
}

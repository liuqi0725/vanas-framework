package com.liuqi.vanasframework.core.mvc.base;

import com.liuqi.vanasframework.core.conf.norm.ResultStatus;
import com.liuqi.vanasframework.core.mvc.res.PageDataResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
public class BaseController {

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
     * 重定向到指定路径
     * @param redirectUrl <b>不能为空</b>。<br>
     *                   <b>比如:</b> <br>
     *                   /user/list <br> /user/info/1 <br> /user/info?id=1&amp;name=admin;
     * @return 重定向的路径
     */
    protected String redirectURL(String redirectUrl){
        return "redirect:"+redirectUrl;
    }

    /**
     * 重定向到指定路径
     * @param params 例如 /test/getUser
     *        支持如下类型参数:<br>
     *        <ul>
     *               <li>{@link ModelAndView} 如果不传，由方法内部新建。</li>
     *               <li>{@link String} redirectUrl <b>不能为空</b>。<br>
     *                   <b>比如:</b> <br>
     *                   /user/list <br> /user/info/1 <br> /user/info?id=1&amp;name=admin;
     *               </li>
     *        </ul>
     * @return {@link ModelAndView}
     */
    protected ModelAndView redirect(Object... params){

        ModelAndView mv = null;
        String redirectUrl = "";

        for(Object o : params){
            if(o instanceof ModelAndView){
                mv = (ModelAndView)o;
            }else if(o instanceof String){
                redirectUrl = (String)o;
            }else{
                throw new RuntimeException("The params Only support [ModelAndView] , [String] Type. but ["+o.getClass().getName()+"] given." );
            }
        }

        if(StringUtils.isEmpty(redirectUrl)){
            throw new RuntimeException("Not found redirect URL." );
        }

        if(mv == null){
            mv = new ModelAndView();
        }

        mv.setViewName("redirect:" + redirectUrl);

        return mv;
    }

    /**
     *
     * @param model 重定向 map
     * @param msg_type 消息类型 {@link ResultStatus}
     * @param msg spring mvc 返回消息
     */
    protected void setRediectMessage(RedirectAttributesModelMap model, ResultStatus msg_type , String msg){

        if(msg_type == ResultStatus.SUCCESS){
            setRediectSuccessMessage(model,msg);
        }else if(msg_type == ResultStatus.ERROR){
            setRediectErrorMessage(model,msg);
        }else if(msg_type == ResultStatus.WARN){
            setRediectWarnMessage(model,msg);
        }
    }
    /**
     * 设置跨重定向的 spring mvc 返回消息
     * @param model 重定向 map
     * @param msg 消息
     */
    protected void setRediectErrorMessage(RedirectAttributesModelMap model, String msg){
        model.addFlashAttribute("msg", msg);
        model.addFlashAttribute("msg_type", ResultStatus.ERROR);
    }

    /**
     * 设置跨重定向的 spring mvc 返回消息
     * @param model 重定向 map
     * @param msg 消息
     */
    protected void setRediectSuccessMessage(RedirectAttributesModelMap model, String msg ){
        model.addFlashAttribute("msg", msg);
        model.addFlashAttribute("msg_type", ResultStatus.SUCCESS);
    }

    /**
     * 设置跨重定向的 spring mvc 返回消息
     * @param model 重定向 map
     * @param msg 消息
     */
    protected void setRediectWarnMessage(RedirectAttributesModelMap model, String msg){
        model.addFlashAttribute("msg", msg);
        model.addFlashAttribute("msg_type", ResultStatus.WARN);
    }

    /**
     * 设置视图 spring mvc 返回消息
     * @param mav map
     * @param msg_type 消息类型 {@link ResultStatus}
     * @param msg 消息体
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
     */
    protected void setForwardSuccessMessage(ModelAndView mav , String msg){
        mav.addObject("msg",msg);
        mav.addObject("msg_type",ResultStatus.SUCCESS);
    }

    /**
     * 设置视图 spring mvc 返回消息
     * @param mav map
     * @param msg 消息
     */
    protected void setForwardWarningMessage(ModelAndView mav , String msg){
        mav.addObject("msg",msg);
        mav.addObject("msg_type",ResultStatus.WARN);
    }

    /**
     * 设置视图 spring mvc 返回消息
     * @param mav map
     * @param msg 消息
     */
    protected void setForwardErrorMessage(ModelAndView mav , String msg){
        mav.addObject("msg",msg);
        mav.addObject("msg_type",ResultStatus.ERROR);
    }

    /**
     * 过滤前台直接传 bean 参数处理
     * @param binder {@link WebDataBinder}
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);

        //第二个参数是控制是否支持传入的值是空，这个值很关键，如果指定为false，那么如果前台没有传值的话就会报错
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));

    }



}

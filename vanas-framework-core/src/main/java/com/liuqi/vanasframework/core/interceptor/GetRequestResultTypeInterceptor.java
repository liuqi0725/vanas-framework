package com.liuqi.vanasframework.core.interceptor;

import com.liuqi.vanasframework.core.conf.CommonConf;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 类说明 <br>
 *     判断请求返回的是视图还是 json
 * <p>
 *
 *
 *
 *
 *
 * @author : alexliu
 * @version v1.0 , Create at 10:57 AM 2020/3/5
 */
public class GetRequestResultTypeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 通过本次请求的 返回值，注解，controller 注解 来判断是返回 view 还是 body
        // 并把结果放在 request 中

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();
        // 请求函数不是返回字符串
        boolean b1 = !method.getReturnType().equals(String.class);
        // 请求函数 没有 ResponseBody 注解
        boolean b2 = !method.isAnnotationPresent(ResponseBody.class);
        // 请求类 没有 RestController 注解
        boolean b3 = !hm.getBeanType().isAnnotationPresent(RestController.class);
        request.setAttribute(CommonConf.REQUEST_RETURN_VIEW, b1 && b2 && b3);

        return true;
    }
}

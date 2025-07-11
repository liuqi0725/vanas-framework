package com.liuqi.vanasframework.core;

import com.liuqi.vanasframework.core.cache.VanasCacheFactory;
import com.liuqi.vanasframework.core.conf.VanasCustomerConfig;
import com.liuqi.vanasframework.core.interceptor.GetRequestResultTypeInterceptor;
import com.liuqi.vanasframework.core.mvc.view.FreeMarkerConfig;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.Serializable;

/**
 * 类说明 <br>
 *     Vanas web 项目初始化，由外部继承该抽象类
 * <p>
 *
 * @author : alexliu
 * @version v1.0 , Create at 11:27 AM 2020/3/5
 */
@Log4j2
public abstract class VanasAppWebConfig implements WebMvcConfigurer, ApplicationContextAware,Serializable {

    private static final long serialVersionUID = 3506677069701948993L;

    protected FreeMarkerConfig freeMarkerConfig;

    private VanasCustomerConfig vanasCustomerConfig;

    public VanasAppWebConfig(FreeMarkerConfig freeMarkerConfig, VanasCustomerConfig vanasCustomerConfig){
        this.freeMarkerConfig = freeMarkerConfig;
        this.vanasCustomerConfig = vanasCustomerConfig;
    }

    /**
     * 添加 获取请求类型的拦截器，该拦截器获取用户请求类型，当错误时返回 view 或 json
     * @param registry spring filter 注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GetRequestResultTypeInterceptor());
    }

    public abstract String getCacheType();

    /**
     * 设置 spring 上下文到系统中，方便在外部调用。 外部使用 Vanas.SpringContext 即可
     * @param applicationContext spring 上下文
     * @throws BeansException bean 注入错误
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("Vanas Spring Context success set on. {}",applicationContext);
        Vanas.SpringContext = applicationContext;
        log.info("Vanas Customer Config success set on. {}", vanasCustomerConfig);
        Vanas.customerConfig = vanasCustomerConfig;
        log.info("Vanas CacheManager success set on. ");
        Vanas.cacheManager = getCacheManager(applicationContext);
    }

    private CacheManager getCacheManager(ApplicationContext applicationContext){
        String cacheType = getCacheType();
        if(StringUtils.isEmpty(cacheType)){
            throw new IllegalArgumentException("没有获取到缓存类型配置. VanasAppWebConfig -> getCacheType() 未设置!");
        }
        log.info("Vanas build CacheManager with {}", cacheType);
        return VanasCacheFactory.createCacheManager(cacheType, applicationContext);
    }

}

package com.liuqi.vanasframework.core;

import com.liuqi.vanasframework.core.interceptor.GetRequestResultTypeInterceptor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.cache.CacheManager;
import java.io.IOException;
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
public abstract class AppWebConfig implements WebMvcConfigurer, ApplicationContextAware,Serializable {

    private static final long serialVersionUID = 3506677069701948993L;

    /**
     * 添加 获取请求类型的拦截器，该拦截器获取用户请求类型，当错误时返回 view 或 json
     * @param registry spring filter 注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GetRequestResultTypeInterceptor());
    }

    /**
     * 设置 spring 上下文到系统中，方便在外部调用。 外部使用 Vanas.SpringContext 即可
     * @param applicationContext spring 上下文
     * @throws BeansException bean 注入错误
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("Vanas Spring Context success set on. {}",applicationContext);
        Vanas.SpringContext = applicationContext;
    }

    /**
     * 获取缓存配置文件
     * @return 配置文件路径
     */
    public abstract String getCacheConfigPath();

    /**
     * 缓存管理器
     * @return spring 缓存管理器
     */
    @Bean("jCacheManagerFactoryBean")
    public JCacheManagerFactoryBean mManagerFactoryBean() {
        log.info("缓存加载初始化 ehcache 3.x .");

        log.info("缓存加载初始化 >> 加载 spring 缓存管理器.");

        if(StringUtils.isEmpty(getCacheConfigPath())){
            log.warn("缓存加载初始化 >> cacheConfigPath() 配置文件路径为空。 放弃加载缓存。");
            return null;
        }

        JCacheManagerFactoryBean factoryBean;
        try {
            factoryBean = new JCacheManagerFactoryBean();
            factoryBean.setCacheManagerUri(new ClassPathResource(getCacheConfigPath()).getURI());

        } catch (IOException e) {
            e.printStackTrace();
            log.error("缓存加载失败，{} 配置文件不存在",getCacheConfigPath());
            return null;
        }

        return factoryBean;
    }

    /**
     * 缓存管理器
     * @return 缓存管理器
     */
    @Bean
    public JCacheCacheManager cacheInitManage(@Qualifier("jCacheManagerFactoryBean") CacheManager cacheManager) {

        if(cacheManager == null){
            log.warn("缓存加载初始化 >> cacheManager 为空。 放弃加载缓存。");
            return null;
        }

        // 返回 org.springframework.CacheManager
        // CacheManager cacheManager = new ConcurrentMapCacheManager();
        Vanas.cacheManager = new JCacheCacheManager(cacheManager);
        return Vanas.cacheManager;
    }
}

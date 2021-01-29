package com.liuqi.vanasframework.core;

import com.liuqi.vanasframework.core.conf.VanasCustomerConfig;
import com.liuqi.vanasframework.core.interceptor.GetRequestResultTypeInterceptor;
import com.liuqi.vanasframework.core.mvc.view.FreeMarkerConfig;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    protected FreeMarkerConfig freeMarkerConfig;

    @Autowired
    private VanasCustomerConfig vanasCustomerConfig;

    @Autowired
    private CacheManager springAutoFitCacheManager;

    /**
     * 添加 获取请求类型的拦截器，该拦截器获取用户请求类型，当错误时返回 view 或 json
     * @param registry spring filter 注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new GetRequestResultTypeInterceptor());
    }

    public abstract CacheManager getCacheManager();

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
        CacheManager cacheManager = getCacheManager();
        if(cacheManager == null){
            cacheManager = springAutoFitCacheManager;
        }
        Vanas.cacheManager = cacheManager;
    }

    /**
     * 初始化缓存
     */
//    @Bean
//    private void initCacheManager() {
//
//        log.info("缓存加载初始化 ehcache 3.x .");
//
//        log.info("缓存加载初始化 >> 加载 spring 缓存管理器.");
//        try {
////            CachingProvider provider = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider");
////            URI uri = new ClassPathResource(getCacheConfigPath()).getURI();// getClass().getResource(getCacheConfigPath()).toURI();
////            CacheManager cacheManager = provider.getCacheManager(uri, getClass().getClassLoader());
////            CacheManager cacheManager = cacheCacheManager.getCacheManager();
////            cacheCacheManager
//
//
//            Vanas.cacheManager = Vanas.SpringContext.getBean(RedisCacheConfig.class).cacheManager();
////            Vanas.cacheManager = Vanas.SpringContext.getBean(JCacheCacheManager.class);//new JCacheCacheManager(cacheManager);
//        } catch (Exception e) {
//            log.error("缓存加载初始化 >> Error : ",e);
//            throw new AppBootstrapException("缓存加载失败" , e);
//        }
//    }
}

package com.liuqi.vanasframework.core.mvc.view;


import cn.org.rapid_framework.freemarker.directive.BlockDirective;
import cn.org.rapid_framework.freemarker.directive.ExtendsDirective;
import cn.org.rapid_framework.freemarker.directive.OverrideDirective;
import cn.org.rapid_framework.freemarker.directive.SuperDirective;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.Serializable;
import java.util.Map;

/**
 * 类说明 <br>
 * <p>
 *
 *
 *
 *
 *
 * @author : alexliu
 * @version v1.0 , Create at 1:44 PM 2020/3/1
 */
@Component
@Log4j2
public class FreeMarkerConfig implements Serializable{

    private static final long serialVersionUID = 7225569316034483810L;

    private freemarker.template.Configuration configuration;

    private FreeMarkerViewResolver freeMarkerViewResolver;

    public FreeMarkerConfig(freemarker.template.Configuration configuration , FreeMarkerViewResolver freeMarkerViewResolver){
        this.configuration = configuration;
        this.freeMarkerViewResolver = freeMarkerViewResolver;
    }

    public void setFreeMarkConf(Map<String,String> staticClass){

        log.info("加载 Freemarker 配置.");

        log.info("加载 Freemarker 配置 >>> Freemarker set Tag [Extends , Override , Super , Block]");
        this.setSharedVar();

        log.info("加载 Freemarker 配置 >>> Freemarker set templates used Java static class and enum.");
        this.setStaticModels(staticClass);
    }

    /**
     * 设置全局变量
     */
    private void setSharedVar(){
        // 添加对 override block extends super 支持
        configuration.setSharedVariable("block" , new BlockDirective());
        configuration.setSharedVariable("override" , new OverrideDirective());
        configuration.setSharedVariable("extends" , new ExtendsDirective());
        configuration.setSharedVariable("super", new SuperDirective());
    }

    /**
     * 设置静态资源
     */
    private void setStaticModels(Map<String,String> staticClass) {
        freeMarkerViewResolver.setAttributesMap(new FreeMarkerStaticModels(staticClass));
    }

}

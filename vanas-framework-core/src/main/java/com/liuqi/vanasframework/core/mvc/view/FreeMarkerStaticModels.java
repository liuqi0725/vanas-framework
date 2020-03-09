package com.liuqi.vanasframework.core.mvc.view;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
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
 * @version v1.0 , Create at 3:42 PM 2020/3/1
 */
@Log4j2
public class FreeMarkerStaticModels extends HashMap<String,Object> {

    public FreeMarkerStaticModels(Map<String, String> classMap) {
        for (String key : classMap.keySet()) {
            put(key, getModel(classMap.get(key)));
        }
    }

    private TemplateHashModel getModel(String packageName) {
        BeansWrapper wrapper = new BeansWrapperBuilder(Configuration.VERSION_2_3_28).build();
        try {
            return (TemplateHashModel)  wrapper.getStaticModels().get(packageName);
        } catch (TemplateModelException e) {
            log.error("添加 Freemarker 静态资源失败。" , e);
        }
        return null;
    }

}

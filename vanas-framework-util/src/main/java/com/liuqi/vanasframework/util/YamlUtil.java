package com.liuqi.vanasframework.util;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

/**
 * 类说明 <br>
 *     yaml 文件读写帮助
 * @author : alexliu
 * @version v1.0 , Create at 11:49 AM 2019/12/6
 */
public class YamlUtil {

    /**
     * 读取 yaml 文件
     * @param fileName 文件名，文件需放在 resource 目录下
     * @return Map 对象
     */
    public static Map readYamlFile(String fileName) {
        Yaml yaml = new Yaml();

        InputStream in = YamlUtil.class.getClassLoader().getResourceAsStream(fileName);

        return (Map) yaml.load(in);
    }

}

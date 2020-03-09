package com.liuqi.vanasframework.util;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 类说明 <br>
 *     类操作工具类
 *
 * @author : alexliu
 * @version v1.0 , Create at 9:30 AM 2020/3/6
 */
public class ClassUtil {

    /**
     * java 类后缀名
     */
    private String CLASS_FILE_SUFFIX = ".class";

    /**
     * 包 类路径分隔符
     */
    private char PACKAGE_SEPARATOR = '.';

    /**
     * 包 路径分隔符
     */
    private char PACKAGE_PATH_SEPARATOR = '/';

    /**
     * 类的协议
     */
    private String CLASS_PROTOCOL = "file";

    private ClassUtil(){}

    public static ClassUtil getInstance(){
        return ClassUtilHook.instance;
    }

    private static class ClassUtilHook {
        private static ClassUtil instance = new ClassUtil();
    }

    /**
     * 该方法通过传入的包对象，获取该包对象下，所有的 class 类对象
     *
     * @param pkg {@link Package}
     * @return {@link List}&lt;Class&lt;?&gt;&gt;
     */
    public List<Class<?>> getClasses(Package pkg){
        String packageName = pkg.getName();
        return getClasses(packageName);
    }

    /**
     * 该方法通过传入的包名称，获取该包对象下，所有的 class 类对象
     * 包名必须是 xx.xx.xx.xx 的结构, 分隔符为 "." , "*" 代表匿名匹配所有 <br>
     *
     * @param pkgName 包名
     * @return {@link List}&lt;Class&lt;?&gt;&gt;
     */
    public List<Class<?>> getClasses(String pkgName){
        return this.findClassWithPackageName(pkgName , null);
    }

    /**
     * 该方法通过传入的包名称，获取该包对象下，所有的 class 类对象<br>
     * 并通过 annotationClass 获取带有对应注释的类<br>
     * 包名必须是 <i>xx.xx.xx.xx</i> 的结构, 分隔符为 "." , "*" 代表匿名匹配所有 <br>
     *
     * @param pkgName 包名
     * @param annotationClass 注释名
     * @return {@link List}&lt;Class&lt;?&gt;&gt;
     */
    public List<Class<?>> getClasses(String pkgName , Class annotationClass){
        return this.findClassWithPackageName(pkgName , annotationClass);
    }

    /**
     * 通过包名查找所有 class
     *
     * @param packageName 包名
     * @param annotationClass 注释类
     * @return 待注释的 class 类
     */
    private List<Class<?>> findClassWithPackageName(String packageName , Class annotationClass){

        // 返回值
        List<Class<?>> classes = new ArrayList<Class<?>>();

        // 替换包结构
        String packageDirName = packageName.replace(PACKAGE_SEPARATOR, PACKAGE_PATH_SEPARATOR);

        Enumeration<URL> urls;
        URL url;
        try {
            urls = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            // 循环迭代下去
            while (urls.hasMoreElements()) {

                url = urls.nextElement();
                // 获取协议
                String protocol = url.getProtocol();

                if(protocol.equalsIgnoreCase(CLASS_PROTOCOL)){
                    // 转化为路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 过滤路径中的文件并转化为 class
                    filterFile2Class(packageName , filePath , classes , annotationClass);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return classes;
    }


    /**
     * 过滤文件，将 java 文件转化为 Class
     * @param packageName 包名
     * @param filePath 文件路径
     * @param classes Class 集合
     * @throws ClassNotFoundException 如果文件找不到将抛出该异常
     */
    private void filterFile2Class(String packageName ,String filePath, List<Class<?>> classes , Class annotationClass) throws ClassNotFoundException {

        File file = new File(filePath);
        if(!file.exists() || !file.isDirectory())
            return;

        // 获取所有文件夹 和 class 文件
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File childrenFile) {
                return (childrenFile.isDirectory() || childrenFile.getName().endsWith(CLASS_FILE_SUFFIX));
            }
        });

        if(files == null || files.length == 0 )
            return;

        String className;
        String tempPackageName;
        Class targetClass;
        for(File f : files){

            if(f.isDirectory()){
                tempPackageName = packageName + PACKAGE_SEPARATOR + f.getName();
                filterFile2Class(tempPackageName , f.getAbsolutePath() , classes , annotationClass);
            }else{
                className = f.getName().substring(0 , f.getName().length() - CLASS_FILE_SUFFIX.length());

                targetClass = Class.forName(packageName + "." + className);

                if(annotationClass == null){
                    classes.add(targetClass);
                }else{
                    // 判断是否有该注释
                    Annotation annotation = targetClass.getAnnotation(annotationClass);
                    if(annotation != null){
                        classes.add(Class.forName(packageName + "." + className));
                    }
                }

            }

        }

    }
}

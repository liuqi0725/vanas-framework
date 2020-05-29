package com.liuqi.vanasframework.util.entry;

/**
 * 类说明 <br>
 *     读取 Jar 包内的资源文件.
 *
 * @author : alexliu
 * @version v1.0 , Create at 23:21 2020-05-27
 */
public class JarResourceData {
    private String RESOURCE_SEPARATOR = "/";

    private String FILE_SEPARATOR = ".";

    private String path;

    private String fileName;

    private String fileType;

    private String filePath;

    public JarResourceData(String path) {
        this.path = path;
        this.fileName = resolverResourceFileName();
        this.fileType = resolverResourceFileType();
        this.filePath = resolverResourceFilePath();

    }

    private String resolverResourceFileName(){

        int lastIndex = path.lastIndexOf(RESOURCE_SEPARATOR);

        if(lastIndex == -1){
            return null;
        }

        return path.substring(lastIndex+1);
    }

    private String resolverResourceFileType(){
        int lastIndex = path.lastIndexOf(FILE_SEPARATOR);

        if(lastIndex == -1){
            return null;
        }

        return path.substring(lastIndex+1);
    }

    private String resolverResourceFilePath(){
        int lastIndex = path.lastIndexOf(FILE_SEPARATOR);

        if(lastIndex == -1){
            return null;
        }

        return path.substring(0 , lastIndex);
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public String getFilePath() {
        return filePath;
    }
}

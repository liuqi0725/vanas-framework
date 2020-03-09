package com.liuqi.vanasframework.core.mvc.base;

import java.io.Serializable;

/**
 * 类说明 <br>
 * 带分页数据的父类
 *
 * @author : alexliu
 * @version v1.0 , Create at 2:54 PM 2020/3/6
 */
public abstract class PageBean implements Serializable{

    //分页数据，查询用
    private Integer page;

    //分页数据，查询用
    private Integer limit;

    public Integer getPage() {
        return page;
    }

    public Integer getLimit() {
        return limit;
    }

    /**
     * 一般通用分页设置
     *
     * 适用于 page 以 1开始 的框架
     *
     * @param page 第几页
     * @param limit 每页数量
     */
    public void setPageGeneral(int page,int limit){

        if(page == 1){
            this.page = page-1;
        }else if(page > 1){
            this.page = (page-1)*limit;
        }
        this.limit = limit;
    }
}

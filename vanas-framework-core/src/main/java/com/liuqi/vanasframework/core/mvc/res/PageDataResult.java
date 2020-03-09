package com.liuqi.vanasframework.core.mvc.res;


import java.util.List;

/**
 * 类说明 <br>
 * 业务层操作返回值，可以直接返回到页面
 * <p>
 *
 *
 *   {@code
 *   DataResult ret = new DataResult();
 *   }
 *
 *
 * @author : alexliu
 * @version v1.0 , Create at 12:53 PM 2018/11/9
 */
public class PageDataResult extends DataResult {

    private List pageData;

    private Integer total;

    public List getPageData() {
        return pageData;
    }

    public void setPageData(List pageData) {
        this.pageData = pageData;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


}

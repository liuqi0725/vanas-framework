package com.liuqi.vanasframework.core.mvc.res;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 类说明 <br>
 * <p>
 * </p>
 *
 * @author : lau.Q
 * @version v1.0 , Create at 2024/7/19 09:44
 */
@Data
@Builder
public class PageDataVO <T>{

    private List<T> records;

    private long total;

    private long currentPage;

    private long pageSize;

}

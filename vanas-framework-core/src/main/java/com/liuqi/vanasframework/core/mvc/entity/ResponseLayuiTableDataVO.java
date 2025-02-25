package com.liuqi.vanasframework.core.mvc.entity;

import com.alibaba.fastjson.JSON;
import com.liuqi.vanasframework.core.util.WebUtils;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 类说明 <br>
 * <p>
 * </p>
 *
 * @author : lau.Q
 * @version v1.0 , Create at 2024/7/19 09:32
 */
@Getter
public class ResponseLayuiTableDataVO<T> implements Serializable {

    private static final long serialVersionUID = 4090535024459217044L;

    /**
     * 状态码
     *
     */
    private final int code;

    /**
     * 响应信息
     */
    private final String msg;

    /**
     * 响应数据对象
     */
    private final List<T> data;

    /**
     * table 总条数
     */
    private final int total;

    /**
     * 请求id
     */
    private final String requestId;

    public String toString(){
        return JSON.toJSONString(this);
    }

    /**
     * 私有构造函数
     * @param builder 构造器
     */
    @SuppressWarnings("unchecked")
    private ResponseLayuiTableDataVO(Builder<T> builder){
        this.code = builder.code;
        this.msg = StringUtils.isEmpty(builder.msg) ? "" : builder.msg;
        this.data = builder.data == null ? Collections.emptyList() : builder.data;
        this.total = builder.total;
        this.requestId = builder.requestId;
    }

    public static <T> Builder<T> builder(){
        return new Builder<T>();
    }

    public static class Builder<T>{
        private int code;
        private String msg;

        private String requestId;

        private int total;

        private List<T> data;

        public Builder(){
            // 默认成功
            this.code = 0;
            this.requestId = WebUtils.getRequestId();
        }

        public Builder<T> code(boolean code){
            this.code = code ? 0 : 1;
            return this;
        }

        public Builder<T> msg(String msg){
            this.msg = msg;
            return this;
        }

        public Builder<T> requestId(String requestId){
            this.requestId = requestId;
            return this;
        }

        public Builder<T> data(List<T> data){
            this.data = data;
            return this;
        }

        public Builder<T> total(int total){
            this.total = total;
            return this;
        }

        public ResponseLayuiTableDataVO<T> build(){
            return new ResponseLayuiTableDataVO<>(this);
        }
    }

}

package com.liuqi.vanasframework.core.mvc.entity;

import com.alibaba.fastjson.JSON;
import com.liuqi.vanasframework.core.util.WebUtils;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * 类说明 <br>
 * <p>
 * </p>
 *
 * @author : lau.Q
 * @version v1.0 , Create at 2024/3/31 15:20
 */
@Getter
public class ResponseDataVO<T> implements Serializable {

    private static final long serialVersionUID = 7455808373918315510L;
    /**
     * 接口是否成功
     */
    private final boolean success;

    /**
     * 状态码 200 成功
     */
    private final int code;

    /**
     * 响应信息
     */
    private final String msg;

    /**
     * 响应数据对象
     */
    private final T data;

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
    private ResponseDataVO(Builder<T> builder){
        this.success = builder.success;
        this.code = builder.code;
        this.msg = StringUtils.isEmpty(builder.msg) ? "" : builder.msg;
        this.data = builder.data == null ? (T)"" : builder.data;
        this.requestId = builder.requestId;
    }

    public static <T> Builder<T> builder(){
        return new Builder<T>();
    }

    public static class Builder<T>{
        private boolean success;
        private int code;
        private String msg;
        private T data;
        private String requestId;

        public Builder(){
            // 默认成功
            this.success = true;
            this.code = 200;
            this.requestId = WebUtils.getRequestId();
        }

        public Builder<T> success(boolean success){
            this.success = success;
            this.code = 200;
            return this;
        }

        public Builder<T> code(int code){
            this.code = code;
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

        public Builder<T> data(T data){
            this.data = data;
            return this;
        }

        public ResponseDataVO<T> build(){
            return new ResponseDataVO<>(this);
        }
    }

}

package com.liuqi.vanasframework.core.tuple.ex;

import com.liuqi.vanasframework.core.conf.norm.ResultStatus;
import com.liuqi.vanasframework.core.tuple.TwoTuple;

/**
 * 类说明 <br>
 *     请求返回消息的元祖
 *
 * @author : alexliu
 * @version v1.0 , Create at 13:50 2020-04-29
 */
public class RequestMsgResultTuple extends TwoTuple<ResultStatus, String> {

    public RequestMsgResultTuple(ResultStatus var1, String var2) {
        super(var1, var2);
    }
}

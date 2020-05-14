package com.liuqi.vanasframework.core.tuple;

/**
 * 类说明 <br>
 *     2 位参数的元祖
 *
 * @author : alexliu
 * @version v1.0 , Create at 11:33 2020-04-29
 */
public class TwoTuple<A,B>{

    public final A var1;

    public final B var2;

    public TwoTuple(A var1, B var2){
        this.var1 = var1;
        this.var2 = var2;
    }
}

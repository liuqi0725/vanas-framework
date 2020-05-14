package com.liuqi.vanasframework.core.tuple;

/**
 * 类说明 <br>
 *     3 位参数的元祖
 *
 * @author : alexliu
 * @version v1.0 , Create at 11:33 2020-04-29
 */
public class ThreeTuple<A,B,C> extends TwoTuple<A,B>{

    public final C var3;

    public ThreeTuple(A var1, B var2, C var3){
        super(var1,var2);
        this.var3 = var3;
    }
}

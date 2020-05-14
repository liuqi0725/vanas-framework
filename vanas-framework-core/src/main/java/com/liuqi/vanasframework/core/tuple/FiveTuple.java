package com.liuqi.vanasframework.core.tuple;

/**
 * 类说明 <br>
 *     5 位参数的元祖
 *
 * @author : alexliu
 * @version v1.0 , Create at 11:33 2020-04-29
 */
public class FiveTuple<A,B,C,D,E> extends FourTuple<A,B,C,D>{

    public final E var5;

    public FiveTuple(A var1, B var2, C var3, D var4, E var5){
        super(var1, var2, var3 , var4);
        this.var5 = var5;
    }
}

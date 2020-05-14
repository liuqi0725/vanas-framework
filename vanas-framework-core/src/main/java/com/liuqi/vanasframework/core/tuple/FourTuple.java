package com.liuqi.vanasframework.core.tuple;

/**
 * 类说明 <br>
 *     4 位参数的元祖
 *
 * @author : alexliu
 * @version v1.0 , Create at 11:33 2020-04-29
 */
public class FourTuple<A,B,C,D> extends ThreeTuple<A,B,C>{

    public final D var4;

    public FourTuple(A var1, B var2, C var3, D var4){
        super(var1,var2,var3);
        this.var4 = var4;
    }
}

package com.sun.study.module.HighQualityCode;

/**
 * Created by sunfusheng on 15/12/17.
 */
public class _2_Base_Type {

    /**
     * 第二章 基本类型
     *
     * 不积跬步，无以至千里；
     * 不积小流，无以成江海。 －－荀子《劝学篇》
     *
     * 千里之行始于足下之基础
     */

    /**
     * 用偶判断，不用奇判断
     *
     * 用整数类型处理货币，下面两种方式比较好：
     * 1.BigDecimal 2.使用整数（放大100倍，计算完再缩小100倍）
     *
     * 不要让类型默默转换
     *
     * 不要让四舍五入亏啦一方
     * Math.round(10.5); //11
     * Math.round(-10.5); //10
     * Math.round采用的是正无穷舍入规则，即RoundingMode.ROUND_CEILING
     *
     * 谨慎包装类型的大小比较
     * Java八种基本数据类型的包装类是类啊，==, >, <, 直接比较出问题
     * 可以使用包装类的compareTo进行比较哦
     *
     * 优先使用整形池(i >= -128 && i <= 127)
     *
     */

}

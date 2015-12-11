/*
 * 作者：孙福生
 *
 * GitHub：https://github.com/sfsheng0322
 *  微博：http://weibo.com/3852192525/profile?topnav=1&wvr=6
 *
 *  Copyright (c) 2015.
 */

package com.sun.study.module.HighQualityCode;

/**
 * Created by sunfusheng on 15/12/11.
 */
public class _1_Methods_and_Rules {

    /**
     * 第一章 Java开发中通用的方法贺准则
     *
     * The reasonable man adapts himself to the world; the unreasonable one
     * persists in trying to adapt the world to himself.
     * 明白事理的人使自己适应世界，不明白事理的人想让世界适应自己。－－萧伯纳
     *
     * 千里之行始于足下之基础
     */


    /**
     * 三元操作符的类型务必一致
     */
    public void suggestion3() {
        int i = 80;
        String s1 = String.valueOf(i < 100? 90:110);
        String s2 = String.valueOf(i < 100? 90:110.0);
        System.out.print("两者是否相等：" + s1.equals(s2)); // false
    }

    /**
     * 避免带有变长参数的方法重载
     *
     * 变长参数的规则：
     * 变长参数必须是方法中最后一个参数
     * 一个方法不能定义多个变长参数
     */
}

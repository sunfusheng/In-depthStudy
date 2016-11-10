package com.sun.study.model;

/**
 * Created by sunfusheng on 2016/11/10.
 */

public class PointEntity {

    public float x;
    public float y;

    public PointEntity() {
    }

    public PointEntity(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "PointEntity{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

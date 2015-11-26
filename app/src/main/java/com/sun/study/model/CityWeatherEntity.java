package com.sun.study.model;

import java.io.Serializable;

/**
 * Created by sunfusheng on 15/11/20.
 */
public class CityWeatherEntity implements Serializable {

    private int errNum;
    private String errMsg;
    private CityWeatherDataEntity retData;

    public int getErrNum() {
        return errNum;
    }

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public CityWeatherDataEntity getRetData() {
        return retData;
    }

    public void setRetData(CityWeatherDataEntity retData) {
        this.retData = retData;
    }
}

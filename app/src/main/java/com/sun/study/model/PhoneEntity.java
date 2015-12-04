package com.sun.study.model;

import java.io.Serializable;

/**
 * Created by sunfusheng on 15/12/1.
 */
public class PhoneEntity implements Serializable {

    private int errNum;
    private String retMsg;
    private RetDataEntity retData;

    public void setErrNum(int errNum) {
        this.errNum = errNum;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public void setRetData(RetDataEntity retData) {
        this.retData = retData;
    }

    public int getErrNum() {
        return errNum;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public RetDataEntity getRetData() {
        return retData;
    }

    public static class RetDataEntity {

        private String telString;
        private String province;
        private String carrier;

        public void setTelString(String telString) {
            this.telString = telString;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public void setCarrier(String carrier) {
            this.carrier = carrier;
        }

        public String getTelString() {
            return telString;
        }

        public String getProvince() {
            return province;
        }

        public String getCarrier() {
            return carrier;
        }
    }
}

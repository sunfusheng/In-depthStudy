package com.sun.study.model;

/**
 * Created by sunfusheng on 15/11/20.
 */
public class CityDistrictEntity {

    private String province_cn;
    private String district_cn;
    private String name_cn;
    private String name_en;
    private String area_id;

    public void setProvince_cn(String province_cn) {
        this.province_cn = province_cn;
    }

    public void setDistrict_cn(String district_cn) {
        this.district_cn = district_cn;
    }

    public void setName_cn(String name_cn) {
        this.name_cn = name_cn;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getProvince_cn() {
        return province_cn;
    }

    public String getDistrict_cn() {
        return district_cn;
    }

    public String getName_cn() {
        return name_cn;
    }

    public String getName_en() {
        return name_en;
    }

    public String getArea_id() {
        return area_id;
    }
}

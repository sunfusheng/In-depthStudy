package com.sun.study.module.okhttp;

import com.sun.study.constant.ConstantParams;

/**
 * Created by sunfusheng on 15/11/19.
 */
public interface UrlManager {

    // 天气查询－根据城市名称
    String URL_CITY_WEATHER = ConstantParams.APISTORE_BASE_URL + "weatherservice/cityname";

    // 天气查询－查询可用城市列表
    String URL_WEATHER_CITY_LIST = ConstantParams.APISTORE_BASE_URL + "weatherservice/citylist";

    // 手机号码归属地
    String URL_PHONE_NUM_PLACE = ConstantParams.APISTORE_BASE_URL + "mobilephoneservice/mobilephone";

}

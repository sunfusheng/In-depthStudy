package com.sun.study.module.okhttp;

/**
 * Created by sunfusheng on 15/11/19.
 */
public interface UrlManager {

    // 天气查询－BASE URL
    String BASE_URL_WEATHER = "http://apis.baidu.com/apistore/weatherservice/";

    // 天气查询－根据城市名称
    String URL_CITY_WEATHER = BASE_URL_WEATHER + "cityname";

    // 天气查询－查询可用城市列表
    String URL_WEATHER_CITY_LIST = BASE_URL_WEATHER + "citylist";

}

package com.sun.study.module.okhttp;

import com.sun.study.constant.ConstantParams;
import com.sun.study.model.CityDistrictEntity;
import com.sun.study.model.CityWeatherDataEntity;
import com.sun.study.model.CityWeatherEntity;
import com.sun.study.model.PhoneEntity;
import com.sun.study.module.okhttp.request.OkHttp;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sunfusheng on 15/11/19.
 */
public class HttpApi implements UrlManager {

    private OkHttp.Builder mHttp;

    public HttpApi() {
        mHttp = new OkHttp.Builder();
    }

    // 天气查询－根据城市名称
    public CityWeatherEntity getCityWeather(String cityname) throws IOException, com.alibaba.fastjson.JSONException, org.json.JSONException {
        mHttp = mHttp.url(URL_CITY_WEATHER)
                .addHeader("apikey", ConstantParams.APISTORE_API_KEY)
                .addParams("cityname", cityname);
        return mHttp.get(CityWeatherEntity.class);
    }

    // 天气查询－根据城市名称
    public CityWeatherDataEntity getCityWeatherData(String cityname) throws IOException, com.alibaba.fastjson.JSONException, org.json.JSONException {
        mHttp = mHttp.url(URL_CITY_WEATHER)
                .addHeader("apikey", ConstantParams.APISTORE_API_KEY)
                .addParams("cityname", cityname);
        return mHttp.get("retData", CityWeatherDataEntity.class);
    }

    // 天气查询－查询该城市下可用区域列表
    public ArrayList<CityDistrictEntity> getWeatherCityDistrictList(String cityname) throws IOException, com.alibaba.fastjson.JSONException, org.json.JSONException {
        mHttp = mHttp.url(URL_WEATHER_CITY_LIST)
                .addHeader("apikey", ConstantParams.APISTORE_API_KEY)
                .addParams("cityname", cityname);
        return mHttp.getList("retData", CityDistrictEntity.class);
    }

    // 手机号码归属地
    public PhoneEntity getPhoneNumPlace3(String phone) throws IOException, com.alibaba.fastjson.JSONException, org.json.JSONException {
        mHttp = mHttp.url(URL_PHONE_NUM_PLACE)
                .addHeader("apikey", ConstantParams.APISTORE_API_KEY)
                .addParams("phone", phone);
        return mHttp.get(PhoneEntity.class);
    }

}

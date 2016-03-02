package com.sun.study.control;

import com.sun.study.constant.ConstantParams;
import com.sun.study.model.CityDistrictEntity;
import com.sun.study.model.CityWeatherDataEntity;
import com.sun.study.model.CityWeatherEntity;
import com.sun.study.model.PhoneEntity;
import com.sun.study.module.okhttp.OkHttpProxy;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sunfusheng on 15/11/19.
 */
public class HttpApi implements UrlManager {

    private static HttpApi mApi;

    private HttpApi() {}

    public static HttpApi getInstance() {
        if (mApi == null) {
            synchronized (HttpApi.class) {
                if (mApi == null) {
                    mApi = new HttpApi();
                }
            }
        }
        return mApi;
    }

    // 天气查询－根据城市名称
    public CityWeatherEntity getCityWeather(String cityname) throws IOException, com.alibaba.fastjson.JSONException, org.json.JSONException {
        return OkHttpProxy.get().url(URL_CITY_WEATHER)
                .addHeader("apikey", ConstantParams.APISTORE_API_KEY)
                .addParams("cityname", cityname)
                .build()
                .execute(CityWeatherEntity.class);
    }

    // 天气查询－根据城市名称
    public CityWeatherDataEntity getCityWeatherData(String cityname) throws IOException, com.alibaba.fastjson.JSONException, org.json.JSONException {
        return OkHttpProxy.get().url(URL_CITY_WEATHER)
                .addHeader("apikey", ConstantParams.APISTORE_API_KEY)
                .addParams("cityname", cityname)
                .build()
                .execute("retData", CityWeatherDataEntity.class);
    }

    // 天气查询－查询该城市下可用区域列表
    public ArrayList<CityDistrictEntity> getWeatherCityDistrictList(String cityname) throws IOException, com.alibaba.fastjson.JSONException, org.json.JSONException {
        return OkHttpProxy.get().url(URL_WEATHER_CITY_LIST)
                .addHeader("apikey", ConstantParams.APISTORE_API_KEY)
                .addParams("cityname", cityname)
                .build()
                .executeForList("retData", CityDistrictEntity.class);
    }

    // 手机号码归属地
    public PhoneEntity getPhoneNumPlace3(String tel) throws IOException, com.alibaba.fastjson.JSONException, org.json.JSONException {
        return OkHttpProxy.get().url(URL_PHONE_NUM_PLACE)
                .addHeader("apikey", ConstantParams.APISTORE_API_KEY)
                .addParams("tel", tel).build()
                .execute(PhoneEntity.class);
    }

    public PhoneEntity getMyApps() throws IOException, com.alibaba.fastjson.JSONException, org.json.JSONException {
        return OkHttpProxy.get().url("http://www.pgyer.com/apiv1/app/builds")
                .addParams("aKey", "1a2c7ad534c07fa34c088fc4e8f00b83")
                .addParams("_api_key", "216dc1b595a05072ba3642a7843bad92")
                .build()
                .execute(PhoneEntity.class);
    }

}

package com.sun.study.control;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.sun.study.constant.ConstantParams;
import com.sun.study.framework.annotation.AsyncAtomMethod;
import com.sun.study.framework.base.BaseControl;
import com.sun.study.framework.proxy.MessageProxy;
import com.sun.study.model.CityDistrictEntity;
import com.sun.study.model.CityWeatherDataEntity;
import com.sun.study.model.CityWeatherEntity;
import com.sun.study.model.PhoneEntity;
import com.sun.study.module.okhttp.HttpApi;
import com.sun.study.module.okhttp.UrlManager;
import com.sun.study.module.retrofit.RetrofitFactory;

import java.util.ArrayList;

import retrofit.Call;

/**
 * Created by sunfusheng on 15/11/5.
 */
public class SingleControl extends BaseControl {

    private HttpApi api = new HttpApi();

    public SingleControl(MessageProxy mMessageCallBack) {
        super(mMessageCallBack);
    }

    @AsyncAtomMethod(withCancelableDialog = true)
    public void getCityWeather(String cityname) {
        try {
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder().url(UrlManager.URL_CITY_WEATHER + "?cityname=" +cityname)
                    .addHeader("apikey", ConstantParams.APIKEY_APISTORE)
                    .build();
            Response response = client.newCall(request).execute();
            CityWeatherEntity entity = null;
            if (response.isSuccessful()) {
                entity = JSON.parseObject(response.body().string(), CityWeatherEntity.class);
            }
            mModel.put(1, entity);
            sendMessage("getCityWeatherCallBack");
        } catch (Exception e) {
            dealWithException(e);
        }
    }

    @AsyncAtomMethod(withCancelableDialog = true)
    public void getCityWeatherSimple(String cityname) {
        try {
            CityWeatherEntity entity = api.getCityWeather(cityname);
            mModel.put(1, entity);
            sendMessage("getCityWeatherCallBack");
        } catch (Exception e) {
            dealWithException(e);
        }
    }

    @AsyncAtomMethod(withCancelableDialog = true)
    public void getCityWeatherDataSimple(String cityname) {
        try {
            CityWeatherDataEntity entity = api.getCityWeatherData(cityname);
            mModel.put(1, entity);
            sendMessage("getCityWeatherDataSimpleCallBack");
        } catch (Exception e) {
            dealWithException(e);
        }
    }

    @AsyncAtomMethod(withCancelableDialog = true)
    public void getWeatherCityDistrictList(String cityname) {
        try {
            ArrayList<CityDistrictEntity> list = api.getWeatherCityDistrictList(cityname);
            mModel.put(1, list);
            sendMessage("getWeatherCityDistrictListCallBack");
        } catch (Exception e) {
            dealWithException(e);
        }
    }

    @AsyncAtomMethod(withCancelableDialog = true)
    public void getPhoneNumPlace(String phone) {
        try {
            Call<PhoneEntity> entity = RetrofitFactory.get().getPhoneNumPlace(phone, ConstantParams.APIKEY_APISTORE);
            PhoneEntity phoneEntity = entity.execute().body();
            mModel.put(1, phoneEntity);
            sendMessage("getPhoneNumPlaceCallBack");
        } catch (Exception e) {
            dealWithException(e);
        }
    }
}

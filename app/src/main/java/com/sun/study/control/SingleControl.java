package com.sun.study.control;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.sun.study.constant.ConstantParams;
import com.sun.study.framework.annotation.AsyncAtomMethod;
import com.sun.study.framework.base.BaseControl;
import com.sun.study.framework.proxy.MessageProxy;
import com.sun.study.model.AppInfoEntity;
import com.sun.study.model.CityDistrictEntity;
import com.sun.study.model.CityWeatherDataEntity;
import com.sun.study.model.CityWeatherEntity;
import com.sun.study.model.PhoneEntity;
import com.sun.study.module.okhttp.HttpApi;
import com.sun.study.module.okhttp.UrlManager;
import com.sun.study.module.retrofit.RetrofitFactory;
import com.sun.study.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;

/**
 * Created by sunfusheng on 15/11/5.
 */
public class SingleControl extends BaseControl {

    private static HttpApi mApi;

    public SingleControl(MessageProxy mMessageCallBack) {
        super(mMessageCallBack);
        mApi = HttpApi.getInstance();
    }

    @AsyncAtomMethod(withCancelableDialog = true)
    public void getCityWeather(String cityname) {
        try {
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder().url(UrlManager.URL_CITY_WEATHER + "?cityname=" +cityname)
                    .addHeader("apikey", ConstantParams.APISTORE_API_KEY)
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
            CityWeatherEntity entity = mApi.getCityWeather(cityname);
            mModel.put(1, entity);
            sendMessage("getCityWeatherCallBack");
        } catch (Exception e) {
            dealWithException(e);
        }
    }

    @AsyncAtomMethod(withCancelableDialog = true)
    public void getCityWeatherDataSimple(String cityname) {
        try {
            CityWeatherDataEntity entity = mApi.getCityWeatherData(cityname);
            mModel.put(1, entity);
            sendMessage("getCityWeatherDataSimpleCallBack");
        } catch (Exception e) {
            dealWithException(e);
        }
    }

    @AsyncAtomMethod(withCancelableDialog = true)
    public void getWeatherCityDistrictList(String cityname) {
        try {
            ArrayList<CityDistrictEntity> list = mApi.getWeatherCityDistrictList(cityname);
            mModel.put(1, list);
            sendMessage("getWeatherCityDistrictListCallBack");
        } catch (Exception e) {
            dealWithException(e);
        }
    }

    @AsyncAtomMethod
    public void getPhoneNumPlace(String tel) {
        try {
            Call<PhoneEntity> call = RetrofitFactory.get().getPhoneNumPlace(tel);
            PhoneEntity phoneEntity = call.execute().body();
            mModel.put(1, phoneEntity);
            sendMessage("getPhoneNumPlaceCallBack");
        } catch (Exception e) {
            dealWithException(e);
        }
    }

    @AsyncAtomMethod
    public void getPhoneNumPlace3(String tel) {
        try {
            PhoneEntity phoneEntity = mApi.getPhoneNumPlace3(tel);
            mModel.put(1, phoneEntity);
            sendMessage("getPhoneNumPlaceCallBack");
        } catch (Exception e) {
            dealWithException(e);
        }
    }

    @AsyncAtomMethod
    public void getMyApps() {
        try {
            PhoneEntity phoneEntity = mApi.getMyApps();
            mModel.put(1, phoneEntity);
            sendMessage("getMyAppsCallBack");
        } catch (Exception e) {
            dealWithException(e);
        }
    }

    @AsyncAtomMethod(withCancelableDialog = true)
    public void getApkListFromDownload(Context context) {
        try {
            List<AppInfoEntity> apkList = AppUtil.getApkListFromDownload(context);
            mModel.put(1, apkList);
            sendMessage("getApkListFromDownloadCallBack");
        } catch (Exception e) {
            dealWithException(e);
        }
    }

}

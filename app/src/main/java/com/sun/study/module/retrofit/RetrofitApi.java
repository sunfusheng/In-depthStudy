package com.sun.study.module.retrofit;

import com.sun.study.model.PhoneEntity;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by sunfusheng on 15/12/1.
 */
public interface RetrofitApi {

    @GET("mobilenumber/mobilenumber")
    Call<PhoneEntity> getPhoneNumPlace(@Query("phone") String phone);

}

package com.sun.study.module.retrofit;

import com.sun.study.model.PhoneEntity;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by sunfusheng on 15/12/1.
 */
public interface RetrofitApi {

    @GET("mobilephoneservice/mobilephone")
    Call<PhoneEntity> getPhoneNumPlace(@Query("tel") String tel);

    @GET("mobilephoneservice/mobilephone")
    Observable<PhoneEntity> getPhoneNumPlaceByRxJava(@Query("tel") String tel);

}

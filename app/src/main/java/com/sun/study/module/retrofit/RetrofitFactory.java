package com.sun.study.module.retrofit;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by sunfusheng on 15/12/1.
 */
public class RetrofitFactory {

    public static IRetrofit mApi;
    public static final String BSAE_URL = "http://apis.baidu.com/apistore/mobilenumber/";

    private RetrofitFactory() {}

    public static IRetrofit get() {
        if (mApi == null) {
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setReadTimeout(21, TimeUnit.SECONDS);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BSAE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mApi = retrofit.create(IRetrofit.class);
        }
        return mApi;
    }

}

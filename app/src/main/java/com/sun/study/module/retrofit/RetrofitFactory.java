package com.sun.study.module.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.sun.study.constant.ConstantParams;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by sunfusheng on 15/12/1.
 */
public class RetrofitFactory {

    public static RetrofitApi mApi;
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

    private RetrofitFactory() {}

    public static RetrofitApi get() {
        if (mApi == null) {
            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
            okHttpClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder().addHeader("apikey", ConstantParams.APISTORE_API_KEY).build();
                    return chain.proceed(newRequest);
                }
            });

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConstantParams.APISTORE_BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            mApi = retrofit.create(RetrofitApi.class);
        }
        return mApi;
    }

}

package com.sun.study.module.okhttp.core;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * Created by sunfusheng on 15/12/9.
 */
public class OkHttpProxy {

    private static OkHttpClient mOkHttpClient;

    public static OkHttpClient getInstance() {
        if (mOkHttpClient == null) {
            synchronized (OkHttpProxy.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient();
                    mOkHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);
                    mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
                    mOkHttpClient.setWriteTimeout(30, TimeUnit.SECONDS);
                }
            }
        }
        return mOkHttpClient;
    }
}

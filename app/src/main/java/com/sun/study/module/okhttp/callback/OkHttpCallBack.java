package com.sun.study.module.okhttp.callback;

import com.squareup.okhttp.Request;

/**
 * Created by sunfusheng on 15/12/8.
 */
public abstract class OkHttpCallBack<T> {

    public OkHttpCallBack() {
    }

    public void onBefore(Request request) {
    }

    public void onAfter() {
    }

    public void inProgress(float progress) {
    }

    public abstract void onError(Request request, Exception e);

    public abstract void onResponse(T response);


    public static final OkHttpCallBack<String> DEFAULT_RESULT_CALLBACK = new OkHttpCallBack<String>() {

        @Override
        public void onError(Request request, Exception e) {
        }

        @Override
        public void onResponse(String response) {
        }
    };
}

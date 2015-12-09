package com.sun.study.module.okhttp.callback;

import com.squareup.okhttp.Request;

/**
 * Created by sunfusheng on 15/12/8.
 */
public abstract class OkHttpCallBack<T> {

    public void onBefore(Request request) {}

    public void onAfter() {}

    public void inProgress(float progress) {}

    public abstract void onSuccess(String response);

    public abstract void onFailure(Request request, Exception e);

    public static final OkHttpCallBack<String> DEFAULT_CALLBACK = new OkHttpCallBack<String>() {
        @Override
        public void onSuccess(String response) {}

        @Override
        public void onFailure(Request request, Exception e) {}
    };
}

package com.sun.study.module.okhttp.core;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.sun.study.constant.GlobalParams;
import com.sun.study.module.okhttp.callback.OkHttpCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by sunfusheng on 15/11/19.
 */
public abstract class OkHttp {

    protected static OkHttpClient mOkHttpClient;
    protected static OkHttpClientManager mOkHttpClientManager;

    protected RequestBody requestBody;
    protected Request request;

    protected String url;
    protected String tag;
    protected Map<String, String> params;
    protected Map<String, String> headers;

    public OkHttp(String url) {
        this.url = url;
        mOkHttpClient = OkHttp.getClient();
        mOkHttpClientManager = OkHttpClientManager.getInstance();
    }

    public OkHttp(String url, Map<String, String> params) {
        this(url);
        this.params = params;
    }

    public OkHttp(String url, Map<String, String> params, Map<String, String> headers) {
        this(url, params);
        this.headers = headers;
    }

    public OkHttp(String url, Map<String, String> params, Map<String, String> headers, String tag) {
        this(url, params, headers);
        this.tag = tag;
    }

    public static OkHttpClient getClient() {
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

    public abstract Request buildRequest();
    public abstract RequestBody buildRequestBody();

    public void prepareInvoked(OkHttpCallBack callback) {
        requestBody = buildRequestBody();
        requestBody = wrapRequestBody(requestBody, callback);
        request = buildRequest();
    }

    public void invokeAsync(OkHttpCallBack callback) {
        prepareInvoked(callback);
        mOkHttpClientManager.execute(request, callback);
    }

    public RequestBody wrapRequestBody(RequestBody requestBody, final OkHttpCallBack callback) {
        return requestBody;
    }

    public <T> T invoke(Class<T> clazz) throws IOException, JSONException {
        return invoke(null, clazz);
    }

    public <T> T invoke(String parseWhat, Class<T> clazz) throws IOException, JSONException {
        requestBody = buildRequestBody();
        Request request = buildRequest();
        return execute(parseWhat, request, clazz);
    }

    private  <T> T execute(String parseWhat, Request request, Class<T> clazz) throws IOException, JSONException {
        String response = mOkHttpClientManager.execute(request);
        Logger.d(GlobalParams.LOG_TAG_CONTENT, ""+response);
        if (!TextUtils.isEmpty(parseWhat)) {
            JSONObject jsonObject = new JSONObject(response);
            return JSON.parseObject(jsonObject.getString(parseWhat), clazz);
        }
        return JSON.parseObject(response, clazz);
    }

    public <T> ArrayList<T> invokeForList(String parseWhat, Class<T> clazz) throws IOException, JSONException {
        requestBody = buildRequestBody();
        Request request = buildRequest();
        return executeForList(parseWhat, request, clazz);
    }

    private  <T> ArrayList<T> executeForList(String parseWhat, Request request, Class<T> clazz) throws IOException, JSONException {
        String response = mOkHttpClientManager.execute(request);
        Logger.d(GlobalParams.LOG_TAG_CONTENT, ""+response);
        if (!TextUtils.isEmpty(parseWhat)) {
            JSONObject jsonObject = new JSONObject(response);
            return new ArrayList<T>(JSON.parseArray(jsonObject.getString(parseWhat), clazz));
        }
        return null;
    }

    public void appendHeaders(Request.Builder builder, Map<String, String> headers) {
        if (builder == null) {
            throw new IllegalArgumentException("builder can not be empty!");
        }

        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return ;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

}

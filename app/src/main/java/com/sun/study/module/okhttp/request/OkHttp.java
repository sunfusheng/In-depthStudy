package com.sun.study.module.okhttp.request;

import android.text.TextUtils;
import android.util.Pair;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.sun.study.constant.GlobalParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by sunfusheng on 15/11/19.
 */
public abstract class OkHttp {

    protected OkHttpClient mOkHttpClient;

    protected RequestBody requestBody;
    protected Request request;

    protected String url;
    protected String tag;
    protected Map<String, String> params;
    protected Map<String, String> headers;

    protected OkHttp(String url) {
        this.url = url;
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(100, TimeUnit.SECONDS);
    }

    protected OkHttp(String url, Map<String, String> params) {
        this(url);
        this.params = params;
    }

    protected OkHttp(String url, Map<String, String> params, String tag, Map<String, String> headers) {
        this(url, params);
        this.tag = tag;
        this.headers = headers;
    }

    protected abstract Request buildRequest();
    protected abstract RequestBody buildRequestBody();

    public <T> T invoke(String parseWhat, Class<T> clazz) throws IOException, JSONException {
        requestBody = buildRequestBody();
        Request request = buildRequest();
        return execute(parseWhat, request, clazz);
    }

    private  <T> T execute(String parseWhat, Request request, Class<T> clazz) throws IOException, JSONException {
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        String respStr = execute.body().string();
        Logger.d(GlobalParams.LOG_TAG_CONTENT, ""+respStr);
        if (!TextUtils.isEmpty(parseWhat)) {
            JSONObject jsonObject = new JSONObject(respStr);
            int errNum = jsonObject.getInt("errNum");
            String errMsg = jsonObject.getString("errMsg");
            return JSON.parseObject(jsonObject.getString(parseWhat), clazz);
        }
        return JSON.parseObject(respStr, clazz);
    }

    public <T> ArrayList<T> invokeForList(String parseWhat, Class<T> clazz) throws IOException, JSONException {
        requestBody = buildRequestBody();
        Request request = buildRequest();
        return executeForList(parseWhat, request, clazz);
    }

    private  <T> ArrayList<T> executeForList(String parseWhat, Request request, Class<T> clazz) throws IOException, JSONException {
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        String respStr = execute.body().string();
        Logger.d(GlobalParams.LOG_TAG_CONTENT, ""+respStr);
        if (!TextUtils.isEmpty(parseWhat)) {
            JSONObject jsonObject = new JSONObject(respStr);
            int errNum = jsonObject.getInt("errNum");
            String errMsg = jsonObject.getString("errMsg");
            return new ArrayList<T>(JSON.parseArray(jsonObject.getString(parseWhat), clazz));
        }
        return null;
    }

    protected void appendHeaders(Request.Builder builder, Map<String, String> headers) {
        if (builder == null) {
            throw new IllegalArgumentException("builder can not be empty!");
        }

        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) return;

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }


    public static class Builder {

        private String url;
        private String tag;
        private Map<String, String> headers;
        private Map<String, String> params;
        private Pair<String, File>[] files;
        private MediaType mediaType;

        private String destFileDir;
        private String destFileName;

        private String content;
        private byte[] bytes;
        private File file;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder addParams(String key, String val) {
            if (this.params == null) {
                params = new IdentityHashMap<>();
            }
            params.put(key, val);
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder addHeader(String key, String val) {
            if (this.headers == null) {
                headers = new IdentityHashMap<>();
            }
            headers.put(key, val);
            return this;
        }


        public Builder files(Pair<String, File>... files) {
            this.files = files;
            return this;
        }

        public Builder destFileName(String destFileName) {
            this.destFileName = destFileName;
            return this;
        }

        public Builder destFileDir(String destFileDir) {
            this.destFileDir = destFileDir;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder mediaType(MediaType mediaType) {
            this.mediaType = mediaType;
            return this;
        }

        public <T> T get(Class<T> clazz) throws IOException, JSONException {
            OkHttp request = new OkHttpGet(url, tag, params, headers);
            return request.invoke(null, clazz);
        }

        public <T> T get(String parseWhat, Class<T> clazz) throws IOException, JSONException {
            OkHttp request = new OkHttpGet(url, tag, params, headers);
            return request.invoke(parseWhat, clazz);
        }

        public <T> ArrayList<T> getList(String parseWhat, Class<T> clazz) throws IOException, JSONException {
            OkHttp request = new OkHttpGet(url, tag, params, headers);
            return request.invokeForList(parseWhat, clazz);
        }

        public <T> T post(Class<T> clazz) throws IOException, JSONException {
            OkHttp request = new OkHttpPost(url, tag, params, headers, mediaType, content, bytes, file);
            return request.invoke(null, clazz);
        }
    }


}

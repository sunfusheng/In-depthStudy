package com.sun.study.module.okhttp.core;

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
import com.sun.study.module.okhttp.callback.OkHttpCallBack;
import com.sun.study.module.okhttp.request.OkHttpDownload;
import com.sun.study.module.okhttp.request.OkHttpGet;
import com.sun.study.module.okhttp.request.OkHttpPost;
import com.sun.study.module.okhttp.request.OkHttpUpload;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Created by sunfusheng on 15/11/19.
 */
public abstract class OkHttp {

    protected OkHttpClientManager mOkHttpClientManager = OkHttpClientManager.getInstance();
    protected OkHttpClient mOkHttpClient;

    protected RequestBody requestBody;
    protected Request request;

    protected String url;
    protected String tag;
    protected Map<String, String> params;
    protected Map<String, String> headers;

    public OkHttp(String url) {
        this.url = url;
        mOkHttpClient = OkHttpProxy.getInstance();
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

    public void appendHeaders(Request.Builder builder, Map<String, String> headers) {
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
            OkHttp request = new OkHttpGet(url, params, headers, tag);
            return request.invoke(null, clazz);
        }

        public <T> T get(String parseWhat, Class<T> clazz) throws IOException, JSONException {
            OkHttp request = new OkHttpGet(url, params, headers, tag);
            return request.invoke(parseWhat, clazz);
        }

        public OkHttp get(OkHttpCallBack callback) {
            OkHttp request = new OkHttpGet(url, params, headers, tag);
            request.invokeAsync(callback);
            return request;
        }

        public <T> ArrayList<T> getList(String parseWhat, Class<T> clazz) throws IOException, JSONException {
            OkHttp request = new OkHttpGet(url, params, headers, tag);
            return request.invokeForList(parseWhat, clazz);
        }

        public <T> T post(Class<T> clazz) throws IOException, JSONException {
            OkHttp request = new OkHttpPost(url, params, headers, tag, mediaType, content, bytes, file);
            return request.invoke(null, clazz);
        }

        public OkHttp post(OkHttpCallBack callback) {
            OkHttp request = new OkHttpPost(url, params, headers, tag, mediaType, content, bytes, file);
            request.invokeAsync(callback);
            return request;
        }

        public OkHttp upload(OkHttpCallBack callback) {
            OkHttp request = new OkHttpUpload(url, params, headers, tag, files);
            request.invokeAsync(callback);
            return request;
        }

        public <T> T upload(Class<T> clazz) throws IOException, JSONException {
            OkHttp request = new OkHttpUpload(url, params, headers, tag, files);
            return request.invoke(clazz);
        }

        public OkHttp download(OkHttpCallBack callback) {
            OkHttp request = new OkHttpDownload(url, params, headers, tag, destFileName, destFileDir);
            request.invokeAsync(callback);
            return request;
        }

        public String download() throws IOException, JSONException {
            OkHttp request = new OkHttpDownload(url, params, headers, tag, destFileName, destFileDir);
            return request.invoke(String.class);
        }
    }

}

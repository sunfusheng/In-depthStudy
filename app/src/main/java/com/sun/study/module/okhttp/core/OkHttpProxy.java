package com.sun.study.module.okhttp.core;

import android.text.TextUtils;
import android.util.Pair;

import com.squareup.okhttp.MediaType;
import com.sun.study.module.okhttp.callback.OkHttpCallBack;
import com.sun.study.module.okhttp.request.OkHttpDownload;
import com.sun.study.module.okhttp.request.OkHttpGet;
import com.sun.study.module.okhttp.request.OkHttpPost;
import com.sun.study.module.okhttp.request.OkHttpUpload;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Created by sunfusheng on 15/12/9.
 */
public class OkHttpProxy {

    private String url;
    private String tag;
    private Map<String, String> params;
    private Map<String, String> headers;

    private String destFileDir;
    private String destFileName;

    private String content;
    private byte[] bytes;
    private File file;
    private Pair<String, File>[] files;
    private MediaType mediaType;

    private OkHttpProxy() {}

    public static OkHttpProxy get() {
        return new OkHttpProxy();
    }

    public OkHttpProxy url(String url) {
        this.url = url;
        return this;
    }

    public OkHttpProxy tag(String tag) {
        this.tag = tag;
        return this;
    }

    public void cancel() {
        if (!TextUtils.isEmpty(tag)) {
            OkHttp.getClient().cancel(tag);
        }
    }

    public OkHttpProxy params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public OkHttpProxy addParams(String key, String val) {
        if (this.params == null) {
            params = new IdentityHashMap<>();
        }
        params.put(key, val);
        return this;
    }

    public OkHttpProxy headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public OkHttpProxy addHeader(String key, String val) {
        if (this.headers == null) {
            headers = new IdentityHashMap<>();
        }
        headers.put(key, val);
        return this;
    }

    public OkHttpProxy file(File file) {
        this.file = file;
        return this;
    }

    public OkHttpProxy files(Pair<String, File>... files) {
        this.files = files;
        return this;
    }

    public OkHttpProxy destFileName(String destFileName) {
        this.destFileName = destFileName;
        return this;
    }

    public OkHttpProxy destFileDir(String destFileDir) {
        this.destFileDir = destFileDir;
        return this;
    }

    public OkHttpProxy content(String content) {
        this.content = content;
        return this;
    }

    public OkHttpProxy bytes(byte[] bytes) {
        this.bytes = bytes;
        return this;
    }

    public OkHttpProxy mediaType(MediaType mediaType) {
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

package com.sun.study.module.okhttp.request;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.sun.study.constant.GlobalParams;
import com.sun.study.module.okhttp.core.OkHttp;

import java.util.Map;

/**
 * Created by sunfusheng on 15/11/19.
 */
public class OkHttpGet extends OkHttp {

    public OkHttpGet(String url, Map<String, String> params) {
        super(url, params);
    }

    public OkHttpGet(String url, Map<String, String> params, Map<String, String> headers) {
        super(url, params, headers);
    }

    public OkHttpGet(String url, Map<String, String> params, Map<String, String> headers, String tag) {
        super(url, params, headers, tag);
    }

    @Override
    public Request buildRequest() {
        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("url can not be empty!");
        }
        url = appendParams(url, params);
        Request.Builder builder = new Request.Builder();
        appendHeaders(builder, headers);
        return builder.url(url).tag(tag).build();
    }

    @Override
    public RequestBody buildRequestBody() {
        return null;
    }

    private String appendParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }

        sb = sb.deleteCharAt(sb.length() - 1);
        Logger.d(GlobalParams.LOG_TAG_URL, sb.toString());
        return sb.toString();
    }
}

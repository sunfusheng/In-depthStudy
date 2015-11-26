package com.sun.study.module.okhttp.request;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.sun.study.constant.GlobalParams;

import java.util.Map;

/**
 * Created by sunfusheng on 15/11/19.
 */
public class OkHttpGet extends OkHttp {

    protected OkHttpGet(String url, Map<String, String> params) {
        super(url, params);
    }

    protected OkHttpGet(String url, String tag, Map<String, String> params, Map<String, String> headers) {
        super(url, params, tag, headers);
    }

    @Override
    protected Request buildRequest() {
        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("url can not be empty!");
        }
        url = appendParams(url, params);
        Request.Builder builder = new Request.Builder();
        appendHeaders(builder, headers);
        builder.url(url).tag(tag);
        return builder.build();
    }

    @Override
    protected RequestBody buildRequestBody() {
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

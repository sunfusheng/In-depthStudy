package com.sun.study.module.okhttp.request;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.sun.study.module.okhttp.callback.OkHttpCallBack;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by sunfusheng on 15/12/8.
 */
public class OkHttpClientManager {

    public static int OKHTTP_REQUEST_TIMEOUT = 60; //60s超时
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;

    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setConnectTimeout(OkHttpClientManager.OKHTTP_REQUEST_TIMEOUT, TimeUnit.SECONDS);
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    public Handler getHandler() {
        return mHandler;
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }


    public void execute(final Request request, OkHttpCallBack callback) {
        if (callback == null) callback = OkHttpCallBack.DEFAULT_RESULT_CALLBACK;
        final OkHttpCallBack resCallBack = callback;
        resCallBack.onBefore(request);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                sendFailResultCallback(request, e, resCallBack);
            }

            @Override
            public void onResponse(final Response response) {
                if (response.code() >= 400 && response.code() <= 599) {
                    try {
                        sendFailResultCallback(request, new RuntimeException(response.body().string()), resCallBack);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }

                try {
                    sendSuccessResultCallback(response.body().string(), resCallBack);
                } catch (IOException e) {
                    sendFailResultCallback(response.request(), e, resCallBack);
                }
            }
        });
    }

    public String execute(Request request) throws IOException {
        Call call = mOkHttpClient.newCall(request);
        Response execute = call.execute();
        return execute.body().string();
    }


    public void sendFailResultCallback(final Request request, final Exception e, final OkHttpCallBack callback) {
        if (callback == null) return;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(request, e);
                callback.onAfter();
            }
        });
    }

    public void sendSuccessResultCallback(final Object obj, final OkHttpCallBack callback) {
        if (callback == null) return;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(obj);
                callback.onAfter();
            }
        });
    }


    public void cancelTag(Object tag) {
        mOkHttpClient.cancel(tag);
    }
}

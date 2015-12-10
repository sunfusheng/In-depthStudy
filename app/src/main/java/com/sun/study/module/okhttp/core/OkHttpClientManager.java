package com.sun.study.module.okhttp.core;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.sun.study.module.okhttp.callback.OkHttpCallBack;

import java.io.IOException;

/**
 * Created by sunfusheng on 15/12/8.
 */
public class OkHttpClientManager {

    private static OkHttpClientManager mOkHttpClientManager;
    private OkHttpClient mOkHttpClient;
    private Handler mHandler;

    private OkHttpClientManager() {
        mOkHttpClient = OkHttp.getClient();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpClientManager getInstance() {
        if (mOkHttpClientManager == null) {
            synchronized (OkHttpClientManager.class) {
                if (mOkHttpClientManager == null) {
                    mOkHttpClientManager = new OkHttpClientManager();
                }
            }
        }
        return mOkHttpClientManager;
    }

    public Handler getHandler() {
        return mHandler;
    }

    public void execute(final Request request, OkHttpCallBack callback) {
        if (callback == null) callback = OkHttpCallBack.DEFAULT_CALLBACK;
        final OkHttpCallBack resCallBack = callback;
        resCallBack.onBefore(request);
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                sendFailResultCallback(request, e, resCallBack);
            }

            @Override
            public void onResponse(final Response response) {
                try {
                    if (response.isSuccessful()) {
                        sendSuccessResultCallback(response.body().string(), resCallBack);
                    } else {
                        sendFailResultCallback(request, new RuntimeException(response.body().string()), resCallBack);
                    }
                } catch (IOException e) {
                    sendFailResultCallback(response.request(), e, resCallBack);
                }
            }
        });
    }

    public String execute(Request request) throws IOException {
        Response execute = mOkHttpClient.newCall(request).execute();
        return execute.body().string();
    }

    public void sendFailResultCallback(final Request request, final Exception e, final OkHttpCallBack callback) {
        if (callback == null) return ;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(request, e);
                callback.onAfter();
            }
        });
    }

    public void sendSuccessResultCallback(final String response, final OkHttpCallBack callback) {
        if (callback == null) return ;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response);
                callback.onAfter();
            }
        });
    }

}

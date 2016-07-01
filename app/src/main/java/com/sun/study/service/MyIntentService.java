package com.sun.study.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.sun.study.ui.fragment.IntentServiceFragment;

/**
 * Created by sunfusheng on 16/6/30.
 */
public class MyIntentService extends IntentService {

    private boolean isRunning = true;
    private int count = 0;
    private LocalBroadcastManager mLocalBroadcastManager;

    public MyIntentService() {
        super("MyIntentService");
        Log.d("--->", "MyIntentService()");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("--->", "onCreate()");
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        sendServiceStatus("服务启动");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("--->", "onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d("--->", "onStart()");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("--->", "onHandleIntent()");
        try {
            sendThreadStatus("线程启动", count);
            Thread.sleep(1_000);
            sendServiceStatus("服务运行中...");

            isRunning = true;
            count = 0;
            while (isRunning) {
                count++;
                Thread.sleep(50);
                sendThreadStatus("线程运行中...", count);
                if (count >= 100) {
                    isRunning = false;
                }
            }
            sendThreadStatus("线程结束", count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("--->", "onDestroy()");
        sendServiceStatus("服务结束");
    }

    // 发送服务状态信息
    private void sendServiceStatus(String status) {
        Intent intent = new Intent(IntentServiceFragment.ACTION_TYPE_SERVICE);
        intent.putExtra("status", status);
        mLocalBroadcastManager.sendBroadcast(intent);
    }

    // 发送线程状态信息
    private void sendThreadStatus(String status, int progress) {
        Intent intent = new Intent(IntentServiceFragment.ACTION_TYPE_THREAD);
        intent.putExtra("status", status);
        intent.putExtra("progress", progress);
        mLocalBroadcastManager.sendBroadcast(intent);
    }
}

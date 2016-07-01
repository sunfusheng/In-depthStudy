package com.sun.study.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

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
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        sendServiceStatus("服务启动");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            sendThreadStatus("线程启动", count);
            Thread.sleep(1_000);
            sendServiceStatus("服务运行中...");

            isRunning = true;
            count = 0;
            while (isRunning) {
                count++;
                if (count >= 100) {
                    isRunning = false;
                }
                Thread.sleep(50);
                sendThreadStatus("线程运行中...", count);
            }
            sendThreadStatus("线程结束", count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
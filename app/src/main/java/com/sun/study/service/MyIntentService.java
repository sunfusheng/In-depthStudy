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
            isRunning = true;
            int count = 0;
            sendLocalBroadcast("准备运行", count);
            Thread.sleep(2_000);


            while (isRunning) {
                count++;
                Thread.sleep(200);
                sendLocalBroadcast("运行中...", count);
                if (count >= 100) {
                    isRunning = false;
                }
            }
            sendLocalBroadcast("运行结束", 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("--->", "onDestroy()");
    }

    private void sendLocalBroadcast(String status, int progress) {
        Intent intent = new Intent(IntentServiceFragment.ACTION_INTENTSERVICE);
        intent.putExtra("status", status);
        intent.putExtra("progress", progress);
        mLocalBroadcastManager.sendBroadcast(intent);
    }
}

package com.sun.study.framework.exception;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.sun.study.ui.activity.CrashActivity;

import java.lang.Thread.UncaughtExceptionHandler;

public class CrashHandler implements UncaughtExceptionHandler {

    private static CrashHandler INSTANCE = new CrashHandler();  
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;
  
    private CrashHandler() {}
  
    public static CrashHandler getInstance() {  
        return INSTANCE;  
    }  
  
    public void init(Context ctx) {
        mContext = ctx;  
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }  
  
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
    	if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }
    
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
        if (mContext == null) {
            return false;
        }

        ex.printStackTrace();
        
		StringBuffer sb = new StringBuffer();
        sb.append("基本信息: \n");
        sb.append("APP Version: " + getVersionInfo() + "\n");
        sb.append("API Level: " + Build.VERSION.SDK_INT + "\n");
        sb.append("Android: " + Build.VERSION.RELEASE + " (" + android.os.Build.MODEL + ")\n\n");

        sb.append("异常信息: \n");
        sb.append("Exception: " + ex.getMessage() + "\n\n");

        sb.append("堆栈信息: \n");
		StackTraceElement[] elements = ex.getStackTrace();
		for (int i = 0; i < elements.length; i++) {
            sb.append(elements[i].toString() + "\n");
		}

        Intent intent = new Intent(mContext, CrashActivity.class);
		intent.putExtra("exception", sb.toString());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(intent);

		android.os.Process.killProcess(android.os.Process.myPid());
        return true;
    }

    /**
     * 获取手机的版本信息
     */
    private String getVersionInfo(){
        try {
            PackageManager pm = mContext.getPackageManager();
            PackageInfo info =pm.getPackageInfo(mContext.getPackageName(), 0);
            return  info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "版本号未知";
        }
    }

}

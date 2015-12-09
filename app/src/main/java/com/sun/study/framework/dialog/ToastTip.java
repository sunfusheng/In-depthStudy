package com.sun.study.framework.dialog;

import android.widget.Toast;

import com.sun.study.MainApplication;

/**
 * Created by sunfusheng on 15/8/7.
 */
public class ToastTip {

    private static Toast mToast;

    public static void show(String message) {
        if (mToast != null) {
            mToast.cancel();
        }
        int duration;
        if (message.length() > 10) {
            duration = Toast.LENGTH_LONG; //如果字符串比较长，那么显示的时间也长一些。
        } else {
            duration = Toast.LENGTH_SHORT;
        }
        mToast = Toast.makeText(MainApplication.getInstance(), message, duration);
        mToast.show();
    }
}

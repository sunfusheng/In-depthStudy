package com.sun.study.ui.fragment;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.sun.study.constant.GlobalParams;
import com.sun.study.framework.base.BaseAsyncFragment;
import com.sun.study.framework.base.BaseControl;

/**
 * Created by sunfusheng on 15/11/18.
 */
public class BaseFragment<T extends BaseControl> extends BaseAsyncFragment<T> {

    private Toast mToast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i(GlobalParams.LOG_TAG_FRAGMENT, "(" + getClass().getSimpleName() + ".java:1)");
    }

    protected void toastTip(String message, boolean isCenter) {
        if (mToast != null) {
            mToast.cancel();
        }
        int duration;
        if (message.length() > 10) {
            duration = Toast.LENGTH_LONG; //如果字符串比较长，那么显示的时间也长一些。
        } else {
            duration = Toast.LENGTH_SHORT;
        }
        mToast = Toast.makeText(getActivity(), message, duration);
        if (isCenter) {
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
        mToast.show();
    }

    protected void toastTip(String message) {
        toastTip(message, true);
    }

}

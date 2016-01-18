package com.sun.study.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.sun.study.constant.GlobalParams;
import com.sun.study.framework.base.BaseAsyncFragment;
import com.sun.study.framework.base.BaseControl;

/**
 * Created by sunfusheng on 15/11/18.
 */
public class BaseFragment<T extends BaseControl> extends BaseAsyncFragment<T> implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i(GlobalParams.LOG_TAG_FRAGMENT, "(" + getClass().getSimpleName() + ".java:1)");
    }

    @Override
    public void onClick(View v) {}

}

package com.sun.study.framework.base;

import android.content.Context;
import android.os.Bundle;

import com.sun.study.adapter.BaseListAdapter;
import com.sun.study.framework.proxy.MessageProxy;
import com.sun.study.framework.proxy.ModelMap;
import com.sun.study.framework.proxy.common.IRefreshBack;
import com.sun.study.framework.proxy.helper.AdapterHelper;

public abstract class BaseAsyncListAdapter<T extends BaseControl, E> extends BaseListAdapter<E> implements IRefreshBack {

    protected T mControl;
    protected MessageProxy messageProxy;
    protected ModelMap mModel;
    private AdapterHelper mHelper;

    public BaseAsyncListAdapter(Context context) {
        super(context);
        mHelper = new AdapterHelper<T, BaseAsyncListAdapter>(this);
        mHelper.onCreate();
        initParams();
    }

    public void initParams() {
        mModel = mHelper.getModelMap();
        messageProxy = mHelper.getMessageProxy();
        mControl = (T) mHelper.getControl();
    }

    public void onResume() {
        mHelper.onResume();
    }

    public void onDestroy() {
        mHelper.onDestroy();
        mContext = null;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public void onRefresh(int requestCode, Bundle bundle) {
    }

}

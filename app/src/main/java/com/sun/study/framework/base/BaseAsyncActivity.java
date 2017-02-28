package com.sun.study.framework.base;

import android.os.Bundle;
import android.view.MenuItem;

import com.sun.study.framework.proxy.MessageProxy;
import com.sun.study.framework.proxy.ModelMap;
import com.sun.study.framework.proxy.common.IRefreshBack;
import com.sun.study.framework.proxy.helper.ActivityHelper;
import com.sunfusheng.multitheme.app.SkinCompatActivity;

import de.devland.esperandro.Esperandro;

public class BaseAsyncActivity<T extends BaseControl> extends SkinCompatActivity implements IRefreshBack {

    protected T mControl;
    protected MessageProxy messageProxy;
    protected ModelMap mModel;
    private ActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new ActivityHelper<T, BaseAsyncActivity>(this);
        mHelper.onCreate();
        initVar();
    }

    public void initVar() {
        mModel = mHelper.getModelMap();
        messageProxy = mHelper.getMessageProxy();
        mControl = (T) mHelper.getControl();
    }

    @Override
    protected void onStart() {
        mHelper.onStart();
        super.onStart();
    }

    @Override
    protected void onResume() {
        mHelper.onResume();
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mHelper.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHelper.onPause();
    }

    @Override
    protected void onStop() {
        mHelper.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mHelper.onDestroy();
        super.onDestroy();
    }

    protected boolean isPaused() {
        return mHelper.isPause();
    }

    protected <P> P getSharedPreferences(Class<P> spClass) {
        return Esperandro.getPreferences(spClass, this);
    }

    @Override
    public void onRefresh(int requestCode, Bundle bundle) {

    }

}

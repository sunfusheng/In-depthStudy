package com.sun.study.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.sun.study.R;
import com.sun.study.constant.GlobalParams;
import com.sun.study.framework.base.BaseAsyncActivity;
import com.sun.study.framework.base.BaseControl;
import com.sun.study.framework.dialog.TipDialog;
import com.sun.study.framework.sharedpreferences.LocationSharedPreferences;
import com.sun.study.framework.sharedpreferences.SettingsSharedPreferences;

public class BaseActivity<T extends BaseControl> extends BaseAsyncActivity<T> {

    private Toast mToast;
    protected TipDialog mTipDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.i(GlobalParams.LOG_TAG_ACTIVITY, "(" + getClass().getSimpleName() + ".java:1)");

        initData();
        initSystemBarTint();
    }

    private void initData() {
        mTipDialog = new TipDialog(this);
    }

    public void initSystemBarTint() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus();
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(getColorPrimary());
        }
    }

    private void setTranslucentStatus() {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        winParams.flags |= bits;
        win.setAttributes(winParams);
    }

    public int getColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    public int getDarkColorPrimary() {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }

    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, int resTitle) {
        initToolBar(toolbar, homeAsUpEnabled, getString(resTitle));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mToast = null;
        mTipDialog = null;
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
        mToast = Toast.makeText(this, message, duration);
        if (isCenter) {
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
        mToast.show();
    }

    protected void toastTip(String message) {
        toastTip(message, true);
    }

    public SettingsSharedPreferences getSettingsSharedPreferences() {
        return getSharedPreferences(SettingsSharedPreferences.class);
    }

    public LocationSharedPreferences getLocationSharedPreferences() {
        return getSharedPreferences(LocationSharedPreferences.class);
    }

}

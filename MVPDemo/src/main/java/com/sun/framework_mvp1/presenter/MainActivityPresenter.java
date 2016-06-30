package com.sun.framework_mvp1.presenter;

import android.view.View;

import com.sun.framework_mvp1.R;
import com.sun.framework_mvp1.view.MainDelegate;

public class MainActivityPresenter extends BaseActivityPresenter<MainDelegate> implements View.OnClickListener {

    @Override
    protected Class getDelegateClass() {
        return MainDelegate.class;
    }

    @Override
    protected void initListener() {
        super.initListener();
        viewDelegate.setOnClickListener(this, R.id.tv_activity_delegate, R.id.tv_fragment_delegate,
                R.id.tv_activity_data_binder, R.id.tv_fragment_data_binder, R.id.tv_activity_toolbar_delegate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_activity_delegate:
                viewDelegate.toast("tv_activity_delegate");
                break;
            case R.id.tv_fragment_delegate:

                break;
            case R.id.tv_activity_data_binder:

                break;
            case R.id.tv_fragment_data_binder:

                break;
            case R.id.tv_activity_toolbar_delegate:

                break;
        }
    }
}

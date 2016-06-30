package com.sun.framework_mvp1.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

import com.sun.framework_mvp1.view.i.IBaseDelegate;

/**
 * Presenter base class for Activity
 * Presenter层的实现基类
 *
 * @param <T> View delegate class type
 * @author kymjs (http://www.kymjs.com/) on 10/23/15.
 */
public abstract class BaseActivityPresenter<T extends IBaseDelegate> extends AppCompatActivity {

    protected T viewDelegate;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDelegate();
        viewDelegate.create(getLayoutInflater(), null, savedInstanceState);

        View view = viewDelegate.getContentView();
        mContext = view.getContext();
        setContentView(view);
        initToolbar();
        viewDelegate.initView();
        initListener();
    }

    private void initDelegate() {
        try {
            viewDelegate = getDelegateClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("create IDelegate error");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("create IDelegate error");
        }
    }

    protected void initListener() {
    }

    protected void initToolbar() {
        Toolbar toolbar = viewDelegate.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (viewDelegate.getOptionsMenuId() != 0) {
            getMenuInflater().inflate(viewDelegate.getOptionsMenuId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewDelegate = null;
    }

    protected abstract Class<T> getDelegateClass();
}

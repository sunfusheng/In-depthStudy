package com.sun.framework_mvp1.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sun.framework_mvp1.view.i.IBaseDelegate;

/**
 * View delegate base class
 * 视图层代理的基类
 *
 * @author kymjs (http://www.kymjs.com/) on 10/23/15.
 */
public abstract class BaseDelegate implements IBaseDelegate {

    protected final SparseArray<View> mViews = new SparseArray<>();

    protected View contentView;

    public abstract int getContentViewId();

    @Override
    public void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(getContentViewId(), container, false);
    }

    @Override
    public View getContentView() {
        return contentView;
    }

    @Override
    public void initView() {
    }

    public <T extends View> T bindView(int id) {
        T view = (T) mViews.get(id);
        if (view == null) {
            view = (T) contentView.findViewById(id);
            mViews.put(id, view);
        }
        return view;
    }

    public <T extends View> T get(int id) {
        return (T) bindView(id);
    }

    public void setOnClickListener(View.OnClickListener listener, int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            get(id).setOnClickListener(listener);
        }
    }

    @Override
    public int getOptionsMenuId() {
        return 0;
    }

    public Toolbar getToolbar() {
        return null;
    }

    public void toast(CharSequence msg) {
        Toast.makeText(contentView.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}

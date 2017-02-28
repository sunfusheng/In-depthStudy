package com.sunfusheng.multitheme.design.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.sunfusheng.multitheme.app.SkinLayoutInflater;
import com.sunfusheng.multitheme.design.widget.SkinCompatAppBarLayout;
import com.sunfusheng.multitheme.design.widget.SkinCompatNavigationView;
import com.sunfusheng.multitheme.design.widget.SkinCompatTabLayout;


public class SkinMaterialViewInflater implements SkinLayoutInflater {
    @Override
    public View createView(@NonNull Context context, final String name, @NonNull AttributeSet attrs) {
        View view = null;
        switch (name) {
            case "android.support.design.widget.AppBarLayout":
                view = new SkinCompatAppBarLayout(context, attrs);
                break;
            case "android.support.design.widget.TabLayout":
                view = new SkinCompatTabLayout(context, attrs);
                break;
            case "android.support.design.widget.NavigationView":
                view = new SkinCompatNavigationView(context, attrs);
                break;
        }
        return view;
    }
}

package com.sunfusheng.multitheme.design.widget;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.util.AttributeSet;

import com.sunfusheng.multitheme.widget.SkinCompatBackgroundHelper;
import com.sunfusheng.multitheme.widget.SkinCompatSupportable;


public class SkinCompatAppBarLayout extends AppBarLayout implements SkinCompatSupportable {
    private final SkinCompatBackgroundHelper mBackgroundTintHelper;

    public SkinCompatAppBarLayout(Context context) {
        this(context, null);
    }

    public SkinCompatAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        mBackgroundTintHelper.loadFromAttributes(attrs, 0);
    }

    @Override
    public void applySkin() {
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.applySkin();
        }
    }
}

package com.sunfusheng.multitheme.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;


public class SkinCompatFrameLayout extends FrameLayout implements SkinCompatSupportable {
    private final SkinCompatBackgroundHelper mBackgroundTintHelper;

    public SkinCompatFrameLayout(Context context) {
        this(context, null);
    }

    public SkinCompatFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkinCompatFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBackgroundTintHelper = new SkinCompatBackgroundHelper(this);
        mBackgroundTintHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    @Override
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.applySkin();
        }
    }

    @Override
    public void applySkin() {
        if (mBackgroundTintHelper != null) {
            mBackgroundTintHelper.applySkin();
        }
    }
}

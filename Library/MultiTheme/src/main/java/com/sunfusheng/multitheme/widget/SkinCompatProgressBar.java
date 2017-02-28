package com.sunfusheng.multitheme.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class SkinCompatProgressBar extends ProgressBar implements SkinCompatSupportable {
    private final SkinCompatProgressBarHelper mSkinCompatProgressBarHelper;

    public SkinCompatProgressBar(Context context) {
        this(context, null);
    }

    public SkinCompatProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.progressBarStyle);
    }

    public SkinCompatProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mSkinCompatProgressBarHelper = new SkinCompatProgressBarHelper(this);
        mSkinCompatProgressBarHelper.loadFromAttributes(attrs, defStyleAttr);
    }

    @Override
    public void applySkin() {
        if (mSkinCompatProgressBarHelper != null) {
            mSkinCompatProgressBarHelper.applySkin();
        }
    }
}

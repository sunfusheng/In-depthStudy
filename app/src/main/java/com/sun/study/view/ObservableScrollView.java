package com.sun.study.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by sunfusheng on 16/8/12.
 */
public class ObservableScrollView extends ScrollView {

    private OnScrollViewListener onScrollViewListener = null;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnScrollViewListener(OnScrollViewListener onScrollViewListener) {
        this.onScrollViewListener = onScrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (onScrollViewListener != null) {
            onScrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }

    public interface OnScrollViewListener {
        void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
    }

}

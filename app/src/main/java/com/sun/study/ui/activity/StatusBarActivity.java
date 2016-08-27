package com.sun.study.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.TextView;

import com.sun.study.R;
import com.sun.study.util.ColorUtil;
import com.sun.study.util.DisplayUtil;
import com.sun.study.util.statusbar.StatusBarUtil;
import com.sun.study.view.ObservableScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/8/11.
 */
public class StatusBarActivity extends AppCompatActivity {

    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.sv_root)
    ObservableScrollView svRoot;

    private Activity mActivity;
    private Context mContext;
    private int statusBarHeight = 0;
    private int titleBarHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        mContext = this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            statusBarHeight = StatusBarUtil.getStatusBarHeight(this);
            Log.d("--->", "statusBarHeight: " + statusBarHeight);
        }

        setContentView(R.layout.activity_status_bar);
        ButterKnife.bind(this);

        tvTitle.setPadding(0, (statusBarHeight + 10), 0, (statusBarHeight/2 - 10));
        tvTitle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tvTitle.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                titleBarHeight = tvTitle.getHeight();
                Log.d("--->", "titleBarHeight: " + titleBarHeight);

                svRoot.setOnScrollViewListener(new ObservableScrollView.OnScrollViewListener() {
                    @Override
                    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                        float fraction = (y  * 1.0f) / (DisplayUtil.dip2px(mContext, 220)-titleBarHeight);
                        if (fraction < 0) fraction = 0f;
                        if (fraction > 1) fraction = 1f;

                        Log.d("--->", "fraction: "+fraction+" y: "+y);
                        tvTitle.setBackgroundColor(ColorUtil.getNewColorByStartEndColor(StatusBarActivity.this, fraction, R.color.transparent, R.color.red));
                        tvTitle.setTextColor(ColorUtil.getNewColorByStartEndColor(StatusBarActivity.this, fraction, R.color.red, R.color.white));
                    }
                });
            }
        });
    }

}

package com.sun.study.ui.activity.bezier;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sun.study.R;
import com.sun.study.control.NavigateManager;
import com.sun.study.ui.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 2016/11/10.
 */

public class BezierActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bezier);
        ButterKnife.bind(this);

        initToolBar(toolbar, true, "贝赛尔动画");
    }

    // 二阶贝赛尔曲线
    public void quadraticBezierEvent(View v) {
        NavigateManager.gotoSpecifiedActivity(this, QuadraticBezierActivity.class);
    }

    // 三阶贝赛尔曲线
    public void cubicBezierEvent(View v) {
        NavigateManager.gotoSpecifiedActivity(this, CubicBezierActivity.class);
    }
}

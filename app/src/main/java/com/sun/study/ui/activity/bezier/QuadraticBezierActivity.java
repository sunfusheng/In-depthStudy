package com.sun.study.ui.activity.bezier;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sun.study.R;
import com.sun.study.ui.activity.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 2016/11/10.
 */

public class QuadraticBezierActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quadratic_bezier);
        ButterKnife.bind(this);

        initToolBar(toolbar, true, "二阶贝赛尔曲线");
    }
}

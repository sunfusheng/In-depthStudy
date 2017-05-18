package com.sun.study.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sun.study.R;
import com.sun.study.util.DisplayUtil;
import com.sun.study.view.CircleTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 17/5/18.
 */
public class CircleTextViewActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ctv4)
    CircleTextView ctv4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_text_view);
        ButterKnife.bind(this);

        initToolBar(toolbar, true, "CircleTextView");

        ctv4.setCircleWidth(DisplayUtil.dip2px(this, 2));
        ctv4.setCircleRadius(DisplayUtil.dip2px(this, 25));
        ctv4.setCircleColor(getResources().getColor(R.color.green));
        ctv4.setTextColor(getResources().getColor(R.color.green));
        ctv4.setText("4");
        ctv4.setTextSize(DisplayUtil.dip2px(this, 20));
    }

}

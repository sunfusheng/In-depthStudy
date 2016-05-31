package com.sun.study.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sun.study.R;
import com.sun.study.view.MarqueeView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/5/30.
 */
public class MarqueeViewActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.marqueeView1)
    MarqueeView marqueeView1;
    @Bind(R.id.marqueeView2)
    MarqueeView marqueeView2;
    @Bind(R.id.marqueeView3)
    MarqueeView marqueeView3;
    @Bind(R.id.marqueeView4)
    MarqueeView marqueeView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marquee_view);
        ButterKnife.bind(this);

        initToolBar(toolbar, true, "MarqueeView");

        marqueeView1.startWithText(getString(R.string.vs_res));
        marqueeView2.startWithText(getString(R.string.vs_res));
        marqueeView3.startWithText(getString(R.string.vs_res));
        marqueeView4.startWithText("心中有阳光，脚底有力量！");
    }

}

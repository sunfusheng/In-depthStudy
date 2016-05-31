package com.sun.study.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.sun.study.R;
import com.sun.study.view.VerticalMarqueeView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/5/30.
 */
public class VerticalMarqueeViewActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.iv_loudspeaker1)
    ImageView ivLoudspeaker1;
    @Bind(R.id.marqueeView1)
    VerticalMarqueeView marqueeView1;
    @Bind(R.id.iv_loudspeaker2)
    ImageView ivLoudspeaker2;
    @Bind(R.id.marqueeView2)
    VerticalMarqueeView marqueeView2;
    @Bind(R.id.iv_loudspeaker3)
    ImageView ivLoudspeaker3;
    @Bind(R.id.marqueeView3)
    VerticalMarqueeView marqueeView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_marquee_view);
        ButterKnife.bind(this);

        initToolBar(toolbar, true, "VerticalMarqueeView");

        marqueeView1.startWithText(getString(R.string.vs_res));
        marqueeView2.startWithText(getString(R.string.vs_res));
        marqueeView3.startWithText(getString(R.string.vs_res));
    }

}

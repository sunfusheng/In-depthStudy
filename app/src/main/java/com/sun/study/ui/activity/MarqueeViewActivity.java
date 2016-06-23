package com.sun.study.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sun.study.R;
import com.sun.study.view.MarqueeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/5/31.
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
    @Bind(R.id.marqueeView)
    MarqueeView marqueeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marquee_view);
        ButterKnife.bind(this);

        initToolBar(toolbar, true, "MarqueeView");

        List<String> info = new ArrayList<>();
        info.add("1. 大家好，我是孙福生。");
        info.add("2. 欢迎大家关注我哦！");
        info.add("3. GitHub帐号：sfsheng0322");
        info.add("4. 新浪微博：孙福生微博");
        info.add("5. 个人博客：sunfusheng.com");
        info.add("6. 微信公众号：孙福生");
        marqueeView.startWithList(info);

        marqueeView1.startWithText(getString(R.string.vs_res));
        marqueeView2.startWithText(getString(R.string.vs_res));
        marqueeView3.startWithText(getString(R.string.vs_res));
        marqueeView4.startWithText("心中有阳光，脚底有力量！");
    }

}

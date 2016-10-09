package com.sun.study.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.android.widget.svg.AnimatedSvgView;
import com.sun.study.R;
import com.sun.study.control.NavigateManager;
import com.sun.study.util.AnimUtil;
import com.sun.study.view.SVG;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/10/9.
 */
public class SplashActivity extends BaseActivity {

    @Bind(R.id.svg_view)
    AnimatedSvgView svgView;
    @Bind(R.id.tv_title)
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        initView();
        initListener();
    }

    private void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SVG svg = SVG.values()[1];
                svgView.setGlyphStrings(svg.glyphs);
                svgView.setFillColors(svg.colors);
                svgView.setViewportSize(svg.width, svg.height);
                svgView.setTraceResidueColor(0x32000000);
                svgView.setTraceColors(svg.colors);
                svgView.rebuildGlyphData();
                svgView.start();

                AnimUtil.animAlphaScaleShowView(tvTitle, 3000);
            }
        }, 500);
    }

    private void initListener() {
        svgView.setOnStateChangeListener(new AnimatedSvgView.OnStateChangeListener() {
            @Override
            public void onStateChange(int state) {
                if (state == AnimatedSvgView.STATE_FINISHED) {
                    NavigateManager.gotoSpecifiedActivity(mContext, MainActivity.class);
                    finish();
                }
            }
        });
    }

}

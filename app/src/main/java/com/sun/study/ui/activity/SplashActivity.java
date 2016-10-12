package com.sun.study.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

import com.android.util.statusbar.StatusBarCompat;
import com.android.widget.svg.AnimatedSvgView;
import com.sun.study.R;
import com.sun.study.control.NavigateManager;
import com.sun.study.util.AnimUtil;
import com.sun.study.view.SVG;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.white), true);

        initView();
        initListener();
    }

    public void setLightStatusBar(Window window, boolean lightStatusBar) {
        Class<? extends Window> clazz = window.getClass();
        try {
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            int darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(window, lightStatusBar ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                setSvg(SVG.values()[1]);

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

    private void setSvg(SVG svg) {
        svgView.setGlyphStrings(svg.glyphs);
        svgView.setFillColors(svg.colors);
        svgView.setViewportSize(svg.width, svg.height);
        svgView.setTraceResidueColor(0x32000000);
        svgView.setTraceColors(svg.colors);
        svgView.rebuildGlyphData();
        svgView.start();
    }

}

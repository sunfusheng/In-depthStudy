package com.sun.study.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.android.util.statusbar.StatusBarCompat;
import com.larswerkman.holocolorpicker.ColorPicker;
import com.larswerkman.holocolorpicker.SVBar;
import com.sun.study.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/8/11.
 */
public class StatusBarActivity extends BaseActivity implements ColorPicker.OnColorChangedListener {

    @Bind(R.id.colorPicker)
    ColorPicker colorPicker;
    @Bind(R.id.svBar)
    SVBar svBar;
    @Bind(R.id.tv_color)
    TextView tvColor;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar);
        ButterKnife.bind(this);

        initToolBar(toolbar, true, "StatusBar");
        colorPicker.addSVBar(svBar);
        colorPicker.setOldCenterColor(getResources().getColor(R.color.colorPrimary));
        colorPicker.setOnColorChangedListener(this);
    }

    @Override
    public void onColorChanged(int color) {
        StatusBarCompat.setStatusBarColor(this, color);
        toolbar.setBackgroundColor(color);
    }
}

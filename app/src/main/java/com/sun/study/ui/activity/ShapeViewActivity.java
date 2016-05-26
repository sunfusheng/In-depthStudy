package com.sun.study.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sun.study.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/5/24.
 */
public class ShapeViewActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape_view);
        ButterKnife.bind(this);

        initToolBar(toolbar, true, "ShapeView");
    }

}

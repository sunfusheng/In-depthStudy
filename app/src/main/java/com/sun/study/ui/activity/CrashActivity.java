package com.sun.study.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.sun.study.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CrashActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_crash)
    TextView tvCrash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        ButterKnife.bind(this);

        initToolBar(toolbar, true, "崩溃信息");
        if (getIntent() != null && getIntent().getStringExtra("exception") != null) {
            tvCrash.setText(getIntent().getStringExtra("exception"));
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }
}

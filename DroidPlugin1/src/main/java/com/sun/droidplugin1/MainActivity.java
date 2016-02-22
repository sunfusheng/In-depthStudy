package com.sun.droidplugin1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.tv_info)
    TextView tvInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        if (getIntent() != null) {
            StringBuilder sb = new StringBuilder();
            if (getIntent().hasExtra(PluginParams.PLUGIN_EXTRA_STRING)) {
                sb.append(getIntent().getStringExtra(PluginParams.PLUGIN_EXTRA_STRING));
            }

            if (!TextUtils.isEmpty(sb)) {
                tvInfo.setText(sb);
            }
        }
    }
}

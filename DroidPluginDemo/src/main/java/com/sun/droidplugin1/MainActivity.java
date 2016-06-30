package com.sun.droidplugin1;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
        StringBuilder sb = new StringBuilder();

        if (getIntent() != null) {
            if (getIntent().hasExtra(PluginParams.PLUGIN_EXTRA_STRING)) {
                sb.append(getIntent().getStringExtra(PluginParams.PLUGIN_EXTRA_STRING));
            }
        }

        try {
            PackageManager pkgManager = getPackageManager();
            PackageInfo pkgInfo = pkgManager.getPackageInfo(getPackageName(), 0);
            ApplicationInfo appInfo = pkgInfo.applicationInfo;
            sb.append("\n插件自己读取的应用信息：\n");
            sb.append("应用名称：" + pkgManager.getApplicationLabel(appInfo) + "\n");
            sb.append("应用包名：" + pkgInfo.packageName + "\n");
            sb.append("当前版本：V" + pkgInfo.versionName + "\n");

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (!TextUtils.isEmpty(sb)) {
            tvInfo.setText(sb);
        }
    }
}

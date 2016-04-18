package com.sun.study.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sun.study.R;
import com.sun.study.model.annotation.DynamicProxyUtil;
import com.sun.study.model.annotation.RequestNetworkApi;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/4/18.
 */
public class AnnotationActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_info)
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        ButterKnife.bind(this);

        initToolBar(toolbar, true, "注解");
        tvInfo.setText("该页面的操作是模拟网络请求，通过注解关联网络请求的API。\n\n" +
                "请求API上的注解如下:\n" +
                "@RequestAnnotation(withDialog = true, withMessage = \"正在加载，请稍后...\")");
    }

    public void requestClick(View v) {
        try {
            DynamicProxyUtil proxyUtil = new DynamicProxyUtil(AnnotationActivity.this);
            proxyUtil.process(RequestNetworkApi.class, "apiTestFunc", "字符串参数一", "字符串参数二");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package com.sun.study.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sun.study.R;
import com.sun.study.view.ShareView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/7/29.
 */
public class ShareViewActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private ShareView mShareView; //分享视图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_view);
        ButterKnife.bind(this);

        initToolBar(toolbar, true, "ShareView");

        mShareView = new ShareView(this);
    }

    public void showClick(View v) {
        mShareView.show();
    }

}

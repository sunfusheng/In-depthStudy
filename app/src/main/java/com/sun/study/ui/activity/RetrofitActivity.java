package com.sun.study.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.sun.study.R;
import com.sun.study.framework.dialog.ToastTip;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 15/11/30.
 */
public class RetrofitActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_num)
    EditText etNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);

        initToolBar(toolbar, true, "Retrofit");
    }

    public void getPhoneNumPlace(View v) {
        String num = etNum.getText().toString().trim();
        if (TextUtils.isEmpty(num) || num.length() != 11) {
            ToastTip.show(this, "请输入有效的手机号码");
            return ;
        }
    }

}

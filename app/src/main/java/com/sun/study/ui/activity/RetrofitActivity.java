package com.sun.study.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sun.study.R;
import com.sun.study.constant.ConstantParams;
import com.sun.study.control.SingleControl;
import com.sun.study.framework.dialog.ToastTip;
import com.sun.study.model.PhoneEntity;
import com.sun.study.module.retrofit.RetrofitFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

/**
 * Created by sunfusheng on 15/11/30.
 */
public class RetrofitActivity extends BaseActivity<SingleControl> {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_num)
    EditText etNum;
    @Bind(R.id.tv_result)
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);

        initToolBar(toolbar, true, "Retrofit");
    }

    public void getPhoneNumPlace1(View v) {
        String num = etNum.getText().toString().trim();
        if (TextUtils.isEmpty(num) || num.length() != 11) {
            ToastTip.show(this, "请输入有效的手机号码");
            return;
        }
        Call<PhoneEntity> entity = RetrofitFactory.get().getPhoneNumPlace(num, ConstantParams.APIKEY_APISTORE);
        entity.enqueue(new Callback<PhoneEntity>() {
            @Override
            public void onResponse(retrofit.Response<PhoneEntity> response, Retrofit retrofit) {
                if (response != null) {
                    tvResult.setText(""+response.body().getRetData().toString());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                if (t != null) {
                    tvResult.setText(""+t.getMessage());
                }
            }
        });
    }

    public void getPhoneNumPlace2(View v) {
        String num = etNum.getText().toString().trim();
        if (TextUtils.isEmpty(num) || num.length() != 11) {
            ToastTip.show(this, "请输入有效的手机号码");
            return;
        }
        mControl.getPhoneNumPlace(num);
    }

    public void getPhoneNumPlaceCallBack() {
        PhoneEntity entity = mModel.get(1);
        tvResult.setText(entity.getRetData().toString());
    }


}

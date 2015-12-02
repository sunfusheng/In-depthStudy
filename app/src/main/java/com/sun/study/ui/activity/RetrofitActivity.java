package com.sun.study.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sun.study.R;
import com.sun.study.control.SingleControl;
import com.sun.study.framework.dialog.ToastTip;
import com.sun.study.model.PhoneEntity;
import com.sun.study.module.retrofit.RetrofitApi;
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

    private RetrofitApi mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);

        initToolBar(toolbar, true, "Retrofit");
        mApi = RetrofitFactory.get();
    }

    private void showResultInfo(PhoneEntity entity, long timeMillis) {
        if (entity != null && entity.getRetData() != null) {
            PhoneEntity.RetDataEntity retDataEntity = entity.getRetData();
            tvResult.setText("请求成功: "+timeMillis+"ms\n\n"+
                    "电话号码："+retDataEntity.getPhone()+"\n"+
                    "供应商："+retDataEntity.getSupplier()+"\n"+
                    "归属地："+retDataEntity.getProvince()+retDataEntity.getCity()+"\n"+
                    "号段："+retDataEntity.getSuit());
        }
    }

    public void getPhoneNumPlace1(View v) {
        String phone = etNum.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            ToastTip.show(this, "请输入有效的手机号码");
            return;
        }

        lastTime = System.currentTimeMillis();
        Call<PhoneEntity> call = mApi.getPhoneNumPlace(phone);
        call.enqueue(new Callback<PhoneEntity>() {
            @Override
            public void onResponse(retrofit.Response<PhoneEntity> response, Retrofit retrofit) {
                showResultInfo(response.body(), (System.currentTimeMillis() - lastTime));
            }

            @Override
            public void onFailure(Throwable t) {
                if (t != null) {
                    tvResult.setText("请求失败: \n\n"+t.getMessage());
                }
            }
        });
    }

    public void getPhoneNumPlace2(View v) {
        String phone = etNum.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            ToastTip.show(this, "请输入有效的手机号码");
            return;
        }

        lastTime = System.currentTimeMillis();
        mControl.getPhoneNumPlace(phone);
    }

    public void getPhoneNumPlace3(View v) {
        String phone = etNum.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            ToastTip.show(this, "请输入有效的手机号码");
            return;
        }

        lastTime = System.currentTimeMillis();
        mControl.getPhoneNumPlace3(phone);
    }

    public void getPhoneNumPlaceCallBack() {
        PhoneEntity entity = mModel.get(1);
        showResultInfo(entity, (System.currentTimeMillis() - lastTime));
    }

}

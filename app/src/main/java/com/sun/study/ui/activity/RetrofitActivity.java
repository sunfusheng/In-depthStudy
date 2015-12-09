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
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

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
        if (entity == null || entity.getRetData() == null) return ;
        showResultInfo(entity.getRetData(), timeMillis);
    }

    private void showResultInfo(PhoneEntity.RetDataEntity retDataEntity, long timeMillis) {
        if (retDataEntity == null) return ;
        tvResult.setText("请求成功: "+timeMillis+"ms\n\n"+
                "电话号码："+retDataEntity.getTelString()+"\n"+
                "供应商："+retDataEntity.getCarrier()+"\n"+
                "归属地："+retDataEntity.getProvince()+"\n");
    }

    private boolean checkPhoneAvailable() {
        String phone = etNum.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            ToastTip.show("请输入有效的手机号码");
            return false;
        }
        return true;
    }

    public void getPhoneNumPlace1(View v) {
        String tel = etNum.getText().toString().trim();
        if (!checkPhoneAvailable()) return ;
        lastTime = System.currentTimeMillis();

        Call<PhoneEntity> call = mApi.getPhoneNumPlace(tel);
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
        String tel = etNum.getText().toString().trim();
        if (!checkPhoneAvailable()) return ;
        lastTime = System.currentTimeMillis();

        mControl.getPhoneNumPlace(tel);
    }

    public void getPhoneNumPlace3(View v) {
        String phone = etNum.getText().toString().trim();
        if (!checkPhoneAvailable()) return ;
        lastTime = System.currentTimeMillis();

        mControl.getPhoneNumPlace3(phone);
    }

    public void getPhoneNumPlace4(View v) {
        String phone = etNum.getText().toString().trim();
        if (!checkPhoneAvailable()) return ;
        lastTime = System.currentTimeMillis();

        mApi.getPhoneNumPlaceByRxJava(phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<PhoneEntity, PhoneEntity.RetDataEntity>() {
                    @Override
                    public PhoneEntity.RetDataEntity call(PhoneEntity phoneEntity) {
                        if (phoneEntity != null) {
                            return phoneEntity.getRetData();
                        }
                        return null;
                    }
                })
                .subscribe(new Subscriber<PhoneEntity.RetDataEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PhoneEntity.RetDataEntity retDataEntity) {
                        showResultInfo(retDataEntity, (System.currentTimeMillis() - lastTime));
                    }
                });
    }

    public void getPhoneNumPlaceCallBack() {
        PhoneEntity entity = mModel.get(1);
        showResultInfo(entity, (System.currentTimeMillis() - lastTime));
    }

}

package com.sun.study.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.Request;
import com.sun.study.R;
import com.sun.study.constant.ConstantParams;
import com.sun.study.control.SingleControl;
import com.sun.study.framework.dialog.ToastTip;
import com.sun.study.model.CityDistrictEntity;
import com.sun.study.model.CityWeatherDataEntity;
import com.sun.study.model.CityWeatherEntity;
import com.sun.study.module.okhttp.UrlManager;
import com.sun.study.module.okhttp.callback.OkHttpCallBack;
import com.sun.study.module.okhttp.core.OkHttpProxy;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 15/11/19.
 */
public class OkHttpActivity extends BaseActivity<SingleControl> {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private String cityname = "北京";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        initToolBar(toolbar, true, "OkHttp");
    }

    public void getCityWeather(View v) {
        mControl.getCityWeather(cityname);
    }

    public void getCityWeatherSimple(View v) {
        mControl.getCityWeatherSimple(cityname);
    }

    public void getCityWeatherDataSimple(View v) {
        mControl.getCityWeatherDataSimple(cityname);
    }

    public void getWeatherCityDistrictList(View v) {
        mControl.getWeatherCityDistrictList(cityname);
    }

    public void getCityWeatherCallBack() {
        CityWeatherEntity entity = mModel.get(1);
        if (entity != null) {
            showInfoDialog(entity.getRetData());
        }
    }

    public void getCityWeatherDataSimpleCallBack() {
        CityWeatherDataEntity entity = mModel.get(1);
        showInfoDialog(entity);
    }
    
    private void showInfoDialog(CityWeatherDataEntity entity) {
        if (entity == null) return ;
        mTipDialog.show(cityname + "天气", "发布时间：" + entity.getDate() + " " + entity.getTime() + "\n" +
                "当前温度：" + entity.getTemp() + "\n" +
                "最高温度：" + entity.getH_tmp() + "\n" +
                "最低温度：" + entity.getL_tmp() + "\n" +
                "风力：" + entity.getWS());
    }

    public void getWeatherCityDistrictListCallBack() {
        List<CityDistrictEntity> list = mModel.getList(1);
        if (list != null && list.size() > 0) {
            int size = list.size();
            String[] listArr = new String[size];
            for (int i=0; i<size; i++) {
                CityDistrictEntity entity = list.get(i);
                listArr[i] = entity.getName_cn();
            }
            showLongList(listArr);
        }
    }

    private void showLongList(String[] listArr) {
        new MaterialDialog.Builder(this)
                .title(cityname + "区域列表")
                .items(listArr)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        mControl.getCityWeatherSimple(text.toString());
                    }
                })
                .show();
    }

    public void getCityWeatherDataEnqueue(View v) {
        OkHttpProxy.getInstance()
                .url(UrlManager.URL_CITY_WEATHER + "?cityname=" +cityname)
                .addHeader("apikey", ConstantParams.APISTORE_API_KEY)
                .get(new OkHttpCallBack() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int errNum = jsonObject.getInt("errNum");
                            String errMsg = jsonObject.getString("errMsg");
                            if (errNum == 0) {
                                CityWeatherEntity entity = JSON.parseObject(response, CityWeatherEntity.class);
                                if (entity != null) {
                                    showInfoDialog(entity.getRetData());
                                }
                            } else {
                                ToastTip.show(errMsg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Request request, Exception e) {

                    }
                });
    }

}

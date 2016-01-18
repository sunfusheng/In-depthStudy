package com.sun.study.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sun.study.R;
import com.sun.study.control.NavigateManager;
import com.sun.study.control.SingleControl;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 15/11/18.
 */
public class NetworkFragment extends BaseFragment<SingleControl> {

    @Bind(R.id.tv_okhttp)
    TextView tvOkhttp;
    @Bind(R.id.tv_xutils3)
    TextView tvXutils3;
    @Bind(R.id.tv_volley)
    TextView tvVolley;
    @Bind(R.id.tv_retrofit)
    TextView tvRetrofit;
    @Bind(R.id.tv_socket)
    TextView tvSocket;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_network, container, false);
        ButterKnife.bind(this, rootView);

        initListener();
        return rootView;
    }

    private void initListener() {
        tvOkhttp.setOnClickListener(this);
        tvXutils3.setOnClickListener(this);
        tvVolley.setOnClickListener(this);
        tvRetrofit.setOnClickListener(this);
        tvSocket.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_okhttp:
                NavigateManager.gotoOkHttpActivity(getActivity());
                break;
            case R.id.tv_retrofit:
                NavigateManager.gotoRetrofitActivity(getActivity());
                break;
            case R.id.tv_socket:
                mControl.getMyApps();
                break;
        }
    }

    public void getMyAppsCallBack() {

    }

}

package com.sun.study.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sun.study.R;
import com.sun.study.control.NavigateManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 15/11/27.
 */
public class RxJavaFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.tv_java1)
    TextView tvJava1;
    @Bind(R.id.tv_java2)
    TextView tvJava2;
    @Bind(R.id.tv_java3)
    TextView tvJava3;
    @Bind(R.id.tv_java4)
    TextView tvJava4;
    @Bind(R.id.tv_java5)
    TextView tvJava5;
    @Bind(R.id.tv_java6)
    TextView tvJava6;
    @Bind(R.id.tv_java7)
    TextView tvJava7;
    @Bind(R.id.tv_java8)
    TextView tvJava8;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rxjava, container, false);
        ButterKnife.bind(this, rootView);

        initListener();
        return rootView;
    }

    private void initListener() {
        tvJava1.setOnClickListener(this);
        tvJava2.setOnClickListener(this);
        tvJava3.setOnClickListener(this);
        tvJava4.setOnClickListener(this);
        tvJava5.setOnClickListener(this);
        tvJava6.setOnClickListener(this);
        tvJava7.setOnClickListener(this);
        tvJava8.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_java1:
                NavigateManager.gotoRxJava1Activity(getActivity());
                break;
            case R.id.tv_java2:
                NavigateManager.gotoRxJava2Activity(getActivity());
                break;
            case R.id.tv_java3:
                NavigateManager.gotoRxJava3Activity(getActivity());
                break;
            case R.id.tv_java4:
                NavigateManager.gotoRxJava4Activity(getActivity());
                break;
            case R.id.tv_java5:
                NavigateManager.gotoRxJava5Activity(getActivity());
                break;
            case R.id.tv_java6:
                NavigateManager.gotoRxJava6Activity(getActivity());
                break;
            case R.id.tv_java7:
                NavigateManager.gotoRxJava7Activity(getActivity());
                break;
            case R.id.tv_java8:
                NavigateManager.gotoRxJava8Activity(getActivity());
                break;
        }
    }

}

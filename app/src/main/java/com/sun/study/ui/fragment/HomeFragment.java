package com.sun.study.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sun.study.R;

import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 15/11/18.
 */
public class HomeFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);

        initView();
        initListener();
        return rootView;
    }

    private void initView() {

    }

    private void initListener() {

    }

}

package com.sun.study.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sun.study.R;
import com.sun.study.view.ChoiceView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 15/11/18.
 */
public class CustomViewFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.cv_choice1)
    ChoiceView cvChoice1;
    @Bind(R.id.cv_choice2)
    ChoiceView cvChoice2;
    @Bind(R.id.cv_choice3)
    ChoiceView cvChoice3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_customview, container, false);
        ButterKnife.bind(this, rootView);

        initView();
        initListener();
        return rootView;
    }

    private void initView() {
        cvChoice1.setSellOutViewVisibility(true);
        cvChoice2.setOnChoiceViewClickListener(new ChoiceView.OnChoiceViewClickListener() {
            @Override
            public void onChoiceViewCount(int count) {

            }
        });
        cvChoice3.setTomorrowOnlyViewVisibility(true);
    }

    private void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

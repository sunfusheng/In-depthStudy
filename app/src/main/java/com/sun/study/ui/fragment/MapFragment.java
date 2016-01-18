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
 * Created by sunfusheng on 15/12/11.
 */
public class MapFragment extends BaseFragment<SingleControl> {

    @Bind(R.id.tv_amap)
    TextView tvAmap;
    @Bind(R.id.tv_baidumap)
    TextView tvBaidumap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, rootView);

        initListener();
        return rootView;
    }

    private void initListener() {
        tvAmap.setOnClickListener(this);
        tvBaidumap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_amap:
                NavigateManager.gotoAmapActivity(getActivity());
                break;
            case R.id.tv_baidumap:

                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

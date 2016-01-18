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
 * Created by sunfusheng on 16/1/18.
 */
public class ImageFragment extends BaseFragment {

    @Bind(R.id.tv_Fresco)
    TextView tvFresco;
    @Bind(R.id.tv_ImageLoader)
    TextView tvImageLoader;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image, container, false);
        ButterKnife.bind(this, rootView);

        initListener();
        return rootView;
    }

    private void initListener() {
        tvFresco.setOnClickListener(this);
        tvImageLoader.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_Fresco:
                NavigateManager.gotoFrescoActivity(getActivity());
                break;
            case R.id.tv_ImageLoader:

                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

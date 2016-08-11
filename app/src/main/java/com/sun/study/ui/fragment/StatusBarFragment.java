package com.sun.study.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sun.study.R;
import com.sun.study.control.NavigateManager;
import com.sun.study.ui.activity.StatusBarActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/1/18.
 */
public class StatusBarFragment extends BaseFragment {

    @Bind(R.id.tv_status_bar)
    TextView tvStatusBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_status_bar, container, false);
        ButterKnife.bind(this, rootView);

        initListener();
        return rootView;
    }

    private void initListener() {
        tvStatusBar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_status_bar:
                NavigateManager.gotoSpecifiedActivity(getActivity(), StatusBarActivity.class);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

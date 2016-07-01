package com.sun.study.ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sun.study.R;
import com.sun.study.service.MyIntentService;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/6/30.
 */
public class IntentServiceFragment extends BaseFragment {

    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.tv_progress)
    TextView tvProgress;
    @Bind(R.id.tv_start)
    TextView tvStart;
    @Bind(R.id.tv_cancel)
    TextView tvCancel;
    @Bind(R.id.tv_service_status)
    TextView tvServiceStatus;
    @Bind(R.id.tv_thread_status)
    TextView tvThreadStatus;

    public final static String ACTION_TYPE_SERVICE = "action.type.service";
    public final static String ACTION_TYPE_THREAD = "action.type.thread";

    private LocalBroadcastManager mLocalBroadcastManager;
    private MyBroadcastReceiver mBroadcastReceiver;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        mBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_TYPE_SERVICE);
        intentFilter.addAction(ACTION_TYPE_THREAD);
        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_intent_service, container, false);
        ButterKnife.bind(this, rootView);

        initView();
        initListener();
        return rootView;
    }

    private void initView() {
        tvServiceStatus.setText("服务状态：未运行");
        tvThreadStatus.setText("线程状态：未运行");
        progressBar.setMax(100);
        progressBar.setProgress(0);
        tvProgress.setText(0 + "%");
    }

    private void initListener() {
        tvStart.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start:
                Intent startIntent = new Intent(getActivity(), MyIntentService.class);
                getActivity().startService(startIntent);
                break;
            case R.id.tv_cancel:
                Intent stopIntent = new Intent(getActivity(), MyIntentService.class);
                getActivity().stopService(stopIntent);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case ACTION_TYPE_SERVICE:
                    tvServiceStatus.setText("服务状态：" + intent.getStringExtra("status"));
                    break;
                case ACTION_TYPE_THREAD:
                    int progress = intent.getIntExtra("progress", 0);
                    tvThreadStatus.setText("线程状态：" + intent.getStringExtra("status"));
                    progressBar.setProgress(progress);
                    tvProgress.setText(progress + "%");
                    break;
            }
        }
    }
}

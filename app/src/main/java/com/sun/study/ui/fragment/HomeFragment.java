package com.sun.study.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sun.study.R;
import com.sun.study.control.NavigateManager;
import com.sun.study.ui.activity.AnnotationActivity;
import com.sun.study.ui.activity.MarqueeViewActivity;
import com.sun.study.ui.activity.MultiThreadActivity;
import com.sun.study.ui.activity.OkHttpActivity;
import com.sun.study.ui.activity.ReflectionActivity;
import com.sun.study.ui.activity.RetrofitActivity;
import com.sun.study.ui.activity.ShapeViewActivity;
import com.sun.study.ui.activity.ShareViewActivity;
import com.sun.study.ui.activity.StatusBarActivity;
import com.sun.study.ui.activity.ThreadActivity;
import com.sun.study.ui.activity.ThreadPoolActivity;
import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 15/11/18.
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.tv_retrofit)
    TextView tvRetrofit;
    @Bind(R.id.ll_okhttp)
    LinearLayout llOkhttp;
    @Bind(R.id.ll_multi_thread)
    LinearLayout llMultiThread;
    @Bind(R.id.tv_thread_pool)
    TextView tvThreadPool;
    @Bind(R.id.tv_thread)
    TextView tvThread;
    @Bind(R.id.tv_reflection)
    TextView tvReflection;
    @Bind(R.id.tv_annotation)
    TextView tvAnnotation;
    @Bind(R.id.ll_shape_view)
    LinearLayout llShapeView;
    @Bind(R.id.tv_MarqueeView)
    TextView tvMarqueeView;
    @Bind(R.id.tv_ShareView)
    TextView tvShareView;
    @Bind(R.id.marqueeView)
    MarqueeView marqueeView;
    @Bind(R.id.ll_status_bar)
    LinearLayout llStatusBar;

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
        List<String> info = new ArrayList<>();
        info.add("大家好，我是孙福生。");
        info.add("欢迎大家关注我哦！");
        info.add("GitHub帐号：sfsheng0322");
        info.add("新浪微博：孙福生微博");
        info.add("个人博客：sunfusheng.com");
        info.add("微信公众号：孙福生");
        marqueeView.startWithList(info);
    }

    private void initListener() {
        llOkhttp.setOnClickListener(this);
        tvRetrofit.setOnClickListener(this);
        llMultiThread.setOnClickListener(this);
        tvThread.setOnClickListener(this);
        tvThreadPool.setOnClickListener(this);
        tvReflection.setOnClickListener(this);
        tvAnnotation.setOnClickListener(this);
        llShapeView.setOnClickListener(this);
        tvMarqueeView.setOnClickListener(this);
        tvShareView.setOnClickListener(this);
        llStatusBar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_okhttp:
                NavigateManager.gotoSpecifiedActivity(getContext(), OkHttpActivity.class);
                break;
            case R.id.tv_retrofit:
                NavigateManager.gotoSpecifiedActivity(getContext(), RetrofitActivity.class);
                break;
            case R.id.ll_multi_thread:
                NavigateManager.gotoSpecifiedActivity(getContext(), MultiThreadActivity.class);
                break;
            case R.id.tv_thread:
                NavigateManager.gotoSpecifiedActivity(getContext(), ThreadActivity.class);
                break;
            case R.id.tv_thread_pool:
                NavigateManager.gotoSpecifiedActivity(getContext(), ThreadPoolActivity.class);
                break;
            case R.id.tv_reflection:
                NavigateManager.gotoSpecifiedActivity(getContext(), ReflectionActivity.class);
                break;
            case R.id.tv_annotation:
                NavigateManager.gotoSpecifiedActivity(getContext(), AnnotationActivity.class);
                break;
            case R.id.ll_shape_view:
                NavigateManager.gotoSpecifiedActivity(getContext(), ShapeViewActivity.class);
                break;
            case R.id.tv_MarqueeView:
                NavigateManager.gotoSpecifiedActivity(getContext(), MarqueeViewActivity.class);
                break;
            case R.id.tv_ShareView:
                NavigateManager.gotoSpecifiedActivity(getContext(), ShareViewActivity.class);
                break;
            case R.id.ll_status_bar:
                NavigateManager.gotoSpecifiedActivity(getContext(), StatusBarActivity.class);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

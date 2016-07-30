package com.sun.study.view;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.study.R;
import com.sun.study.util.NavigationBarUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 15/11/25.
 */
public class ShareView {

    @Bind(R.id.iv_share_friend)
    ImageView ivShareFriend;
    @Bind(R.id.iv_share_wx)
    ImageView ivShareWx;
    @Bind(R.id.iv_share_weibo)
    ImageView ivShareWeibo;
    @Bind(R.id.iv_share_qq)
    ImageView ivShareQq;
    @Bind(R.id.iv_share_qqzone)
    ImageView ivShareQqzone;
    @Bind(R.id.tv_share_cancel)
    TextView tvShareCancel;

    private Activity mActivity;
    private View fullMaskView; // 蒙层视图
    private View contentLayout; // 分享视图

    private int panelHeight = 0; // 分享视图高度
    private int navigationBarHeight = 0; // 导航栏高度

    public ShareView(Activity activity) {
        this.mActivity = activity;
        initShareView(activity);
    }

    private void initShareView(Activity activity) {
        fullMaskView = View.inflate(activity, R.layout.ui_view_full_mask_layout, null);
        contentLayout = LayoutInflater.from(activity).inflate(R.layout.ui_share, null);
        ButterKnife.bind(this, contentLayout);

        attachView();
        initListener();
    }

    // 将该View添加到根布局
    private void attachView() {
        ((ViewGroup) mActivity.getWindow().getDecorView()).addView(fullMaskView);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        ((ViewGroup) mActivity.getWindow().getDecorView()).addView(contentLayout, params);
        fullMaskView.setVisibility(View.GONE);
        contentLayout.setVisibility(View.GONE);

        if (NavigationBarUtil.hasNavigationBar(mActivity)) {
            navigationBarHeight = NavigationBarUtil.getNavigationBarHeight(mActivity);
        }
    }

    // 动画显示布局
    public void show() {
        fullMaskView.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.VISIBLE);
        contentLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                contentLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ViewGroup parent = (ViewGroup) contentLayout.getParent();
                panelHeight = parent.getHeight() - contentLayout.getTop();
                ObjectAnimator.ofFloat(contentLayout, "translationY", panelHeight, -navigationBarHeight).setDuration(200).start();
            }
        });
    }

    // 动画隐藏布局
    public void hide() {
        fullMaskView.setVisibility(View.GONE);
        ObjectAnimator.ofFloat(contentLayout, "translationY", -navigationBarHeight, panelHeight).setDuration(200).start();
    }

    private void initListener() {
        fullMaskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        tvShareCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
    }

}

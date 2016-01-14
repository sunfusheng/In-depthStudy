package com.sun.study.ui.fragment;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.sun.study.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 15/11/18.
 */
public class HomeFragment extends BaseFragment {

    @Bind(R.id.sdv_gif)
    SimpleDraweeView sdvGif;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);

        initView();
        initListener();
        return rootView;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        Uri uri = Uri.parse("res://"+getActivity().getPackageName()+"/"+R.drawable.gif_robot_walk);
//        Uri uri = Uri.parse("http://ww2.sinaimg.cn/large/dd412be4gw1esr6ijoebog208e0e1qv6.gif");
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .build();
        sdvGif.setController(controller);
    }

    private void initListener() {

    }

}

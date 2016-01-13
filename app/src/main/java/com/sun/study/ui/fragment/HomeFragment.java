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

    private static final String APATCH_PATH = "/out.apatch";

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

//        try {
//            String patchFileString = Environment.getExternalStorageDirectory().getAbsolutePath() + APATCH_PATH;
//            File file = new File(patchFileString);
//            if (file.exists()) {
//                MainApplication.getPatchManager().addPatch(patchFileString);
//                Logger.e("log-andfix", "addPatch()");
//                file.delete();
//            } else {
//                Logger.e("log-andfix", "Not addPatch()");
//            }
//        } catch (Exception e) {
//            Logger.e("log-andfix", "Exception: "+e.getMessage());
//            e.printStackTrace();
//        }
    }

    private void initListener() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

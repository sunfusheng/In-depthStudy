package com.sun.study.ui.activity;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.sun.study.R;
import com.sun.study.framework.dialog.ToastTip;
import com.sun.study.util.FrescoUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/1/18.
 */
public class FrescoActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.sdv11)
    SimpleDraweeView sdv11;
    @Bind(R.id.sdv12)
    SimpleDraweeView sdv12;
    @Bind(R.id.sdv13)
    SimpleDraweeView sdv13;
    @Bind(R.id.sdv14)
    SimpleDraweeView sdv14;
    @Bind(R.id.sdv21)
    SimpleDraweeView sdv21;
    @Bind(R.id.sdv22)
    SimpleDraweeView sdv22;
    @Bind(R.id.sdv23)
    SimpleDraweeView sdv23;
    @Bind(R.id.sdv24)
    SimpleDraweeView sdv24;
    @Bind(R.id.sdv31)
    SimpleDraweeView sdv31;
    @Bind(R.id.sdv32)
    SimpleDraweeView sdv32;
    @Bind(R.id.sdv33)
    SimpleDraweeView sdv33;
    @Bind(R.id.sdv34)
    SimpleDraweeView sdv34;

    private Animatable animatable;
    private boolean isRunGif = true;

    private String url1 = "http://img3.imgtn.bdimg.com/it/u=3336351749,2467482848&fm=23&gp=0.jpg";
    private String url2 = "http://www.qq1234.org/uploads/allimg/140811/21100051a-53.gif";
    private String url3 = "http://img2.imgtn.bdimg.com/it/u=1992251649,697132307&fm=21&gp=0.jpg";
    private String url4 = "http://img1.imgtn.bdimg.com/it/u=4027212837,1228313366&fm=23&gp=0.jpg";
    private String url5 = "/storage/emulated/0/MIUI/wallpaper/懒懒的猫咪_&_597e6551-ef62-4a77-9bc4-44a9e741a418.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);
        ButterKnife.bind(this);

        initToolBar(toolbar, true, "Fresco");
        initView();
        initListener();
    }

    private void initView() {
        sdv11.setImageURI(Uri.parse(url1));
        sdv12.setController(FrescoUtil.createController(url2));
        sdv13.setController(FrescoUtil.createController(url3));
        sdv14.setController(FrescoUtil.createController(url3));

        sdv21.setController(FrescoUtil.createController(url4));
        sdv22.setController(FrescoUtil.createController(url4));
        sdv23.setController(FrescoUtil.createController(url4));
        sdv24.setController(FrescoUtil.createController(url4));

        sdv31.setImageURI(FrescoUtil.createResUri(R.drawable.ic_launcher));
        sdv32.setImageURI(FrescoUtil.createAssetsUri("ic_launcher.png"));
        sdv33.setImageURI(FrescoUtil.createSDCardUri(url5));
        sdv34.setController(FrescoUtil.createController("res:///" + R.drawable.gif_robot_walk));
    }

    private void initListener() {
        sdv12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatable = sdv12.getController().getAnimatable();
                if (animatable != null) {
                    if (isRunGif) {
                        animatable.stop();
                        ToastTip.show("停止动画");
                    } else {
                        animatable.start();
                        ToastTip.show("播放动画");
                    }
                    isRunGif = !isRunGif;
                }
            }
        });
    }

}

package com.sun.study.util;

import android.net.Uri;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by sunfusheng on 16/1/19.
 */
public class FrescoUtil {

    // 创建DraweeController用来加载网络图片
    public static DraweeController createController(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setProgressiveRenderingEnabled(true)
                .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setUri(Uri.parse(url))
                .setAutoPlayAnimations(true)
                .build();
        return draweeController;
    }

    // 创建Uri用来加载Res下图片
    public static Uri createResUri(int resInt) {
        if (resInt <= 0) {
            return null;
        }
        return Uri.parse("res:///"+resInt);
    }

    // 创建Uri用来加载SD Card下图片
    public static Uri createSDCardUri(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return Uri.parse("file:///"+url);
    }

    // 创建Uri用来加载Assets下图片
    public static Uri createAssetsUri(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        return Uri.parse("asset:///"+name);
    }
}

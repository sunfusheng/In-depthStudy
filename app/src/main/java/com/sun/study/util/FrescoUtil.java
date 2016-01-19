package com.sun.study.util;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by sunfusheng on 16/1/19.
 */
public class FrescoUtil {

    // 创建Fresco的DraweeController
    public static DraweeController createController(String url) {
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
}

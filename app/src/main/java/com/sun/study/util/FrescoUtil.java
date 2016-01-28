package com.sun.study.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sunfusheng on 16/1/19.
 */
public class FrescoUtil {

    // 初始化Fresco
    public static void init(Context context) {
        Set<RequestListener> listeners = new HashSet<>();
        listeners.add(new RequestLoggingListener());
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(context)
                .setRequestListeners(listeners)
                .setBitmapsConfig(Bitmap.Config.ARGB_8888)
                .build();
        Fresco.initialize(context, config);
    }

    // 清除Fresco的内存
    public static void clearMemory() {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearMemoryCaches();
    }

    // 清除Fresco的缓存
    public static void clearCaches() {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearCaches();
    }

    // 创建DraweeController用来加载网络图片
    @Nullable
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

    // 创建DraweeController用来加载网络图片
    @Nullable
    public static DraweeController createController(String url, int width, int height) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setProgressiveRenderingEnabled(true)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setUri(Uri.parse(url))
                .setAutoPlayAnimations(true)
                .build();
        return draweeController;
    }

    // 创建Uri用来加载Res下图片
    @Nullable
    public static Uri createResUri(int resInt) {
        if (resInt <= 0) {
            return null;
        }
        return Uri.parse("res:///"+resInt);
    }

    // 创建Uri用来加载SD Card下图片
    @Nullable
    public static Uri createSDCardUri(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        return Uri.parse("file:///"+url);
    }

    // 创建Uri用来加载Assets下图片
    @Nullable
    public static Uri createAssetsUri(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        return Uri.parse("asset:///"+name);
    }
}

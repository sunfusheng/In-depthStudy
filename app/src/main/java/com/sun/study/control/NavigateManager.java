package com.sun.study.control;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.sun.study.ui.activity.OkHttpActivity;
import com.sun.study.ui.activity.RetrofitActivity;

import java.io.File;

public class NavigateManager {

    public static final int TAKE_PICTURE_REQUEST_CODE = 7;
    public static final int CHOOSE_PICTURE_REQUEST_CODE = 23;

    //拍照
    public static void gotoTakePicture(Activity activity, String takePicturePath) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), takePicturePath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, TAKE_PICTURE_REQUEST_CODE);
    }

    //从相册选择
    public static void gotoChoosePicture(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activity.startActivityForResult(intent, CHOOSE_PICTURE_REQUEST_CODE);
    }

    //使用系统浏览器打开
    public static void gotoSystemExplore(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        context.startActivity(intent);
    }

    public static void gotoOkHttpActivity(Context context) {
        Intent intent = new Intent(context, OkHttpActivity.class);
        context.startActivity(intent);
    }

    public static void gotoRetrofitActivity(Context context) {
        Intent intent = new Intent(context, RetrofitActivity.class);
        context.startActivity(intent);
    }
}

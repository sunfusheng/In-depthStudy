package com.sunfusheng.multitheme.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;


public class SkinFileUtils {
    public static String getSkinDir(Context context) {
        File skinDir = new File(getCacheDir(context), SkinConstants.SKIN_DEPLOY_PATH);
        if (!skinDir.exists()) {
            skinDir.mkdirs();
        }
        return skinDir.getAbsolutePath();
    }

    public static String getCacheDir(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File cacheDir = context.getExternalCacheDir();
            if (cacheDir != null && (cacheDir.exists() || cacheDir.mkdirs())) {
                return cacheDir.getAbsolutePath();
            }
        }

        return context.getCacheDir().getAbsolutePath();
    }

}

package com.sun.study.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;

import com.sun.study.model.AppInfoEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunfusheng on 16/2/19.
 */
public class AppUtil {

    // 从下载文件夹获取Apk列表
    public static List<AppInfoEntity> getApkListFromDownload(Context context) {
        File files = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        ArrayList<AppInfoEntity> apkList = new ArrayList<>();
        if (files == null || files.length() == 0) {
            return apkList;
        }

        for (File file : files.listFiles()) {
            if (file.exists() && file.getPath().toLowerCase().endsWith(".apk")) {
                AppInfoEntity entity = getAppInfoEntity(context, file.getPath());
                if (!TextUtils.isEmpty(entity.getAppName()) && entity.getAppIcon() != null) {
                    apkList.add(entity);
                }
            }
        }
        return apkList;
    }

    // 获取Apk信息
    public static AppInfoEntity getAppInfoEntity(Context context, String apkPath) {
        AppInfoEntity entity = new AppInfoEntity();
        PackageManager pkgManager = context.getPackageManager();
        PackageInfo pkgInfo = pkgManager.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        if (pkgInfo != null) {
            ApplicationInfo appInfo = pkgInfo.applicationInfo;

            // 必须设置, 否则无法获取应用名称
            appInfo.sourceDir = apkPath;
            appInfo.publicSourceDir = apkPath;

            entity.setAppName(pkgManager.getApplicationLabel(appInfo).toString() + ".apk");
            entity.setPackageName(pkgInfo.packageName);
            entity.setPackageInfo(pkgInfo);

            entity.setAppIcon(pkgManager.getApplicationIcon(appInfo));
            entity.setVersionCode(pkgInfo.versionCode);
            entity.setVersionName(pkgInfo.versionName);

            entity.setApkPath(apkPath);
            entity.setSrcPath(appInfo.publicSourceDir);
        }
        return entity;
    }
}

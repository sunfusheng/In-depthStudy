package com.sun.study.model;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

/**
 * Created by sunfusheng on 16/2/18.
 */
public class ApkInfoEntity {

    public Drawable icon; // 图标
    public String title; // 标题
    public String versionName; // 版本名称
    public int versionCode; // 版本号
    public String apkFile; // Apk路径
    public PackageInfo packageInfo; // 包信息

    public ApkInfoEntity(PackageManager pm, PackageInfo pi, String path) {

        // 必须设置, 否则title无法获取
        pi.applicationInfo.sourceDir = path;
        pi.applicationInfo.publicSourceDir = path;

        try {
            icon = pm.getApplicationIcon(pi.applicationInfo);
        } catch (Exception e) {
            icon = pm.getDefaultActivityIcon();
        }
        try {
            title = pm.getApplicationLabel(pi.applicationInfo).toString() + ".apk";
        } catch (Exception e) {
            title = pi.packageName + ".apk";
        }
        versionName = pi.versionName;
        versionCode = pi.versionCode;
        apkFile = path;
        packageInfo = pi;
    }

}

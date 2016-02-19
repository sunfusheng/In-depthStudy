package com.sun.study.model;

import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by sunfusheng on 16/2/18.
 */
public class AppInfoEntity implements Serializable {

    private String appName; // 应用名称
    private String packageName; // 包名
    private PackageInfo packageInfo; // 包信息
    private Drawable appIcon; // 图标
    private int versionCode; // 版本号
    private String versionName; // 版本名称
    private String apkPath; // 安装前Apk路径
    private String srcPath; // 安装后Apk路径

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getApkPath() {
        return apkPath;
    }

    public void setApkPath(String apkPath) {
        this.apkPath = apkPath;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    @Override
    public String toString() {
        return "AppInfoEntity{" +
                "appName='" + appName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", packageInfo=" + packageInfo +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", apkPath='" + apkPath + '\'' +
                ", srcPath='" + srcPath + '\'' +
                '}';
    }
}

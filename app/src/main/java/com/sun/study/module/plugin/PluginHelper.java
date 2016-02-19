package com.sun.study.module.plugin;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.morgoo.droidplugin.pm.PluginManager;
import com.sun.study.framework.dialog.SweetDialog;
import com.sun.study.framework.dialog.TipDialog;
import com.sun.study.model.AppInfoEntity;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by sunfusheng on 16/2/18.
 */
public class PluginHelper {

    public static final int DROID_CONNECT_FAIL = -1; // 插件服务未连接
    public static final int DROID_INSTALLED = 0; // 已安装
    public static final int DROID_INSTALLING = 1; // 正在安装...
    public static final int DROID_REQUEST_PERMISSION = 2; // 宿主包权限不足
    public static final int DROID_INSTALL_SUCCESS = 3; // 安装成功
    public static final int DROID_INSTALL_FAIL = 4; // 安装失败
    private Activity mActivity;

    public PluginHelper(Activity activity) {
        mActivity = activity;
    }

    // Apk是否安装
    public boolean isApkInstall(AppInfoEntity entity) {
        PackageInfo info;
        try {
            info = PluginManager.getInstance().getPackageInfo(entity.getPackageName(), 0);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
        return info != null;
    }

    // 安装Apk, 耗时较长, 需要使用异步线程
    public int installApk(final AppInfoEntity entity) {
        if (!com.morgoo.droidplugin.pm.PluginManager.getInstance().isConnected()) {
            return DROID_CONNECT_FAIL;
        }

        if (isApkInstall(entity)) {
            return DROID_INSTALLED;
        }

        try {
            int result = PluginManager.getInstance().installPackage(entity.getApkPath(), 0);
            if (result == PluginManager.INSTALL_FAILED_NO_REQUESTEDPERMISSION) {
                return DROID_REQUEST_PERMISSION;
            }
        } catch (RemoteException e) {
            return DROID_INSTALL_FAIL;
        }

        return DROID_INSTALL_SUCCESS;
    }

    // 卸载Apk
    public void uninstallApk(final AppInfoEntity entity) {
        new TipDialog(mActivity).show("卸载", "确定要卸载" + entity.getAppName() + "么？", new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);
                try {
                    PluginManager.getInstance().deletePackage(entity.getPackageName(), 0);
                    SweetDialog.show(mActivity, "卸载完成", SweetAlertDialog.NORMAL_TYPE);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // 打开Apk
    public void startApk(final AppInfoEntity entity) {
        PackageManager pm = mActivity.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(entity.getPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mActivity.startActivity(intent);
        } else {
            Toast.makeText(mActivity, ""+entity.getPackageName(), Toast.LENGTH_LONG).show();
        }
    }

}

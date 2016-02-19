package com.sun.study.module.plugin;

import android.app.Activity;
import android.os.AsyncTask;

import com.sun.study.framework.dialog.SweetDialog;
import com.sun.study.model.AppInfoEntity;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by sunfusheng on 16/2/19.
 */
public class InstallApkAsyncTask extends AsyncTask<Void, Void, Integer> {

    private Activity mActivity;
    private AppInfoEntity appEntity;
    private PluginHelper pluginHelper;
    private SweetAlertDialog mProgressDialog;

    public InstallApkAsyncTask(Activity activity, AppInfoEntity appEntity) {
        this.mActivity = activity;
        this.appEntity = appEntity;
        pluginHelper = new PluginHelper(mActivity);
        mProgressDialog = SweetDialog.showProgressDialog(activity, "正在安装...");
    }

    @Override
    protected void onPostExecute(Integer status) {
        switch (status) {
            case PluginHelper.DROID_CONNECT_FAIL: // 插件服务未连接
                SweetDialog.show(mActivity, "插件服务未连接", SweetAlertDialog.NORMAL_TYPE);
                break;
            case PluginHelper.DROID_INSTALLED: // 已安装
                SweetDialog.show(mActivity, "已安装", SweetAlertDialog.NORMAL_TYPE);
                break;
            case PluginHelper.DROID_INSTALLING: // 正在安装...
                break;
            case PluginHelper.DROID_REQUEST_PERMISSION: // 宿主包权限不足
                SweetDialog.show(mActivity, "宿主包权限不足", SweetAlertDialog.NORMAL_TYPE);
                break;
            case PluginHelper.DROID_INSTALL_SUCCESS: // 安装成功
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
                SweetDialog.show(mActivity, "安装成功", SweetAlertDialog.SUCCESS_TYPE);
                break;
            case PluginHelper.DROID_INSTALL_FAIL: // 安装失败
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
                SweetDialog.show(mActivity, "安装失败", SweetAlertDialog.ERROR_TYPE);
                break;
        }
    }

    @Override
    protected Integer doInBackground(Void... params) {
        return pluginHelper.installApk(appEntity);
    }
}

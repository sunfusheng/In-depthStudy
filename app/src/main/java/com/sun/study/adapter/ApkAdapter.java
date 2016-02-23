package com.sun.study.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sun.study.R;
import com.sun.study.constant.PluginParams;
import com.sun.study.framework.dialog.SweetDialog;
import com.sun.study.framework.dialog.TipDialog;
import com.sun.study.model.AppInfoEntity;
import com.sun.study.module.plugin.InstallApkAsyncTask;
import com.sun.study.module.plugin.PluginHelper;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by sunfusheng on 16/2/18.
 */
public class ApkAdapter extends BaseListAdapter<AppInfoEntity> {

    private Activity mActivity;
    private PluginHelper pluginHelper;

    public ApkAdapter(Context context) {
        super(context);
    }

    public ApkAdapter(Activity activity, List<AppInfoEntity> list) {
        this(activity);
        this.mActivity = activity;
        addALL(list);
        if (pluginHelper == null) {
            pluginHelper = new PluginHelper(activity);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_apk, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final AppInfoEntity entity = getItem(position);

        holder.ivApkIcon.setImageDrawable(entity.getAppIcon());
        holder.tvApkName.setText(entity.getAppName());

        holder.rlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pluginHelper.isApkInstall(entity)) {
                    pluginHelper.startApk(entity);
                    gotoPlugin();
                } else {
                    new TipDialog(mContext).show("未安装", "确定要安装〖" + entity.getAppName() + "〗么？", new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            super.onPositive(dialog);
                            new InstallApkAsyncTask(mActivity, entity).execute();
                        }
                    });
                }
            }
        });
        holder.ivOverFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(entity, v);
            }
        });

        return convertView;
    }

    private void showPopupMenu(final AppInfoEntity entity, View ancho) {
        if (!com.morgoo.droidplugin.pm.PluginManager.getInstance().isConnected()) {
            SweetDialog.show(mContext, "插件服务未连接", SweetAlertDialog.NORMAL_TYPE);
            return ;
        }

        final PopupMenu popupMenu = new PopupMenu(mContext,ancho);
        popupMenu.getMenuInflater().inflate(R.menu.item_pop_menu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                popupMenu.dismiss();
                switch (item.getItemId()) {
                    case R.id.menu_install:
                        new InstallApkAsyncTask(mActivity, entity).execute();
                        break;
                    case R.id.menu_update:
                        break;
                    case R.id.menu_start:
                        pluginHelper.startApk(entity);
                        break;
                    case R.id.menu_uninstall:
                        pluginHelper.uninstallApk(entity);
                        break;
                }
                return false;
            }
        });

        MenuItem install = popupMenu.getMenu().findItem(R.id.menu_install);
        MenuItem update = popupMenu.getMenu().findItem(R.id.menu_update);
        MenuItem start = popupMenu.getMenu().findItem(R.id.menu_start);
        MenuItem uninstall = popupMenu.getMenu().findItem(R.id.menu_uninstall);

        update.setVisible(false);

        if (pluginHelper.isApkInstall(entity)) {
            install.setVisible(false);
            uninstall.setVisible(true);
            start.setVisible(true);
        } else {
            install.setVisible(true);
            uninstall.setVisible(false);
            start.setVisible(false);
        }

        popupMenu.show();
    }

    // 跳转控件
    private void gotoPlugin() {
        if (isActionAvailable(mContext, PluginParams.PLUGIN_ACTION_MAIN)) {
            Intent intent = new Intent(PluginParams.PLUGIN_ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(PluginParams.PLUGIN_EXTRA_STRING, "嗨！这是宿主包传过来的字符串！");
            mContext.startActivity(intent);
        } else {
            SweetDialog.show(mContext, "跳转失败", SweetAlertDialog.NORMAL_TYPE);
        }
    }

    // Action是否允许
    public static boolean isActionAvailable(Context context, String action) {
        Intent intent = new Intent(action);
        return context.getPackageManager().resolveActivity(intent, 0) != null;
    }

    static class ViewHolder {
        @Bind(R.id.rl_layout)
        RelativeLayout rlLayout;
        @Bind(R.id.iv_apk_icon)
        ImageView ivApkIcon;
        @Bind(R.id.tv_apk_name)
        TextView tvApkName;
        @Bind(R.id.iv_over_flow)
        ImageView ivOverFlow;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

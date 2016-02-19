package com.sun.study.framework.dialog;

import android.content.Context;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by sunfusheng on 16/2/19.
 */
public class SweetDialog {

    public static SweetAlertDialog show(Context context, String tip, int alertType) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, alertType);
        dialog.setTitleText(tip);
        dialog.setConfirmText("确定");
        if (alertType == SweetAlertDialog.WARNING_TYPE) {
            dialog.setCancelText("取消");
            dialog.showCancelButton(true);
        } else {
            dialog.showCancelButton(false);
        }
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;
    }

    public static SweetAlertDialog showProgressDialog(Context context, String tip) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setTitleText(tip);
        dialog.setConfirmText("确定");
        dialog.setCancelText("取消");
        dialog.show();
        return dialog;
    }
    
}

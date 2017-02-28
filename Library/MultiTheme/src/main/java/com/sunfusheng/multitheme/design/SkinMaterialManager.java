package com.sunfusheng.multitheme.design;

import android.content.Context;

import com.sunfusheng.multitheme.SkinCompatManager;
import com.sunfusheng.multitheme.design.app.SkinMaterialViewInflater;


public class SkinMaterialManager {
    private static volatile SkinMaterialManager sInstance;

    public static SkinMaterialManager init(Context context) {
        if (sInstance == null) {
            synchronized (SkinMaterialManager.class) {
                if (sInstance == null) {
                    sInstance = new SkinMaterialManager(context);
                }
            }
        }
        return sInstance;
    }

    public static SkinMaterialManager getInstance() {
        return sInstance;
    }

    private SkinMaterialManager(Context context) {
        SkinCompatManager.init(context).addInflater(new SkinMaterialViewInflater());
    }
}

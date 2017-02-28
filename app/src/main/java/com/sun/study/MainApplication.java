package com.sun.study;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.morgoo.droidplugin.PluginHelper;
import com.sun.study.framework.exception.CrashHandler;
import com.sun.study.framework.proxy.ControlFactory;
import com.sun.study.framework.sharedpreferences.FastJsonSerial;
import com.sun.study.util.FrescoUtil;

import de.devland.esperandro.Esperandro;

/**
 * Created by sunfusheng on 15/11/18.
 */
public class MainApplication extends Application {

    private static MainApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        Esperandro.setSerializer(new FastJsonSerial());
        ControlFactory.init(this);
        FrescoUtil.init(this);
        CrashHandler.getInstance().init(this);
//        PluginHelper.getInstance().applicationOnCreate(getBaseContext());
    }

    public static MainApplication getInstance() {
        return instance;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        FrescoUtil.clearMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        FrescoUtil.clearCaches();
    }

    @Override
    protected void attachBaseContext(Context base) {
        PluginHelper.getInstance().applicationAttachBaseContext(base);
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}

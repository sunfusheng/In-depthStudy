package com.sun.study;

import android.app.Application;

import com.alipay.euler.andfix.patch.PatchManager;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.sun.study.framework.exception.CrashHandler;
import com.sun.study.framework.proxy.ControlFactory;
import com.sun.study.framework.sharedpreferences.FastJsonSerial;

import de.devland.esperandro.Esperandro;

/**
 * Created by sunfusheng on 15/11/18.
 */
public class MainApplication extends Application {

    private static MainApplication instance;
    private static PatchManager patchManager;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        Esperandro.setSerializer(new FastJsonSerial());

        ControlFactory.init(this);

        Fresco.initialize(this);

        CrashHandler.getInstance().init(this);

        patchManager = new PatchManager(this);
        patchManager.init("1.1");
        patchManager.loadPatch();
    }

    public static MainApplication getInstance() {
        return instance;
    }

    public static PatchManager getPatchManager() {
        return patchManager;
    }
}

package com.sun.study;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.sun.study.framework.proxy.ControlFactory;
import com.sun.study.framework.sharedpreferences.FastJsonSerial;

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

        Fresco.initialize(this);
    }

    public static MainApplication getInstance() {
        return instance;
    }
}

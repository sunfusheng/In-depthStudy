package com.sun.study.framework.proxy.handler;

import android.app.Activity;
import android.content.Context;

import com.sun.study.MainApplication;

public class AsyncObjectHandler extends BaseHandler<Object> {

    private Context mContext;

    public AsyncObjectHandler(Object obj) {
        super(obj);
    }

    public AsyncObjectHandler(Object obj, Context context) {
        super(obj);
        this.mContext = context;
    }

    @Override
    public Context getContext() {
        if (mContext == null) {
            return MainApplication.getInstance();
        } else {
            return mContext;
        }
    }

    @Override
    protected Object checkAvailability() {
        Context context = getContext();
        if (context != null) {
            if ((context instanceof Activity) && ((Activity) context).isFinishing()) {
                return null;
            }
        }
        return mReference.get();
    }

}

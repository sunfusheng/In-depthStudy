package com.sun.study.framework.proxy.helper;

import com.sun.study.framework.base.BaseAsyncFragment;
import com.sun.study.framework.base.BaseControl;
import com.sun.study.framework.proxy.handler.BaseHandler;

public class FragmentHelper<T extends BaseControl, R extends BaseAsyncFragment> extends BaseHelper<T, R> {

    public FragmentHelper(R refrenceObj, BaseHandler handler) {
        super(refrenceObj, handler);
    }

    public void onDestoryView() {
        if (mControl != null) {
            mControl.onDestroyView();
        }
    }

    public void onAttachView() {}
}

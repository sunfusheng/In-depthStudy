package com.sun.study.framework.proxy.helper;

import com.sun.study.framework.base.BaseAsyncObject;
import com.sun.study.framework.base.BaseControl;
import com.sun.study.framework.proxy.handler.BaseHandler;

/**
 * Created by sunfusheng on 15/11/5.
 */
public class ObjectHelper<T extends BaseControl, R extends BaseAsyncObject> extends BaseHelper<T, R> {

    public ObjectHelper(R referenceObj, BaseHandler handler) {
        super(referenceObj, handler);
    }
}

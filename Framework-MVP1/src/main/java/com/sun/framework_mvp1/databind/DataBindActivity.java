package com.sun.framework_mvp1.databind;

import android.os.Bundle;

import com.sun.framework_mvp1.model.IModel;
import com.sun.framework_mvp1.presenter.BaseActivityPresenter;
import com.sun.framework_mvp1.view.i.IBaseDelegate;

/**
 * 集成数据-视图绑定的Activity基类(Presenter层)
 *
 * @param <T> View层代理类
 * @author kymjs (http://www.kymjs.com/) on 10/23/15.
 */
public abstract class DataBindActivity<T extends IBaseDelegate> extends
        BaseActivityPresenter<T> {
    protected DataBinder binder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binder = getDataBinder();
    }

    public abstract DataBinder getDataBinder();

    public <D extends IModel> void notifyModelChanged(D data) {
        if (binder != null)
            binder.viewBindModel(viewDelegate, data);
    }
}

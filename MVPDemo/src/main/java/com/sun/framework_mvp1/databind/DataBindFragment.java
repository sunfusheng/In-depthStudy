package com.sun.framework_mvp1.databind;

import android.os.Bundle;
import android.view.View;

import com.sun.framework_mvp1.model.IModel;
import com.sun.framework_mvp1.presenter.BaseFragmentPresenter;
import com.sun.framework_mvp1.view.i.IBaseDelegate;

/**
 * 集成数据-视图绑定的Fragment基类(Presenter层)
 *
 * @param <T> View层代理类
 * @author kymjs (http://www.kymjs.com/) on 10/26/15.
 */
public abstract class DataBindFragment<T extends IBaseDelegate> extends
        BaseFragmentPresenter<T> {

    protected DataBinder binder;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binder = getDataBinder();
    }

    public abstract DataBinder getDataBinder();

    public <D extends IModel> void notifyModelChanged(D data) {
        if (binder != null)
            binder.viewBindModel(viewDelegate, data);
    }
}

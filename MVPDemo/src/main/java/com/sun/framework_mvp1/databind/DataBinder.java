package com.sun.framework_mvp1.databind;

import com.sun.framework_mvp1.model.IModel;
import com.sun.framework_mvp1.view.i.IBaseDelegate;

/**
 * ViewModel实现
 *
 * @author kymjs (http://www.kymjs.com/) on 10/30/15.
 */
public interface DataBinder<T extends IBaseDelegate, D extends IModel> {

    /**
     * 将数据与View绑定，这样当数据改变的时候，框架就知道这个数据是和哪个View绑定在一起的，就可以自动改变ui
     * 当数据改变的时候，会回调本方法。
     *
     * @param viewDelegate 视图层代理
     * @param data         数据模型对象
     */
    void viewBindModel(T viewDelegate, D data);

//    /**
//     * 将数据与View绑定，这样当数据改变的时候，框架就知道这个数据是和哪个View绑定在一起的，就可以自动改变ui
//     * 当ui改变的时候，会回调本方法。
//     *
//     * @param viewDelegate 视图层代理
//     * @param data         数据模型对象
//     */
//    void modelBindView(T viewDelegate, D data);
}

package com.sun.study.control;

import com.sun.study.framework.base.BaseControl;
import com.sun.study.framework.proxy.MessageProxy;

/**
 * Created by sunfusheng on 15/7/21.
 */
public class PageControl extends BaseControl {

    public static final int PAGE_SIZE_LIMIT = 10;
    private int pageSize = PAGE_SIZE_LIMIT;
    private int lastPageSize = 0;

    public PageControl(MessageProxy mMessageCallBack) {
        super(mMessageCallBack);
    }


}

package com.sun.study.framework.proxy.filter;

import java.lang.reflect.Method;

public class AsyncMethodFilter implements InterceptorFilter {

    @Override
    public int accept(Method method) {
        return 0;
    }

}

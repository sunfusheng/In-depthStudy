package com.sun.study.model.annotation;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.sun.study.framework.dialog.LoadingDialog;
import com.sun.study.framework.dialog.ToastTip;

import java.lang.reflect.Method;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sunfusheng on 16/4/18.
 */
public class DynamicProxyUtil {

    private static ExecutorService pool = Executors.newCachedThreadPool();
    private Context mContext;
    private LoadingDialog loadingDialog;

    private final int TYPE_SUCCESS = 0;
    private final int TYPE_ERROR = -1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
            switch (msg.what) {
                case TYPE_ERROR:
                    ToastTip.show("执行失败");
                    break;
                case TYPE_SUCCESS:
                    ToastTip.show("执行成功");
                    break;
            }
        }
    };

    public DynamicProxyUtil(Context context) {
        this.mContext = context;
        loadingDialog = new LoadingDialog(context);
    }

    // 模拟处理网络请求
    public boolean process(final Class<?> clazz, String methodName, final Object... args) throws Exception {
        Class[] argsClass = getClazzByArgs(args);

        final Method method = clazz.getDeclaredMethod(methodName, argsClass);
        if (method == null) {
            sendMsg(TYPE_ERROR);
            return false;
        }

        // 获取注解信息
        RequestAnnotation annotation = method.getAnnotation(RequestAnnotation.class);
        if (annotation != null && annotation.withDialog()) {
            loadingDialog.show(annotation.withMessage());
        }

        pool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    method.setAccessible(true);
                    method.invoke(clazz.newInstance(), args);
                    sendMsg(TYPE_SUCCESS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return true;
    }

    // 获取参数数组的类型
    private Class[] getClazzByArgs(Object... args) {
        if (args == null || args.length == 0) return null;

        Class[] argsClass = new Class[args.length];
        for (int i=0; i<args.length;i++) {
            argsClass[i] = args[i].getClass();
        }
        return argsClass;
    }

    // 发送消息
    private void sendMsg(int what) {
        Message msg = mHandler.obtainMessage();
        msg.what = what;
        mHandler.sendMessage(msg);
    }

}

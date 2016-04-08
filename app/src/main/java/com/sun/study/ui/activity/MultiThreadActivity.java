package com.sun.study.ui.activity;

import android.os.Bundle;

import com.sun.study.R;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by sunfusheng on 16/4/8.
 */
public class MultiThreadActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_thread);

        DemoThread t = new DemoThread();
        t.run();



        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "个人博客：sunfusheng.com";
            }
        };

        FutureTask<String> task = new FutureTask<>(callable);



        Thread t1 = new Thread(task);
        t1.start();

    }

    class DemoThread extends Thread {

        @Override
        public void run() {
            super.run();
        }
    }

}

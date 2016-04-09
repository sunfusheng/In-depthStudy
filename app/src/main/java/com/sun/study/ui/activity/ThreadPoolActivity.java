package com.sun.study.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sun.study.R;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/4/9.
 */
public class ThreadPoolActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_info1)
    TextView tvInfo1;
    @Bind(R.id.progressBar1)
    ProgressBar progressBar1;
    @Bind(R.id.tv_info2)
    TextView tvInfo2;
    @Bind(R.id.progressBar2)
    ProgressBar progressBar2;
    @Bind(R.id.tv_info3)
    TextView tvInfo3;
    @Bind(R.id.progressBar3)
    ProgressBar progressBar3;
    @Bind(R.id.tv_start1)
    TextView tvStart1;
    @Bind(R.id.tv_start2)
    TextView tvStart2;
    @Bind(R.id.tv_start3)
    TextView tvStart3;
    @Bind(R.id.tv_cancel1)
    TextView tvCancel1;
    @Bind(R.id.tv_cancel2)
    TextView tvCancel2;
    @Bind(R.id.tv_cancel3)
    TextView tvCancel3;
    @Bind(R.id.checkBox1)
    CheckBox checkBox1;
    @Bind(R.id.checkBox2)
    CheckBox checkBox2;
    @Bind(R.id.checkBox3)
    CheckBox checkBox3;
    @Bind(R.id.tv_start)
    TextView tvStart;
    @Bind(R.id.tv_cancel)
    TextView tvCancel;
    @Bind(R.id.tv_shut_down)
    TextView tvShutDown;
    @Bind(R.id.tv_shut_down_now)
    TextView tvShutDownNow;
    @Bind(R.id.tv_info)
    TextView tvInfo;

    private ExecutorService pool = Executors.newCachedThreadPool();

    private Callable<Integer> callable1;
    private Future<Integer> task1;

    private Callable<Integer> callable2;
    private Future<Integer> task2;

    private Callable<Integer> callable3;
    private Future<Integer> task3;

    private final int THREAD1_START = 1001;
    private final int THREAD2_START = 1002;
    private final int THREAD3_START = 1003;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case THREAD1_START:
                    tvInfo1.setText("线程一: " + msg.arg1);
                    progressBar1.setProgress(msg.arg1);
                    break;
                case THREAD2_START:
                    tvInfo2.setText("线程二: " + msg.arg1);
                    progressBar2.setProgress(msg.arg1);
                    break;
                case THREAD3_START:
                    tvInfo3.setText("线程三: " + msg.arg1);
                    progressBar3.setProgress(msg.arg1);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);
        ButterKnife.bind(this);

        initView();
        initListener();
    }

    private void initView() {
        initToolBar(toolbar, true, "线程池");
        progressBar1.setProgress(0);
        progressBar1.setMax(100);
        progressBar2.setProgress(0);
        progressBar2.setMax(100);
        progressBar3.setProgress(0);
        progressBar3.setMax(100);
    }

    private void initListener() {
        tvStart.setOnClickListener(this);
        tvStart1.setOnClickListener(this);
        tvStart2.setOnClickListener(this);
        tvStart3.setOnClickListener(this);

        tvCancel.setOnClickListener(this);
        tvCancel1.setOnClickListener(this);
        tvCancel2.setOnClickListener(this);
        tvCancel3.setOnClickListener(this);

        tvShutDown.setOnClickListener(this);
        tvShutDownNow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_start:
                if (checkBox1.isChecked()) {
                    startThread1();
                }
                if (checkBox2.isChecked()) {
                    startThread2();
                }
                if (checkBox3.isChecked()) {
                    startThread3();
                }
                break;
            case R.id.tv_start1:
                startThread1();
                break;
            case R.id.tv_start2:
                startThread2();
                break;
            case R.id.tv_start3:
                startThread3();
                break;
            case R.id.tv_cancel:
                if (checkBox1.isChecked()) {
                    cancelThread(task1);
                }
                if (checkBox2.isChecked()) {
                    cancelThread(task2);
                }
                if (checkBox3.isChecked()) {
                    cancelThread(task3);
                }
                break;
            case R.id.tv_cancel1:
                cancelThread(task1);
                break;
            case R.id.tv_cancel2:
                cancelThread(task2);
                break;
            case R.id.tv_cancel3:
                cancelThread(task3);
                break;
            case R.id.tv_shut_down:
                if (pool != null && !pool.isShutdown()) {
                    pool.shutdown();
                }
                break;
            case R.id.tv_shut_down_now:
                if (pool != null && !pool.isShutdown()) {
                    pool.shutdownNow();
                }
                break;
        }
    }

    private void startThread1() {
        if (task1 != null && !task1.isDone() || pool.isShutdown()) return;

        callable1 = new Callable<Integer>() {
            int num = 0;

            @Override
            public Integer call() throws Exception {
                while (num < 100) {
                    num++;
                    sendMsg(num, THREAD1_START);
                    Thread.sleep(100);
                }
                return 100;
            }
        };

        task1 = pool.submit(callable1);
    }

    private void startThread2() {
        if (task2 != null && !task2.isDone() || pool.isShutdown()) return;

        callable2 = new Callable<Integer>() {
            int num = 0;

            @Override
            public Integer call() throws Exception {
                while (num < 100) {
                    num++;
                    sendMsg(num, THREAD2_START);
                    Thread.sleep(100);
                }
                return 100;
            }
        };

        task2 = pool.submit(callable2);
    }

    private void startThread3() {
        if (task3 != null && !task3.isDone() || pool.isShutdown()) return;

        callable3 = new Callable<Integer>() {
            int num = 0;

            @Override
            public Integer call() throws Exception {
                while (num < 100) {
                    num++;
                    sendMsg(num, THREAD3_START);
                    Thread.sleep(100);
                }
                return 100;
            }
        };

        task3 = pool.submit(callable3);
    }

    private void cancelThread(Future<Integer> task) {
        if (task != null && !pool.isShutdown()) {
            task.cancel(true);
        }
    }

    private void sendMsg(int arg1, int what) {
        Message msg = Message.obtain();
        msg.arg1 = arg1;
        msg.what = what;
        mHandler.sendMessage(msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_thread_pool, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_newCachedThreadPool:
                pool = Executors.newCachedThreadPool();
                tvInfo.setText("当前线程池类型: newCachedThreadPool()");
                return true;
            case R.id.item_newSingleThreadExecutor:
                pool = Executors.newSingleThreadExecutor();
                tvInfo.setText("当前线程池类型: newSingleThreadExecutor()");
                return true;
            case R.id.item_newFixedThreadPool:
                pool = Executors.newFixedThreadPool(2);
                tvInfo.setText("当前线程池类型: newFixedThreadPool(2)");
                return true;
            case R.id.item_newScheduledThreadPool:
                pool = Executors.newScheduledThreadPool(2);
                tvInfo.setText("当前线程池类型: newScheduledThreadPool(2)");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pool != null && !pool.isShutdown()) {
            pool.shutdownNow();
        }
    }
}

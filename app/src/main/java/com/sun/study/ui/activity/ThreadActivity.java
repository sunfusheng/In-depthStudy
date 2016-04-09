package com.sun.study.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sun.study.R;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/4/8.
 */
public class ThreadActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.tv_start)
    TextView tvStart;
    @Bind(R.id.tv_cancel)
    TextView tvCancel;
    @Bind(R.id.tv_info1)
    TextView tvInfo1;
    @Bind(R.id.tv_info2)
    TextView tvInfo2;
    @Bind(R.id.cb_cancel)
    CheckBox cbCancel;

    private Callable<String> callable;
    private FutureTask<String> task;
    private Thread thread;

    private final int TYPE_MSG_RUN = 1001;
    private final int TYPE_MSG_DONE = 1002;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case TYPE_MSG_RUN:
                    progressBar.setProgress(msg.arg1);
                    tvInfo1.setText("线程计数: " + msg.arg1);
                    break;
                case TYPE_MSG_DONE:
                    tvInfo1.setText("计数完成");
                    showStatus();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        ButterKnife.bind(this);

        initView();
        initListener();
    }

    private void initView() {
        initToolBar(toolbar, true, "线程");

        progressBar.setMax(100);
        progressBar.setProgress(0);
    }

    private void initListener() {
        tvStart.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_start:
                if (task == null || thread == null || task.isDone()) {
                    startThread();
                    showStatus();
                }
                break;
            case R.id.tv_cancel:
                if (task != null) {
                    task.cancel(cbCancel.isChecked());
                    showStatus();
                }
                break;
        }
    }

    private void startThread() {
        callable = new Callable<String>() {

            int num = 0;

            @Override
            public String call() throws Exception {
                while (num < 100) {
                    num++;
                    sendMsg(num, TYPE_MSG_RUN);
                    Thread.sleep(100);
                }
                sendMsg(num, TYPE_MSG_DONE);
                return "Done!";
            }
        };

        task = new FutureTask<>(callable);

        thread = new Thread(task);
        thread.start();
    }

    private void showStatus() {
        if (task == null || thread == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("状态: ");
        sb.append("\nFutureTask isCancelled(): " + task.isCancelled());
        sb.append("\nFutureTask isDone(): " + task.isDone());
        sb.append("\n\nThread isAlive(): " + thread.isAlive());
        sb.append("\nThread isInterrupted(): " + thread.isInterrupted());
        tvInfo2.setText(sb.toString());
    }

    private void sendMsg(int arg1, int what) {
        Message msg = Message.obtain();
        msg.arg1 = arg1;
        msg.what = what;
        mHandler.sendMessage(msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (task != null && !task.isDone()) {
            task.cancel(true);
        }
    }
}

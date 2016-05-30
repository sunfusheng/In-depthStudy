package com.sun.study.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.sun.study.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/5/30.
 */
public class VSTextViewActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_VSTextView)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vs_textview);
        ButterKnife.bind(this);

        initToolBar(toolbar, true, "VSTextView");

        tv.setText("好雨知时节, 当春乃发生。随风潜入夜, 润物细无声。野径云俱黑, 江船火独明。晓看红湿处, 花重锦官城。");
        mHandler.postDelayed(mRunnable, 2000);
    }

    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            int height = tv.getHeight();
            int scrollY = tv.getScrollY();
            int lineHeight = tv.getLineHeight();
            int lineCount = tv.getLineCount();//总行数
            /**
             * textView不可见内容的高度，可以理解为偏移位移
             */
            int maxY = (tv.getLineCount() * tv.getLineHeight() +
                    tv.getPaddingTop() + tv.getPaddingBottom()) - tv.getHeight();

            Log.d("--->", "maxY: " + maxY);
            Log.d("--->", "height: " + height);
            Log.d("--->", "lineCount: " + lineCount);

            double viewCount = Math.floor(height / lineHeight);//可见区域最大显示多少行
            if (lineCount > viewCount) {//总行数大于可见区域显示的行数时则滚动

                if (scrollY >= maxY) {
                    tv.scrollBy(0, -maxY);
                } else {
                    tv.scrollBy(0, lineHeight);
                }
                mHandler.postDelayed(this, 2000);
            }

        }

    };
}

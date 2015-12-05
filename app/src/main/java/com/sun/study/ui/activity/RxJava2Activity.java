package com.sun.study.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sun.study.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by sunfusheng on 15/12/3.
 */
public class RxJava2Activity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_Content)
    TextView tvContent;

    private StringBuilder sb = new StringBuilder();
    private Integer[] intArr = {1,2,3,4,5,6,7,8,9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava2);
        ButterKnife.bind(this);

        initToolBar(toolbar, true, "RxJava(二)");
    }

    public void mapClick(View v) {
        sb = new StringBuilder();
        Observable.from(intArr).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                return "map(): "+integer;
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onStart() {
                super.onStart();
                sb.append("map()\n");
            }

            @Override
            public void onCompleted() {
                sb.append("onCompleted()");
                tvContent.setText(sb.toString());
            }

            @Override
            public void onError(Throwable e) {
                sb.append("onError(): "+e.getMessage());
                tvContent.setText(sb.toString());
            }

            @Override
            public void onNext(String str) {
                sb.append("onNext(): "+str+"\n");
            }
        });
    }

    public void flatMapClick(View v) {
        sb = new StringBuilder();
        Observable.from(intArr).flatMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer integer) {
                return Observable.just("flatmap(): "+integer);
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onStart() {
                super.onStart();
                sb.append("flatMap()\n");
            }

            @Override
            public void onCompleted() {
                sb.append("onCompleted()");
                tvContent.setText(sb.toString());
            }

            @Override
            public void onError(Throwable e) {
                sb.append("onError(): "+e.getMessage());
                tvContent.setText(sb.toString());
            }

            @Override
            public void onNext(String str) {
                sb.append("onNext(): "+str+"\n");
            }
        });
    }

}

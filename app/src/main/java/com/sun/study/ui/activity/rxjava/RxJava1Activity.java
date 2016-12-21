package com.sun.study.ui.activity.rxjava;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sun.study.R;
import com.sun.study.ui.activity.BaseActivity;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sunfusheng on 15/12/3.
 */
public class RxJava1Activity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_Content)
    TextView tvContent;

    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava1);
        ButterKnife.bind(this);

        initToolBar(toolbar, true, "RxJava(一)");
    }

    public void createClick(View v) {
        sb = new StringBuilder();
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    int temp = new Random().nextInt(10);
                    if (temp > 8) {
                        //if value>8, we make an error
                        subscriber.onError(new Throwable("value >8"));
                        break;
                    } else {
                        subscriber.onNext(temp);
                    }
                    // on error,complete the job
                    if (i == 4) {
                        subscriber.onCompleted();
                    }
                }
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onStart() {
                super.onStart();
                sb.append("create()\n");
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
            public void onNext(Integer integer) {
                sb.append("onNext(): "+integer+"\n");
            }
        });
    }

    public void deferClick(View v) {
        Observable.defer(() ->
                Observable.just(System.currentTimeMillis()))
                .subscribe(aLong -> {
                    sb.append("defer(): "+aLong+"\n");
                    tvContent.setText(sb.toString());
                });
    }

    public void justClick(View v) {
        sb = new StringBuilder();
        String[] strings = {"this", "is", "just", "operator"};
        Observable.just(strings)
                .flatMap(Observable::from)
                .subscribe(s -> {
                    sb.append("just(): "+s+"\n");
                    tvContent.setText(sb);
                },Throwable::printStackTrace);
    }

    public void fromClick(View v) {
        sb = new StringBuilder();
        String[] strArr = {"this", "is", "from()"};
        Observable.from(strArr).subscribe(str -> {
            sb.append("from(): "+str+"\n");
            tvContent.setText(sb.toString());
        });
    }

    private Subscriber subscriber;

    public void intervalClick(View v) {
        sb = new StringBuilder();
        if (subscriber != null) {
            subscriber.unsubscribe();
        }
        subscriber = new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                sb.append("onCompleted(): \n");
                tvContent.setText(sb.toString());
            }

            @Override
            public void onError(Throwable e) {
                sb.append("onError(): "+e.getMessage()+"\n");
                tvContent.setText(sb.toString());
            }

            @Override
            public void onNext(Long aLong) {
                sb.append("interval(): "+aLong+"\n");
                tvContent.setText(sb.toString());
            }
        };

        Observable.interval(1, TimeUnit.SECONDS, Schedulers.computation())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void repeatClick(View v) {
        sb = new StringBuilder();
        Observable.just(1).repeat(5)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {
                        sb.append("onCompleted(): \n");
                        tvContent.setText(sb.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        sb.append("onError(): "+e.getMessage()+"\n");
                        tvContent.setText(sb.toString());
                    }

                    @Override
                    public void onNext(Integer i) {
                        sb.append("repeat(): "+i+"\n");
                        tvContent.setText(sb.toString());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscriber != null) {
            subscriber.unsubscribe();
        }
    }
}

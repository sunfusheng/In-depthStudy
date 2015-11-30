package com.sun.study.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sun.study.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Producer;
import rx.Subscriber;

/**
 * Created by sunfusheng on 15/11/27.
 */
public class RxJavaFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.tv_tips)
    TextView tvTips;

    private static final String TAG = RxJavaFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rxjava, container, false);
        ButterKnife.bind(this, rootView);

        initData();
        initView();
        initListener();
        return rootView;
    }

    private void initData() {
        //订阅者
        Subscriber subscriber = new Subscriber<String>() {

            StringBuilder sb = new StringBuilder();

            @Override
            public void onStart() {
                super.onStart();
                sb.append("Subscriber:\nonStart()\n");
            }

            @Override
            public void setProducer(Producer p) {
                super.setProducer(p);
                sb.append("setProducer(): " + p.toString() + "\n");
            }

            @Override
            public void onNext(String s) {
                sb.append("onNext(): "+s+"\n");
            }

            @Override
            public void onCompleted() {
                sb.append("onCompleted()\n\n");
                tvTips.setText(sb.toString());
            }

            @Override
            public void onError(Throwable e) {
                sb.append("\"onError(): \"+e.getMessage()\n\n");
                tvTips.setText(sb.toString());
            }
        };

        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Learning");
                subscriber.onNext("RxJava");
            }
        });
        observable.subscribe(subscriber);

        String[] tips = {"Hello", "RxJava"};
        Observable observable1 = Observable.from(tips);
        observable1.subscribe(subscriber);
    }

    private void initView() {

    }

    private void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

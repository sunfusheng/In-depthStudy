package com.sun.study.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sun.study.R;
import com.sun.study.view.ChoiceView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 16/1/14.
 */
public class ShoppingViewActivity extends BaseActivity {

    @Bind(R.id.cv_choice1)
    ChoiceView cvChoice1;
    @Bind(R.id.cv_choice2)
    ChoiceView cvChoice2;
    @Bind(R.id.cv_choice3)
    ChoiceView cvChoice3;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingview);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        initToolBar(toolbar, true, "购物车");
        cvChoice1.setSellOutViewVisibility(true);
        cvChoice2.setTomorrowOnlyViewVisibility(true);
        cvChoice3.setOnChoiceViewClickListener(new ChoiceView.OnChoiceViewClickListener() {
            @Override
            public void onChoiceViewCount(int count) {

            }
        });
    }
}

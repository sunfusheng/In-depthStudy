package com.sun.study.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sun.study.R;
import com.sun.study.util.DisplayUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sunfusheng on 15/11/23.
 */
public class ChoiceView extends RelativeLayout {

    @Bind(R.id.i_iv_kitchen_detail_little_table_item_minus)
    ImageView ivMinus;
    @Bind(R.id.i_rl_kitchen_detail_item_minus_back)
    RelativeLayout rlMinus;
    @Bind(R.id.i_iv_kitchen_detail_little_table_item_plus)
    ImageView ivPlus;
    @Bind(R.id.i_rl_kitchen_detail_item_plus_back)
    RelativeLayout rlPlus;
    @Bind(R.id.i_tv_kitchen_detail_item_count)
    TextView tvCount;
    @Bind(R.id.i_tv_kitchen_info)
    TextView tvKitchenInfo;

    private Context mContext;
    private int count = 0;

    public ChoiceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ChoiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        initAttrs(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_choiceview_layout, this);
        ButterKnife.bind(this, view);
        initView();
        initListener();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
    }

    private void initView() {
        rlMinus.setVisibility(INVISIBLE);
        tvCount.setVisibility(INVISIBLE);
    }

    private void initListener() {
        ivMinus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    return ;
                }
                if (count == 1) {
                    rlMinus.setVisibility(INVISIBLE);
                    tvCount.setVisibility(INVISIBLE);
                    animDismissBk();
                    animDismissCount();
                    count--;
                } else {
                    count--;
                    tvCount.setText("" + count);
                }
                if (onChoiceViewClickListener != null) {
                    onChoiceViewClickListener.onChoiceViewCount(count);
                }
            }
        });
        ivPlus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0) {
                    rlMinus.setVisibility(VISIBLE);
                    tvCount.setVisibility(VISIBLE);
                    animShowBk();
                    animShowCount();
                }
                count++;
                tvCount.setText("" + count);
                if (onChoiceViewClickListener != null) {
                    onChoiceViewClickListener.onChoiceViewCount(count);
                }
            }
        });
    }

    /**
     * 动画不显示数字
     */
    private void animDismissCount() {
        AlphaAnimation am = new AlphaAnimation(1f, 0f);
        am.setDuration(40);
        am.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tvCount.setText("0");
                tvCount.setVisibility(View.INVISIBLE);
            }
        });
        tvCount.startAnimation(am);
    }

    /**
     * 动画显示数字
     */
    private void animShowCount() {
        AlphaAnimation am = new AlphaAnimation(0f, 1.0f);
        am.setDuration(250);
        tvCount.startAnimation(am);
    }

    /**
     * 动画显示背景
     */
    private void animShowBk() {
        ScaleAnimation sa = new ScaleAnimation(0.3f, 1f, 1f, 1f,
                Animation.RELATIVE_TO_SELF, 0.8f, Animation.RELATIVE_TO_SELF, 0.8f);
        sa.setDuration(80);
        sa.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                float fromXDelta = ivMinus.getX();
                float toXDelta = ivMinus.getX() - DisplayUtil.dip2px(mContext, 2);
                float fromYDelta = ivMinus.getY();
                TranslateAnimation ta = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, fromYDelta);
                ta.setDuration(80);
                ta.setInterpolator(new DecelerateInterpolator());
                ta.setFillAfter(true);
                ta.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {}

                    @Override
                    public void onAnimationRepeat(Animation animation) {}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        float fromXDelta = ivMinus.getX() - DisplayUtil.dip2px(mContext, 2);
                        float toXDelta = ivMinus.getX() + DisplayUtil.dip2px(mContext, 1);
                        float fromYDelta = ivMinus.getY();
                        TranslateAnimation ta1 = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, fromYDelta);
                        ta1.setDuration(120);
                        ta1.setInterpolator(new AccelerateInterpolator());
                        ta1.setFillAfter(true);
                        ta1.setAnimationListener(new Animation.AnimationListener() {

                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                float fromXDelta = ivMinus.getX() + DisplayUtil.dip2px(mContext, 1);
                                float toXDelta = ivMinus.getX();
                                float fromYDelta = ivMinus.getY();
                                TranslateAnimation ta2 = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, fromYDelta);
                                ta2.setDuration(80);
                                ta2.setInterpolator(new DecelerateInterpolator());
                                ta2.setFillAfter(false);
                                ivMinus.startAnimation(ta2);
                            }
                        });
                        ivMinus.startAnimation(ta1);
                    }
                });
                ivMinus.startAnimation(ta);
            }
        });
        rlMinus.startAnimation(sa);
    }

    /**
     * 动画不显示背景
     */
    private void animDismissBk() {
        ScaleAnimation sa = new ScaleAnimation(1f, 0.2f, 1f, 1f,
                Animation.RELATIVE_TO_SELF, 0.9f, Animation.RELATIVE_TO_SELF, 0.9f);
        sa.setDuration(80);
        sa.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                ivMinus.setClickable(false);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                rlMinus.setVisibility(View.INVISIBLE);
                tvCount.setVisibility(View.INVISIBLE);
                ivMinus.setClickable(true);
            }
        });
        rlMinus.startAnimation(sa);
    }

    public void setSellOutViewVisibility(boolean isShow) {
        if (isShow) {
            rlMinus.setVisibility(GONE);
            rlPlus.setVisibility(GONE);
            tvCount.setVisibility(GONE);
            tvKitchenInfo.setVisibility(VISIBLE);
            tvKitchenInfo.setText(R.string.s_kitchen_detail_sell_out);
        } else {
            rlMinus.setVisibility(INVISIBLE);
            rlPlus.setVisibility(VISIBLE);
            tvCount.setVisibility(INVISIBLE);
            tvKitchenInfo.setVisibility(GONE);
        }
    }

    public void setTomorrowOnlyViewVisibility(boolean isShow) {
        if (isShow) {
            rlMinus.setVisibility(GONE);
            rlPlus.setVisibility(GONE);
            tvCount.setVisibility(GONE);
            tvKitchenInfo.setVisibility(VISIBLE);
            tvKitchenInfo.setText(R.string.s_kitchen_detail_tomorrow_only);
        } else {
            rlMinus.setVisibility(INVISIBLE);
            rlPlus.setVisibility(VISIBLE);
            tvCount.setVisibility(INVISIBLE);
            tvKitchenInfo.setVisibility(GONE);
        }
    }

    private OnChoiceViewClickListener onChoiceViewClickListener;
    public interface OnChoiceViewClickListener {
        void onChoiceViewCount(int count);
    }
    public void setOnChoiceViewClickListener(OnChoiceViewClickListener onChoiceViewClickListener) {
        this.onChoiceViewClickListener = onChoiceViewClickListener;
    }
}

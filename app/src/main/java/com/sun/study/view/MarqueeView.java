package com.sun.study.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.sun.study.R;
import com.sun.study.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunfusheng on 16/5/31.
 */
public class MarqueeView extends ViewFlipper {

    private Context mContext;
    private List<String> notices;

    private int interval = 2000;
    private int animDuration = 500;
    private int textSize = 14;
    private int textColor = 0xffffffff;

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        if (notices == null) {
            notices = new ArrayList<>();
        }

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MarqueeViewStyle, defStyleAttr, 0);
        interval = typedArray.getInteger(R.styleable.MarqueeViewStyle_mvInterval, interval);
        animDuration = typedArray.getInteger(R.styleable.MarqueeViewStyle_mvAnimDuration, animDuration);
        textSize = (int) typedArray.getDimension(R.styleable.MarqueeViewStyle_mvTextSize, textSize);
        textColor = typedArray.getColor(R.styleable.MarqueeViewStyle_mvTextColor, textColor);
        typedArray.recycle();

        setFlipInterval(interval);

        Animation animIn = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_in);
        animIn.setDuration(animDuration);
        setInAnimation(animIn);

        Animation animOut = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_out);
        animOut.setDuration(animDuration);
        setOutAnimation(animOut);
    }

    public void startWithText(final String notice) {
        if (TextUtils.isEmpty(notice)) return;
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeGlobalOnLayoutListener(this);
                startWithFixedWidth(notice, getWidth());
            }
        });
    }

    public void startWithList(List<String> notices) {
        setNotices(notices);
        start();
    }

    public boolean start() {
        if (notices == null || notices.size() == 0) return false;
        removeAllViews();
        for (String notice:notices) {
            addView(createTextView(notice));
        }
        if (notices.size() > 1) {
            startFlipping();
        }
        return true;
    }

    private void startWithFixedWidth(String notice, int width) {
        int noticeLength = notice.length();
        int dpW = DisplayUtil.px2dip(mContext, width);
        int limit = dpW/textSize;
        if (dpW == 0) {
            throw new RuntimeException("Please set MarqueeView width");
        }

        Log.d("--->", "width: "+width+" dpW: "+dpW+" limit: "+limit);

        if (noticeLength <= limit) {
            notices.add(notice);
        } else {
            int size = noticeLength/limit + (noticeLength%limit != 0? 1:0);
            Log.d("--->", "noticeLength: "+noticeLength+" limit: "+limit+" size: "+size);
            for (int i=0; i<size; i++) {
                int startIndex = i*limit;
                int endIndex = ((i+1)*limit >= noticeLength? noticeLength:(i+1)*limit);
                String temp = notice.substring(startIndex, endIndex);
                Log.d("--->", "startIndex: "+startIndex+" endIndex: "+endIndex+" temp: "+temp);
                notices.add(temp);
            }
        }
        start();
    }

    private TextView createTextView(String text) {
        TextView tv = new TextView(mContext);
        tv.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
        tv.setText(text);
        tv.setTextColor(textColor);
        tv.setTextSize(textSize);
        return tv;
    }

    public List<String> getNotices() {
        return notices;
    }

    public void setNotices(List<String> notices) {
        this.notices = notices;
    }

    private AnimationSet animMarqueeViewIn() {
        AnimationSet set = new AnimationSet(true);

        TranslateAnimation translateAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        AlphaAnimation alphaAnim = new AlphaAnimation(0f, 1f);

        set.addAnimation(translateAnim);
        set.addAnimation(alphaAnim);
        set.setDuration(animDuration);
        return set;
    }

    private AnimationSet animMarqueeViewOut() {
        AnimationSet set = new AnimationSet(true);

        TranslateAnimation translateAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        AlphaAnimation alphaAnim = new AlphaAnimation(1f, 0f);

        set.addAnimation(translateAnim);
        set.addAnimation(alphaAnim);
        set.setDuration(animDuration);
        return set;
    }

}

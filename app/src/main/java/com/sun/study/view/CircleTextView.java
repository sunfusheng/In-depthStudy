package com.sun.study.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.sun.study.R;

/**
 * Created by sunfusheng on 2017/5/18.
 */
public class CircleTextView extends View {

    private float circleWidth = 2;
    private float circleRadius = 50;
    private int circleColor = 0x9e9e9e;
    private float textSize = 16;
    private int textColor = 0x9e9e9e;
    private String text = "";

    private Paint mCirclePaint;
    private Paint mTextPaint;

    public CircleTextView(Context context) {
        this(context, null);
    }

    public CircleTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
        initMinimumWidthHeight();
        initPaints(context);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircleTextViewStyle, defStyleAttr, 0);
        circleWidth = typedArray.getDimension(R.styleable.CircleTextViewStyle_ctv_circle_width, circleWidth);
        circleRadius = typedArray.getDimension(R.styleable.CircleTextViewStyle_ctv_circle_radius, circleRadius);
        circleColor = typedArray.getColor(R.styleable.CircleTextViewStyle_ctv_circle_color, circleColor);
        textSize = typedArray.getDimension(R.styleable.CircleTextViewStyle_ctv_text_size, textSize);
        textColor = typedArray.getColor(R.styleable.CircleTextViewStyle_ctv_text_color, textColor);
        text = typedArray.getString(R.styleable.CircleTextViewStyle_ctv_text);
        typedArray.recycle();
    }

    private void initPaints(Context context) {
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setColor(circleColor);
        mCirclePaint.setStrokeWidth(circleWidth);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(textColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    private void initMinimumWidthHeight() {
        setMinimumWidth((int) ((circleRadius + circleWidth) * 2));
        setMinimumHeight((int) ((circleRadius + circleWidth) * 2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(circleRadius + circleWidth, circleRadius + circleWidth, circleRadius, mCirclePaint);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float baseline = ((circleRadius + circleWidth) * 2 - fontMetrics.bottom - fontMetrics.top) / 2;
        canvas.drawText(text, circleRadius + circleWidth, baseline, mTextPaint);
    }

    public void setCircleWidth(float circleWidth) {
        this.circleWidth = circleWidth;
        mCirclePaint.setStrokeWidth(circleWidth);
        initMinimumWidthHeight();
        invalidate();
    }

    public void setCircleRadius(float circleRadius) {
        this.circleRadius = circleRadius;
        initMinimumWidthHeight();
        invalidate();
    }

    public void setCircleColor(@ColorInt int circleColor) {
        this.circleColor = circleColor;
        mCirclePaint.setColor(circleColor);
        invalidate();
    }

    public void setColor(@ColorInt int color) {
        this.circleColor = color;
        this.textColor = color;
        mCirclePaint.setColor(color);
        mTextPaint.setColor(color);
        invalidate();
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        mTextPaint.setTextSize(textSize);
        invalidate();
    }

    public void setTextColor(@ColorInt int textColor) {
        this.textColor = textColor;
        mTextPaint.setColor(textColor);
        invalidate();
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }
}

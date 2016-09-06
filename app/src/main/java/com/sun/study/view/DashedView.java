package com.sun.study.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

import com.sun.study.R;

/**
 * Created by sunfusheng on 16/9/5.
 */
public class DashedView extends View {

    private int dashColor = 0x9e9e9e;
    private int dashWidth = 5;
    private int dashGap = 5;

    private Paint mPaint;

    public DashedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.DashedView, 0, 0);
        dashColor = typedArray.getColor(R.styleable.DashedView_dashColor, dashColor);
        dashWidth = (int) typedArray.getDimension(R.styleable.DashedView_dashWidth, dashWidth);
        dashGap = (int) typedArray.getDimension(R.styleable.DashedView_dashGap, dashGap);
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(dashColor);
        mPaint.setStrokeWidth(dashWidth);
        /**
         * DashPathEffect第一个参数是数组，数组长度必须 >=2，数组元素的值定义了宽度，比如 {20, 15, 10, 5}。
         * 意思是第一个实线段长20，它后面的空白长15，然后又是一个长10的实线段，然后是长度5的空白。
         */
        PathEffect effect = new DashPathEffect(new float[]{dashWidth, dashGap}, 1);
        mPaint.setPathEffect(effect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(getWidth(), 0);
        canvas.drawPath(path, mPaint);
    }
}

package com.sun.study.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.sun.study.R;
import com.sun.study.constant.GlobalParams;
import com.sun.study.model.PointEntity;
import com.sun.study.util.DisplayUtil;

/**
 * Created by sunfusheng on 2016/11/10.
 */

public class QuadraticBezier extends View {

    private Paint mPaintPoint;
    private Paint mPaintBezier;
    private Paint mPaintAuxiliary;
    private Paint mPaintAuxiliaryText;

    private PointEntity mStartPoint;
    private PointEntity mEndPoint;
    private PointEntity mControlPoint;

    private Path mPath = new Path();

    public QuadraticBezier(Context context) {
        super(context);
        init(context);
    }

    public QuadraticBezier(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public QuadraticBezier(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPaintPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintPoint.setStyle(Paint.Style.STROKE);
        mPaintPoint.setStrokeWidth(4);
        mPaintPoint.setColor(context.getResources().getColor(R.color.green));

        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStyle(Paint.Style.STROKE);
        mPaintBezier.setStrokeWidth(8);
        mPaintBezier.setColor(context.getResources().getColor(R.color.colorPrimary));

        mPaintAuxiliary = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliary.setStyle(Paint.Style.STROKE);
        mPaintAuxiliary.setStrokeWidth(4);
        mPaintAuxiliary.setColor(context.getResources().getColor(R.color.font_black_4));

        mPaintAuxiliaryText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliaryText.setStyle(Paint.Style.STROKE);
        mPaintAuxiliaryText.setTextSize(DisplayUtil.dip2px(context, 12));
        mPaintAuxiliaryText.setColor(context.getResources().getColor(R.color.font_black_4));

        mStartPoint = new PointEntity(GlobalParams.SCREEN_WIDTH / 4, GlobalParams.SCREEN_HEIGHT / 2);
        mEndPoint = new PointEntity(GlobalParams.SCREEN_WIDTH * 3 / 4, GlobalParams.SCREEN_HEIGHT / 2);
        mControlPoint = new PointEntity(GlobalParams.SCREEN_WIDTH / 4, GlobalParams.SCREEN_HEIGHT / 4);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();

        canvas.drawCircle(mStartPoint.x, mStartPoint.y, 8, mPaintPoint);
        canvas.drawCircle(mEndPoint.x, mEndPoint.y, 8, mPaintPoint);
        canvas.drawCircle(mControlPoint.x, mControlPoint.y, 8, mPaintPoint);

        canvas.drawText("起始点", mStartPoint.x, mStartPoint.y, mPaintAuxiliaryText);
        canvas.drawText("终止点", mEndPoint.x, mEndPoint.y, mPaintAuxiliaryText);
        canvas.drawText("控制点", mControlPoint.x, mControlPoint.y, mPaintAuxiliaryText);

        canvas.drawLine(mStartPoint.x, mStartPoint.y, mControlPoint.x, mControlPoint.y, mPaintAuxiliary);
        canvas.drawLine(mEndPoint.x, mEndPoint.y, mControlPoint.x, mControlPoint.y, mPaintAuxiliary);

        mPath.moveTo(mStartPoint.x, mStartPoint.y);
        mPath.quadTo(mControlPoint.x, mControlPoint.y, mEndPoint.x, mEndPoint.y);
        canvas.drawPath(mPath, mPaintBezier);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mControlPoint.x = event.getX();
                mControlPoint.y = event.getY();
                invalidate();
                break;
        }
        return true;
    }
}

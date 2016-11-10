package com.sun.study.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
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

    private static final int REGION_WIDTH = 50;

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
        // 起始点、终止点、控制点画笔
        mPaintPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintPoint.setStyle(Paint.Style.STROKE);
        mPaintPoint.setStrokeWidth(4);
        mPaintPoint.setColor(context.getResources().getColor(R.color.green));

        // 贝赛尔曲线画笔
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStyle(Paint.Style.STROKE);
        mPaintBezier.setStrokeWidth(8);
        mPaintBezier.setColor(context.getResources().getColor(R.color.colorPrimary));

        // 辅助线画笔
        mPaintAuxiliary = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliary.setStyle(Paint.Style.STROKE);
        mPaintAuxiliary.setStrokeWidth(4);
        mPaintAuxiliary.setColor(context.getResources().getColor(R.color.font_black_4));

        // 辅助文字画笔
        mPaintAuxiliaryText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliaryText.setStyle(Paint.Style.STROKE);
        mPaintAuxiliaryText.setTextSize(DisplayUtil.dip2px(context, 12));
        mPaintAuxiliaryText.setColor(context.getResources().getColor(R.color.font_black_4));

        // 起始点、终止点、控制点坐标
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

    private boolean touchFlag = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchFlag = isTouchPointedRegion(mControlPoint, event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (touchFlag) {
                    mControlPoint.x = event.getX();
                    mControlPoint.y = event.getY();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                touchFlag = false;
                break;
        }
        return true;
    }

    // 是否触摸到相应的点
    private boolean isTouchPointedRegion(PointEntity point, float touchX, float touchY) {
        RectF rectF = new RectF();
        rectF.set(point.x - REGION_WIDTH, point.y - REGION_WIDTH, point.x + REGION_WIDTH, point.y + REGION_WIDTH);
        if (rectF.contains(touchX, touchY)) {
            return true;
        }
        return false;
    }
}

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunfusheng on 2016/11/10.
 */

public class CubicBezier extends View {

    private Paint mPaintPoint; // 起始点、终止点、控制点画笔
    private Paint mPaintBezier; // 贝赛尔曲线画笔
    private Paint mPaintAuxiliary; // 辅助线画笔
    private Paint mPaintAuxiliaryText; // 起始点、终止点、控制点坐标

    private PointEntity mStartPoint; // 起始点
    private PointEntity mEndPoint; // 终止点
    private PointEntity mControlPoint1; // 控制点1
    private PointEntity mControlPoint2; // 控制点2

    private Path mPath = new Path();

    private static final int TOUCH_REGION = 50; // 触摸的区域半径，为判断是触摸哪个点
    private List<PointEntity> mPointList = new ArrayList<>();
    private PointEntity curPoint;

    public CubicBezier(Context context) {
        super(context);
        init(context);
    }

    public CubicBezier(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CubicBezier(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    // 初始化
    private void init(Context context) {
        // 初始化起始点、终止点、控制点画笔
        mPaintPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintPoint.setStyle(Paint.Style.STROKE);
        mPaintPoint.setStrokeWidth(4);
        mPaintPoint.setColor(context.getResources().getColor(R.color.green));

        // 初始化贝赛尔曲线画笔
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStyle(Paint.Style.STROKE);
        mPaintBezier.setStrokeWidth(8);
        mPaintBezier.setColor(context.getResources().getColor(R.color.colorPrimary));

        // 初始化辅助线画笔
        mPaintAuxiliary = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliary.setStyle(Paint.Style.STROKE);
        mPaintAuxiliary.setStrokeWidth(4);
        mPaintAuxiliary.setColor(context.getResources().getColor(R.color.font_black_4));

        // 初始化辅助文字画笔
        mPaintAuxiliaryText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintAuxiliaryText.setStyle(Paint.Style.STROKE);
        mPaintAuxiliaryText.setTextSize(DisplayUtil.dip2px(context, 12));
        mPaintAuxiliaryText.setColor(context.getResources().getColor(R.color.font_black_4));

        // 初始化起始点、终止点、控制点坐标
        mStartPoint = new PointEntity(GlobalParams.SCREEN_WIDTH / 4, GlobalParams.SCREEN_HEIGHT / 2);
        mEndPoint = new PointEntity(GlobalParams.SCREEN_WIDTH * 3 / 4, GlobalParams.SCREEN_HEIGHT / 2);
        mControlPoint1 = new PointEntity(GlobalParams.SCREEN_WIDTH / 4, GlobalParams.SCREEN_HEIGHT / 4);
        mControlPoint2 = new PointEntity(GlobalParams.SCREEN_WIDTH * 3 / 4, GlobalParams.SCREEN_HEIGHT / 4);

        // 将4个点加到列表中
        mPointList.add(mStartPoint);
        mPointList.add(mEndPoint);
        mPointList.add(mControlPoint1);
        mPointList.add(mControlPoint2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();

        // 绘制所有连接点
        for (PointEntity point:mPointList) {
            canvas.drawCircle(point.x, point.y, 8, mPaintPoint);
        }

        // 绘制辅助文字
        canvas.drawText("起始点", mStartPoint.x, mStartPoint.y, mPaintAuxiliaryText);
        canvas.drawText("终止点", mEndPoint.x, mEndPoint.y, mPaintAuxiliaryText);
        canvas.drawText("控制点1", mControlPoint1.x, mControlPoint1.y, mPaintAuxiliaryText);
        canvas.drawText("控制点2", mControlPoint2.x, mControlPoint2.y, mPaintAuxiliaryText);

        // 绘制辅助线
        canvas.drawLine(mStartPoint.x, mStartPoint.y, mControlPoint1.x, mControlPoint1.y, mPaintAuxiliary);
        canvas.drawLine(mEndPoint.x, mEndPoint.y, mControlPoint1.x, mControlPoint1.y, mPaintAuxiliary);
        canvas.drawLine(mStartPoint.x, mStartPoint.y, mControlPoint2.x, mControlPoint2.y, mPaintAuxiliary);
        canvas.drawLine(mEndPoint.x, mEndPoint.y, mControlPoint2.x, mControlPoint2.y, mPaintAuxiliary);

        // 绘制三阶贝赛尔曲线
        mPath.moveTo(mStartPoint.x, mStartPoint.y);
        mPath.cubicTo(mControlPoint1.x, mControlPoint1.y, mControlPoint2.x, mControlPoint2.y, mEndPoint.x, mEndPoint.y);
        canvas.drawPath(mPath, mPaintBezier);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                curPoint = getCurrentTouchPoint(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                if (curPoint != null) {
                    curPoint.x = event.getX();
                    curPoint.y = event.getY();
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                curPoint = null;
                break;
        }
        return true;
    }

    // 获得当前触摸的点
    private PointEntity getCurrentTouchPoint(float touchX, float touchY) {
        for (PointEntity point:mPointList) {
            if (isTouchPointedRegion(point, touchX, touchY)) {
                return point;
            }
        }
        return null;
    }

    // 判断该点是否被触摸
    private boolean isTouchPointedRegion(PointEntity point, float touchX, float touchY) {
        RectF rectF = new RectF();
        rectF.set(point.x - TOUCH_REGION, point.y - TOUCH_REGION, point.x + TOUCH_REGION, point.y + TOUCH_REGION);
        if (rectF.contains(touchX, touchY)) {
            return true;
        }
        return false;
    }
}

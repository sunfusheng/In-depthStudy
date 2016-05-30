package com.sun.study.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.sun.study.util.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunfusheng on 16/5/30.
 */
public class VerticalScrollTextView extends TextView {

    private float step = 0f;
    private Paint mPaint;
    private String text;
    private float width;
    private List<String> textList = new ArrayList<String>();    //分行保存textview的显示信息。

    public VerticalScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public VerticalScrollTextView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        text = "好雨知时节, 当春乃发生。随风潜入夜, 润物细无声。野径云俱黑, 江船火独明。晓看红湿处, 花重锦官城。";

        mPaint = new Paint();

        mPaint = new TextPaint();
        mPaint.setColor(Color.parseColor("#FFFFFF"));
        mPaint.setTextSize(DisplayUtil.sp2px(context, 14));
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY) {
            throw new IllegalStateException("ScrollLayout only canmCurScreen run at EXACTLY mode!");
        }

        float length = 0;
        if (text == null || text.length() == 0) {
            return;
        }

        //下面的代码是根据宽度和字体大小，来计算textview显示的行数。

        textList.clear();

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            Log.e("textviewscroll", "" + i + text.charAt(i));
            if (length < width) {
                builder.append(text.charAt(i));
                length += mPaint.measureText(text.substring(i, i + 1));
                if (i == text.length() - 1) {
                    Log.e("textviewscroll", "" + i + text.charAt(i));
                    textList.add(builder.toString());
                }
            } else {
                textList.add(builder.toString().substring(0, builder.toString().length() - 1));
                builder.delete(0, builder.length() - 1);
                length = mPaint.measureText(text.substring(i, i + 1));
                i--;
            }

        }
    }


    //下面代码是利用上面计算的显示行数，将文字画在画布上，实时更新。
    @Override
    public void onDraw(Canvas canvas) {
        if (textList.size() == 0) return;
        for (int i = 0; i < textList.size(); i++) {
            canvas.drawText(textList.get(i), 0, this.getHeight() + (i + 1) * mPaint.getTextSize() - step, getPaint());
        }

        invalidate();
        step = step + 1f;
        if (step >= this.getHeight() + textList.size() * mPaint.getTextSize()) {
            step = 0;
        }
    }
}



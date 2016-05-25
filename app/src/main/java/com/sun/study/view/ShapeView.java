package com.sun.study.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.sun.study.R;

/**
 * Created by sunfusheng on 16/5/24.
 */
public class ShapeView extends ImageView {

    private Context mContext;

    private enum Shape {rect, oval, line, ring}

    private int viewWidth = 50;
    private int viewHeight = 50;
    private int viewColor = 0x9e9e9e;
    private Shape viewShape = Shape.rect;
    private int lineWidth = 1;
    private int lineDashWidth = 6;
    private int lineDashGap = 3;

    public ShapeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public ShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ShapeViewStyle, defStyleAttr, 0);

        viewWidth = (int) typedArray.getDimension(R.styleable.ShapeViewStyle_viewWidth, viewWidth);
        viewHeight = (int) typedArray.getDimension(R.styleable.ShapeViewStyle_viewHeight, viewHeight);
        viewColor = typedArray.getColor(R.styleable.ShapeViewStyle_viewColor, viewColor);

        lineWidth = (int) typedArray.getDimension(R.styleable.ShapeViewStyle_lineWidth, lineWidth);
        lineDashWidth = (int) typedArray.getDimension(R.styleable.ShapeViewStyle_lineDashWidth, lineDashWidth);
        lineDashGap = (int) typedArray.getDimension(R.styleable.ShapeViewStyle_lineDashGap, lineDashGap);

        int shape = typedArray.getInt(R.styleable.ShapeViewStyle_viewShape, Shape.rect.ordinal());
        for (Shape shapeValue:Shape.values()) {
            if (shapeValue.ordinal() == shape) {
                viewShape = shapeValue;
                break;
            }
        }

        setImageDrawable(createShapeView());
    }

    private LayerDrawable createShapeView() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(viewColor);
        gradientDrawable.setSize(viewWidth, viewHeight);

        switch (viewShape) {
            case rect:
                gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                break;
            case oval:
                gradientDrawable.setShape(GradientDrawable.OVAL);
                break;
            case line:
                gradientDrawable.setShape(GradientDrawable.LINE);
                gradientDrawable.setStroke(lineWidth, viewColor, lineDashWidth, lineDashGap);
                break;
            case ring:
                gradientDrawable.setShape(GradientDrawable.RING);
                gradientDrawable.setStroke(viewWidth, viewColor);
                break;
        }
        return new LayerDrawable(new Drawable[]{gradientDrawable});
    }
}

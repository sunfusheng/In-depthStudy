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

    private enum Shape {
        rect, oval, line, ring
    }

    private int viewWidth = 50;
    private int viewHeight = 50;
    private int viewColor = 0x9e9e9e;
    private int viewShape = Shape.rect.ordinal();

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

        String width = "";
        String height = "";
        for (int i=0; i<attrs.getAttributeCount(); i++) {
            if ("layout_width".equals(attrs.getAttributeName(i))) {
                width = attrs.getAttributeValue(i);
            } else if ("layout_height".equals(attrs.getAttributeName(i))) {
                height = attrs.getAttributeValue(i);
            }
        }
        viewColor = typedArray.getColor(R.styleable.ShapeViewStyle_viewColor, viewColor);
        int shape = typedArray.getInt(R.styleable.ShapeViewStyle_viewShape, Shape.rect.ordinal());
        for (Shape shapeValue:Shape.values()) {
            if (shapeValue.ordinal() == shape) {
                viewShape = shape;
                break;
            }
        }

//        switch (viewShape) {
//            case rect:
//
//                break;
//        }

        setImageDrawable(createShapeView());
    }

    private LayerDrawable createShapeView() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(viewShape);
        gradientDrawable.setColor(getResources().getColor(R.color.orange));
        gradientDrawable.setSize(50, 50);
        return new LayerDrawable(new Drawable[]{gradientDrawable});
    }
}

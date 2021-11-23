package com.ylz.ylzfloatingactionbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;



public class YlzFloatingActionButton extends FloatingActionButton {

    String name1="";
    String name2="";


    public YlzFloatingActionButton(@NonNull Context context) {
        this(context, null);
    }

    public YlzFloatingActionButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.YlzFloatingActionButton);
        name1 = a.getString(R.styleable.YlzFloatingActionButton_name1);
        name2 = a.getString(R.styleable.YlzFloatingActionButton_name2);
    }

    public YlzFloatingActionButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.YlzFloatingActionButton);
        name1 = a.getString(R.styleable.YlzFloatingActionButton_name1);
        name2 = a.getString(R.styleable.YlzFloatingActionButton_name2);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float drawDistance = (float) (getHeight() * 0.6);
        float x = (float) (getWidth() * 0.25);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setTextSize(36);
        canvas.drawText(name1, x-7, drawDistance-10, paint);
        canvas.drawText(name2, x-7, drawDistance + 26, paint);
    }
}

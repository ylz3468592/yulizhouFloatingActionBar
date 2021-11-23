package com.ylz.ylzfloatingactionbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import java.util.ArrayList;

public class FloatingDraftButton extends FloatingActionButton implements View.OnTouchListener {
    int lastX, lastY;
    int originX, originY;
    int screenWidth;
    int screenHeight;
    private ArrayList<FloatingActionButton> floatingActionButtons = new ArrayList<>();
    String name1="";
    String name2="";

    public FloatingDraftButton(Context context) {
        this(context, null);
    }


    public FloatingDraftButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public FloatingDraftButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, (AttributeSet) attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FloatingDraftButton);
        name1 = a.getString(R.styleable.FloatingDraftButton_name3);
        name2 = a.getString(R.styleable.FloatingDraftButton_name4);
        screenWidth = ScreenUtil.getScreenWidthPixels(context);
        screenHeight = ScreenUtil.getScreenHeightPixels(context);
        setOnTouchListener(this);
    }

    public void registerButton(FloatingActionButton floatingActionButton) {
        floatingActionButtons.add(floatingActionButton);
    }


    public ArrayList<FloatingActionButton> getButtons() {
        return floatingActionButtons;
    }

    public int getButtonSize() {
        return floatingActionButtons.size();
    }

    public boolean isDraftable() {
        for (FloatingActionButton btn : floatingActionButtons) {
            if (btn.getVisibility() == View.VISIBLE) {
                return false;
            }
        }
        return true;
    }

    private void slideButton(int l, int t, int r, int b) {
        for (FloatingActionButton floatingActionButton : floatingActionButtons) {
            floatingActionButton.layout(l, t, r, b);
        }
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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!isDraftable()) {
            return false;
        }
        int ea = event.getAction();
        switch (ea) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                originX = lastX;
                originY = lastY;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                int l = v.getLeft() + dx;
                int b = v.getBottom() + dy;
                int r = v.getRight() + dx;
                int t = v.getTop() + dy;
                if (l < 0) {
                    l = 0;
                    r = l + v.getWidth();
                }
                if (t < 0) {
                    t = 0;
                    b = t + v.getHeight();
                }
                if (r > screenWidth) {
                    r = screenWidth;
                    l = r - v.getWidth();
                }
                if (b > screenHeight) {
                    b = screenHeight;
                    t = b - v.getHeight();
                }
                v.layout(l, t, r, b);
                slideButton(l, t, r, b);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                v.postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                int distance = (int) event.getRawX() - originX + (int) event.getRawY() - originY;
                if (Math.abs(distance) < 20) {

                } else {
                    return true;
                }
                break;
        }
        return false;
    }

}

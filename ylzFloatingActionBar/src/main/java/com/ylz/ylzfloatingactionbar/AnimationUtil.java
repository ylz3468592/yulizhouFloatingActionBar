package com.ylz.ylzfloatingactionbar;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

import java.util.ArrayList;
import java.util.Random;

public class AnimationUtil {
    public static void slideview(final View view, final float p1, final float p2, final float p3, final float p4,
                                 long durationMillis, long delayMillis,
                                 final boolean startVisible, final boolean endVisible) {
        if (view.getTag() != null && "-1".equals(view.getTag().toString())) {
            return;
        }
        TranslateAnimation animation = new TranslateAnimation(p1, p2, p3, p4);
        animation.setDuration(durationMillis);
        animation.setStartOffset(delayMillis);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (startVisible) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.INVISIBLE);
                }
                view.setTag(-1);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
                int left = view.getLeft() + (int) (p2 - p1);
                int top = view.getTop() + (int) (p4 - p3);
                int width = view.getWidth();
                int height = view.getHeight();
                view.layout(left, top, left + width, top + height);
                if (endVisible) {
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.INVISIBLE);
                }
                view.setTag(1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if (endVisible) {
            view.startAnimation(animation);
        } else {
            AnimationSet animationSet = new AnimationSet(true);
            animationSet.setDuration(durationMillis);
            animationSet.setStartOffset(delayMillis);
            animationSet.addAnimation(animation);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0F, 0.0F);
            animationSet.addAnimation(alphaAnimation);
            view.startAnimation(animationSet);
        }
    }

    public static void slideButtons(Context context, final FloatingDraftButton button) {
        int size = button.getButtonSize();
        if (size == 0) {
            return;
        }
        int buttonLeft = button.getLeft();
        int screenWidth = ScreenUtil.getScreenWidthPixels(context);
        int buttonRight = screenWidth - button.getRight();
        int buttonTop = button.getTop();
        int buttonBottom = ScreenUtil.getScreenHeightPixels(context) - button.getBottom();
        int buttonWidth = button.getWidth();
        int radius = 7 * buttonWidth / 4;
        int gap = 5 * buttonWidth / 4;
        ArrayList<FloatingActionButton> buttons = button.getButtons();
        if (button.isDraftable()) {
            showRotateAnimation(button, 0, 360);
            if (buttonLeft >= radius && buttonRight >= radius
                    && buttonTop >= radius && buttonBottom >= radius
            ) {
                double angle = 360.0 / size;
                int randomDegree = new Random().nextInt(180);
                for (int i = 0; i < size; i++) {
                    FloatingActionButton faButton = buttons.get(i);
                    slideview(faButton, 0, radius * (float) Math.cos(Math.toRadians(randomDegree + angle * i)), 0, radius * (float) Math.sin(Math.toRadians(randomDegree + angle * i)), 500, 0, true, true);
                }
            } else if (size * gap < screenWidth && (buttonTop > 3 * buttonBottom || buttonBottom > 3 * buttonTop)) {
                int leftNumber = buttonLeft / gap;
                int rightNumber = buttonRight / gap;
                if (buttonTop >= radius && buttonBottom >= radius) {
                    if (buttonTop > buttonBottom) {
                        FloatingActionButton fabutton = buttons.get(0);
                        slideview(fabutton, 0, 0, 0, -radius, 500, 0, true, true);
                        for (int i = 1; i < leftNumber && i < size; i++) {
                            fabutton = buttons.get(i);
                            slideview(fabutton, 0, -gap * i, 0, -radius, 500, 0, true, true);
                        }
                        for (int i = 1; i < rightNumber && i < size - leftNumber; i++) {
                            fabutton = buttons.get(i + leftNumber);
                            slideview(fabutton, 0, gap * i, 0, -radius, 500, 0, true, true);
                        }
                    } else {
                        FloatingActionButton fabutton = buttons.get(0);
                        slideview(fabutton, 0, 0, 0, radius, 500, 0, true, true);
                        for (int i = 1; i < leftNumber && i < size; i++) {
                            fabutton = buttons.get(i);
                            slideview(fabutton, 0, -gap * i, 0, radius, 500, 0, true, true);
                        }
                        for (int i = 1; i < rightNumber && i < size - leftNumber; i++) {
                            fabutton = buttons.get(i + leftNumber);
                            slideview(fabutton, 0, gap * i, 0, radius, 500, 0, true, true);
                        }
                    }
                } else if (buttonTop >= radius) {
                    FloatingActionButton fabutton = buttons.get(0);
                    slideview(fabutton, 0, 0, 0, -radius, 500, 0, true, true);
                    for (int i = 1; i <= leftNumber && i < size; i++) {
                        fabutton = buttons.get(i);
                        slideview(fabutton, 0, -gap * i, 0, -radius, 500, 0, true, true);
                    }
                    for (int i = 1; i <= rightNumber && i < size - leftNumber; i++) {
                        fabutton = buttons.get(i + leftNumber);
                        slideview(fabutton, 0, gap * i, 0, -radius, 500, 0, true, true);
                    }


                } else if (buttonBottom >= radius) {
                    FloatingActionButton fabutton = buttons.get(0);
                    slideview(fabutton, 0, 0, 0, radius, 500, 0, true, true);
                    for (int i = 1; i < leftNumber && i < size; i++) {
                        fabutton = buttons.get(i);
                        slideview(fabutton, 0, -gap * i, 0, radius, 500, 0, true, true);
                    }
                    for (int i = 1; i < rightNumber && i < size - leftNumber; i++) {
                        fabutton = buttons.get(i + leftNumber);
                        slideview(fabutton, 0, gap * i, 0, radius, 500, 0, true, true);
                    }
                }
            } else {
                int upNumber = buttonTop / gap;
                int belowNumber = buttonBottom / gap;
                if ((upNumber + belowNumber + 1) > size) {
                    upNumber = upNumber * (size - 1) / (upNumber + belowNumber);
                    belowNumber = size - 1 - upNumber;
                    if (buttonLeft >= radius) {
                        FloatingActionButton fabutton = buttons.get(0);
                        slideview(fabutton, 0, -radius, 0, 0, 500, 0, true, true);
                        for (int i = 1; i <= upNumber && i < size; i++) {
                            fabutton = buttons.get(i);
                            slideview(fabutton, 0, -radius, 0, -gap * i, 500, 0, true, true);
                        }
                        for (int i = 1; i <= belowNumber && i < size - upNumber; i++) {
                            fabutton = buttons.get(i + upNumber);
                            slideview(fabutton, 0, -radius, 0, gap * i, 500, 0, true, true);
                        }
                    } else if (buttonRight >= radius) {
                        FloatingActionButton fabutton = buttons.get(0);
                        slideview(fabutton, 0, radius, 0, 0, 500, 0, true, true);
                        for (int i = 1; i <= upNumber && i < size; i++) {
                            fabutton = buttons.get(i);
                            slideview(fabutton, 0, radius, 0, -gap * i, 500, 0, true, true);
                        }
                        for (int i = 1; i <= belowNumber && i < size - upNumber; i++) {
                            fabutton = buttons.get(i + upNumber);
                            slideview(fabutton, 0, radius, 0, gap * i, 500, 0, true, true);
                        }
                    }
                }

            }
        } else {
            showRotateAnimation(button, 225, 0);
            for (FloatingActionButton fabutton : buttons) {
                int faButtonLeft = fabutton.getLeft();
                int faButtonTop = fabutton.getTop();
                slideview(fabutton, 0, buttonLeft - faButtonLeft, 0, buttonTop - faButtonTop, 500, 0, true, false);
            }
        }

    }

    public static void showRotateAnimation(View mView, int startDegress, int degress) {
        mView.clearAnimation();
        float centerX = mView.getWidth() / 2.0f;
        float centerY = mView.getHeight() / 2.0f;
        RotateAnimation rotateAnimation = new RotateAnimation(startDegress, degress, centerX, centerY);
        rotateAnimation.setDuration(500);
        rotateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        rotateAnimation.setFillAfter(true);
        mView.startAnimation(rotateAnimation);
    }

}

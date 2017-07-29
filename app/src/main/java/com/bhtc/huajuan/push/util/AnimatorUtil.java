package com.bhtc.huajuan.push.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.view.View;
import android.view.ViewAnimationUtils;

/**
 * Created by kouxiongfei on 2017/5/21.
 */

public class AnimatorUtil {

    public static void change(final View view, final AnimatorCallBack callBack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int centerX = view.getWidth() / 2;
            int centerY = view.getHeight() / 2;
            int maxRadius = Math.max(view.getWidth(), view.getHeight());

            if (view.getVisibility() == View.VISIBLE) {
                Animator anim = ViewAnimationUtils.createCircularReveal(view,
                        centerX, centerY, maxRadius, 0);
                anim.setDuration(500);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.setVisibility(View.GONE);
                        if (callBack != null)
                            callBack.callBack();
                    }
                });
                anim.start();
            } else {
                Animator anim = ViewAnimationUtils.createCircularReveal(view,
                        centerX, centerY, 0, maxRadius);
                anim.setDuration(500);
                view.setVisibility(View.VISIBLE);
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (callBack != null)
                            callBack.callBack();
                    }
                });
                anim.start();
            }
        }
    }

    public static void rootViewVisible(final View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                    view.setVisibility(View.GONE);
                    change(view, null);
                }
            });
        }else {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void viewVisible(final View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setVisibility(View.INVISIBLE);
            change(view, null);
        }else {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void rootViewVisible(final View view, final AnimatorCallBack callBack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    v.removeOnLayoutChangeListener(this);
                    view.setVisibility(View.GONE);
                    change(view, callBack);
                }
            });
        }
    }

    public static void rootViewGone(final View view, final AnimatorCallBack callBack) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setVisibility(View.VISIBLE);
            change(view, callBack);
        }else {
            view.setVisibility(View.GONE);
        }
    }

    public static void tranAnimator(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0.0f, 30.0f, 0f);
        animator.setDuration(1000);
        animator.setRepeatMode(ObjectAnimator.RESTART);
        animator.setRepeatCount(-1);
        animator.start();
    }

    public interface AnimatorCallBack {
        void callBack();
    }

    private static AnimatorCallBack mAnimatorCallBack;

    public static void setAnimatorCallBack(AnimatorCallBack animatorCallBack) {
        mAnimatorCallBack = animatorCallBack;
    }
}

package com.example.sawai.coordinatorlayoutexamples.Example4;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.sawai.coordinatorlayoutexamples.R;

/**
 * Created by sawai on 02/10/16.
 */
public class CustomBehavior extends CoordinatorLayout.Behavior<View> {
    private static int DIRECTION_UP = 1;
    private static int DIRECTION_DOWN = -1;

    private int mScrollingDirection;
    private int mScrollTrigger;
    private int mScrollDistance;
    private int mScrollThreshold;

    private ObjectAnimator mAnimator;

    public CustomBehavior() {

    }

    public CustomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(new int[] {R.attr.actionBarSize});
        mScrollThreshold = typedArray.getDimensionPixelSize(0, 0) / 2;
        typedArray.recycle();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        if (dy > 0 && mScrollingDirection != DIRECTION_UP) {
            mScrollingDirection = DIRECTION_UP;
            mScrollDistance = 0;
        } else if (dy < 0 && mScrollingDirection != DIRECTION_DOWN) {
            mScrollingDirection = DIRECTION_DOWN;
            mScrollDistance = 0;
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        mScrollDistance += dyConsumed;

        if (mScrollDistance > mScrollThreshold && mScrollTrigger != DIRECTION_UP) {
            mScrollTrigger = DIRECTION_UP;

            restartAnimator(child, getTargetHideValue(coordinatorLayout, child));

        } else if (mScrollDistance < -mScrollThreshold && mScrollTrigger != DIRECTION_DOWN) {
            mScrollTrigger = DIRECTION_DOWN;

            restartAnimator(child, 0f);
        }
    }

    private void restartAnimator(View target, float value) {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }

        mAnimator = ObjectAnimator.ofFloat(target, View.TRANSLATION_Y, value)
                .setDuration(250);
        mAnimator.start();
    }

    private float getTargetHideValue(ViewGroup parent, View target) {
        if (target instanceof AppBarLayout) {
            return -target.getHeight();
        } else if (target instanceof FloatingActionButton){
            return parent.getHeight() - target.getTop();
        }

        return 0.f;
    }
}

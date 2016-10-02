package com.example.sawai.coordinatorlayoutexamples.Example3;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sawai on 02/10/16.
 */
public class BottomLayoutBehavior extends CoordinatorLayout.Behavior<BottomLayout> {
    private int mDependencyOffSet;
    private int mChildInitialOffSet;

    public BottomLayoutBehavior() {
        super();
    }

    public BottomLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, BottomLayout child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    boolean flag = true;
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, BottomLayout child, View dependency) {
        if (mDependencyOffSet != dependency.getTop()) {
            mDependencyOffSet = dependency.getTop();

            if (flag) {
                flag = false;
                mChildInitialOffSet = child.getTop();
            }

            int x = mChildInitialOffSet - child.getTop() - mDependencyOffSet;
            child.offsetTopAndBottom(x);

            return true;
        }

        return false;
    }
}

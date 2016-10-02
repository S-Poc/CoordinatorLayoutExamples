package com.example.sawai.coordinatorlayoutexamples.Example3;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by sawai on 02/10/16.
 */
@CoordinatorLayout.DefaultBehavior(BottomLayoutBehavior.class)
public class BottomLayout extends FrameLayout {

    public BottomLayout(Context context) {
        super(context);
    }

    public BottomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BottomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}

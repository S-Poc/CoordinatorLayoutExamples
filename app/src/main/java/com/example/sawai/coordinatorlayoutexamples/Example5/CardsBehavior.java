package com.example.sawai.coordinatorlayoutexamples.Example5;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.view.View;

/**
 * Created by sawai on 03/10/16.
 */
public class CardsBehavior extends CoordinatorLayout.Behavior<CardsLayout> {
    private int mInitialOffset;

    private ScrollerCompat mScroller;
    private FlingRunnable mFlingRunnable;

    public CardsBehavior() {
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, CardsLayout child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        final int offset = getChildMeasureOffset(parent, child);
        final int measuredHeight = View.MeasureSpec.getSize(parentHeightMeasureSpec) - heightUsed - offset;
        int childMeasuredSpec = View.MeasureSpec.makeMeasureSpec(measuredHeight, View.MeasureSpec.EXACTLY);
        child.measure(parentWidthMeasureSpec, childMeasuredSpec);

        return true;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, CardsLayout child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);

        CardsLayout previousChild = getPreviousChild(parent, child);
        if (previousChild != null) {
            int offset = previousChild.getTop() + previousChild.getHeaderHeight();
            child.offsetTopAndBottom(offset);
        }

        mInitialOffset = child.getTop();
        return true;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       CardsLayout child, View directTargetChild,
                                       View target, int nestedScrollAxes) {
        //We have to declare interest in the scroll to receive further events
        boolean isVertical = (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        //Only capture on the view currently being scrolled
        return isVertical && child == directTargetChild;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout,
                                  CardsLayout child, View target,
                                  int dx, int dy,
                                  int[] consumed) {
        //When not at the top, consume all scrolling for the card
        if (child.getTop() > mInitialOffset) {
            //Tell the parent what we've consumed
            consumed[1] = scroll(child, dy,
                    mInitialOffset,
                    mInitialOffset + child.getHeight() - child.getHeaderHeight());
            shiftSiblings(coordinatorLayout, child, consumed[1]);
        }
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout,
                               CardsLayout child, View target,
                               int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed) {
        //Use any unconsumed distance to scroll the card layout
        int shift = scroll(child, dyUnconsumed,
                mInitialOffset,
                mInitialOffset + child.getHeight() - child.getHeaderHeight());
        shiftSiblings(coordinatorLayout, child, shift);
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout,
                                    CardsLayout child, View target,
                                    float velocityX, float velocityY) {
        if (child.getTop() > mInitialOffset) {
            return fling(coordinatorLayout,
                    child,
                    mInitialOffset,
                    mInitialOffset + child.getHeight() - child.getHeaderHeight(),
                    -velocityY);
        }

        return false;
    }

    @Override
    public boolean onNestedFling(CoordinatorLayout coordinatorLayout,
                                 CardsLayout child, View target,
                                 float velocityX, float velocityY, boolean consumed) {
        if (!consumed) {
            return fling(coordinatorLayout,
                    child,
                    mInitialOffset,
                    mInitialOffset + child.getHeight() - child.getHeaderHeight(),
                    -velocityY);
        }

        return false;
    }

    private boolean fling(CoordinatorLayout parent, CardsLayout layout,
                          int minOffset, int maxOffset, float velocityY) {
        if (mFlingRunnable != null) {
            layout.removeCallbacks(mFlingRunnable);
        }

        if (mScroller == null) {
            mScroller = ScrollerCompat.create(layout.getContext());
        }

        mScroller.fling(
                0, layout.getTop(), // curr
                0, Math.round(velocityY), // velocity.
                0, 0, // x
                minOffset, maxOffset); // y

        if (mScroller.computeScrollOffset()) {
            mFlingRunnable = new FlingRunnable(parent, layout);
            ViewCompat.postOnAnimation(layout, mFlingRunnable);
            return true;
        } else {
            mFlingRunnable = null;
            return false;
        }
    }

    private int getChildMeasureOffset(CoordinatorLayout parent, View child) {
        int offset = 0;
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if (view != child && view instanceof CardsLayout) {
                offset += ((CardsLayout) view).getHeaderHeight();
            }
        }

        return offset;
    }

    private CardsLayout getPreviousChild(CoordinatorLayout parent, View child) {
        int cardIndex = parent.indexOfChild(child);

        for (int i = cardIndex - 1; i >= 0; i--) {
            View v = parent.getChildAt(i);
            if (v instanceof CardsLayout) {
                return (CardsLayout) v;
            }
        }

        return null;
    }

    private CardsLayout getNextChild(CoordinatorLayout parent, View child) {
        int cardIndex = parent.indexOfChild(child);
        for (int i = cardIndex + 1; i < parent.getChildCount(); i++) {
            View v = parent.getChildAt(i);
            if (v instanceof CardsLayout) {
                return (CardsLayout) v;
            }
        }

        return null;
    }

    private int clamp(int value, int min, int max) {
        if (value > max) {
            return max;
        } else if (value < min) {
            return min;
        } else {
            return value;
        }
    }

    private int scroll(View child, int dy, int minOffset, int maxOffset) {
        final int initialOffset = child.getTop();
        //Clamped new position - initial position = offset change
        int delta = clamp(initialOffset - dy, minOffset, maxOffset) - initialOffset;
        child.offsetTopAndBottom(delta);

        return -delta;
    }

    private int getHeaderOverlap(CardsLayout above, CardsLayout below) {
        return (above.getTop() + above.getHeaderHeight()) - below.getTop();
    }

    private void shiftSiblings(CoordinatorLayout parent, CardsLayout child, int shift) {
        if (shift == 0) return;

        if (shift > 0) {
            //Push siblings up if overlapping
            CardsLayout current = child;
            CardsLayout card = getPreviousChild(parent, current);
            while (card != null) {
                int delta = getHeaderOverlap(card, current);
                if (delta > 0) {
                    card.offsetTopAndBottom(-delta);
                }

                current = card;
                card = getPreviousChild(parent, current);
            }
        } else {
            //Push siblings down if overlapping
            CardsLayout current = child;
            CardsLayout card = getNextChild(parent, current);
            while (card != null) {
                int delta = getHeaderOverlap(current, card);
                if (delta > 0) {
                    card.offsetTopAndBottom(delta);
                }

                current = card;
                card = getNextChild(parent, current);
            }
        }
    }

    private class FlingRunnable implements Runnable {
        private final CoordinatorLayout mParent;
        private final CardsLayout mLayout;

        FlingRunnable(CoordinatorLayout parent, CardsLayout layout) {
            mParent = parent;
            mLayout = layout;
        }

        @Override
        public void run() {
            if (mLayout != null && mScroller != null && mScroller.computeScrollOffset()) {
                int delta = mScroller.getCurrY() - mLayout.getTop();
                mLayout.offsetTopAndBottom(delta);
                shiftSiblings(mParent, mLayout, -delta);

                // Post ourselves so that we run on the next animation
                ViewCompat.postOnAnimation(mLayout, this);
            }
        }
    }
}


package com.example.sawai.coordinatorlayoutexamples.Example5;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.sawai.coordinatorlayoutexamples.R;

/**
 * Created by sawai on 03/10/16.
 */
@CoordinatorLayout.DefaultBehavior(CardsBehavior.class)
public class CardsLayout extends FrameLayout {
    private int mHeaderViewHeight;

    public CardsLayout(Context context) {
        this(context, null);
    }

    public CardsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.card_layout, this);

        RecyclerView list = (RecyclerView) findViewById(R.id.list);
        TextView header = (TextView) findViewById(R.id.header);

        Adapter adapter = new Adapter(list);
        list.setAdapter(adapter);
        list.setLayoutManager(new LinearLayoutManager(getContext()));

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlidingCardLayout, defStyleAttr, 0);
        header.setBackgroundColor(a.getColor(R.styleable.SlidingCardLayout_android_colorBackground, Color.BLACK));
        header.setText(a.getText(R.styleable.SlidingCardLayout_android_text));
        a.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            mHeaderViewHeight = findViewById(R.id.header).getMeasuredHeight();
        }
    }

    public int getHeaderHeight() {
        return mHeaderViewHeight;
    }
}

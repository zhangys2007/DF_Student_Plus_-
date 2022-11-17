package com.example.df.zhiyun.mvp.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class NLinearLayout extends LinearLayout {
    public NLinearLayout(Context context) {
        super(context);
    }

    public NLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NLinearLayout(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}

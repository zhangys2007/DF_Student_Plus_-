package com.example.df.zhiyun.mvp.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

public class BottomBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    public BottomBehavior() {
    }

    public BottomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(@NonNull CoordinatorLayout parent, @NonNull V child, int layoutDirection) {
        parent.onLayoutChild(child, layoutDirection);
        int mParentHeight = parent.getHeight();
        ViewCompat.offsetTopAndBottom(child, mParentHeight-child.getHeight());
        return true;
    }
}

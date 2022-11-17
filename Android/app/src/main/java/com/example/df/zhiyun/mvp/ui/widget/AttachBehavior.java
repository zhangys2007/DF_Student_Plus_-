package com.example.df.zhiyun.mvp.ui.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.example.df.zhiyun.R;

public class AttachBehavior extends CoordinatorLayout.Behavior<View> {
    public AttachBehavior() {
    }

    public AttachBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return child.getId() == R.id.nsv_title  && dependency.getId() == R.id.ll_content_bottom_sheet;
//        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        int top = dependency.getTop();
        android.view.ViewGroup.LayoutParams layoutParams =child.getLayoutParams();
        layoutParams.height = top;
        child.setLayoutParams(layoutParams);
        return true;
    }
}

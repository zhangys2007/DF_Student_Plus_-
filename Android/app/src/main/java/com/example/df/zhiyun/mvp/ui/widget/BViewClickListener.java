package com.example.df.zhiyun.mvp.ui.widget;

import android.view.View;

public abstract class BViewClickListener implements View.OnClickListener{
    protected int mIndex;
    public BViewClickListener(int index) {
        mIndex = index;
    }
}

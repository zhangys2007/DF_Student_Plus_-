package com.example.df.zhiyun.mvp.ui.widget;

import android.widget.EditText;

public abstract class BFocusChangeListener implements EditText.OnFocusChangeListener {
    protected int mIndex;
    public BFocusChangeListener(int index) {
        mIndex = index;
    }
}

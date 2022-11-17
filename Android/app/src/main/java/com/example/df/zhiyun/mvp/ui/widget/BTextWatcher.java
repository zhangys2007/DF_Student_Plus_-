package com.example.df.zhiyun.mvp.ui.widget;

import android.text.TextWatcher;

public abstract class BTextWatcher implements TextWatcher {
    protected int mIndex;
    public BTextWatcher(int index) {
        mIndex = index;
    }
}

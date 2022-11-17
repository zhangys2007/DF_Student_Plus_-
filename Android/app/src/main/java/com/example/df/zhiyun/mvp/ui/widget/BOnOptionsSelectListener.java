package com.example.df.zhiyun.mvp.ui.widget;

import com.bigkoo.pickerview.listener.OnOptionsSelectListener;

public abstract class BOnOptionsSelectListener implements OnOptionsSelectListener {
    protected int mIndex;
    public BOnOptionsSelectListener(int index) {
        mIndex = index;
    }
}

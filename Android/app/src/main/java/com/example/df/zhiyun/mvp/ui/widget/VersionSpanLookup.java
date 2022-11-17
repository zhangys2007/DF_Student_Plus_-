package com.example.df.zhiyun.mvp.ui.widget;

import android.support.v7.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import com.example.df.zhiyun.mvp.ui.adapter.VersionAdapter;

public class VersionSpanLookup implements BaseQuickAdapter.SpanSizeLookup {
    BaseMultiItemQuickAdapter mAdapter;
    public VersionSpanLookup(BaseMultiItemQuickAdapter adapter){
        mAdapter = adapter;
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        MultiItemEntity item = (MultiItemEntity)(mAdapter.getData().get(position));
        return item.getItemType()== VersionAdapter.LEVEL_0?3:1;
    }
}

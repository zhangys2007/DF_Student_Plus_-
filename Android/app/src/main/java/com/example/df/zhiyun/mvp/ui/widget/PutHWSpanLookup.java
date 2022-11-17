package com.example.df.zhiyun.mvp.ui.widget;

import android.support.v7.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.common.mvp.model.entity.CommonExpandableItem;
import com.example.df.zhiyun.educate.mvp.ui.adapter.PutHWClassAdapter;

public class PutHWSpanLookup implements BaseQuickAdapter.SpanSizeLookup {
    BaseMultiItemQuickAdapter mAdapter;
    public PutHWSpanLookup(BaseMultiItemQuickAdapter adapter){
        mAdapter = adapter;
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        CommonExpandableItem item = (CommonExpandableItem)(mAdapter.getData().get(position));
        return item.getLevel() == 0?3:1;
    }
}

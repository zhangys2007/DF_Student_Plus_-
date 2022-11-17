package com.example.df.zhiyun.mvp.ui.widget;

import android.support.v7.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.mvp.model.adapterEntity.CorrectCardMultipleItem;

import java.util.List;

public class CorrectCardSpanLookup implements BaseQuickAdapter.SpanSizeLookup {
    List<CorrectCardMultipleItem> mItems;
    public CorrectCardSpanLookup(List<CorrectCardMultipleItem> items){
        mItems = items;
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        CorrectCardMultipleItem item = mItems.get(position);

        return item.getItemType()==CorrectCardMultipleItem.TYPE_TITLE?10:1;
    }
}

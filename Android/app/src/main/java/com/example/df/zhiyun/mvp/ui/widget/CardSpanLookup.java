package com.example.df.zhiyun.mvp.ui.widget;

import android.support.v7.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.mvp.model.adapterEntity.CardMultipleItem;

import java.util.List;

public class CardSpanLookup implements BaseQuickAdapter.SpanSizeLookup {
    List<CardMultipleItem> mItems;
    public CardSpanLookup(List<CardMultipleItem> items){
        mItems = items;
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        CardMultipleItem item = mItems.get(position);

        return item.getItemType()==CardMultipleItem.TYPE_TITLE?10:1;
    }
}

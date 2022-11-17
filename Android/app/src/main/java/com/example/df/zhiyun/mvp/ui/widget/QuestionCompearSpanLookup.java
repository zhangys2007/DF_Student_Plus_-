package com.example.df.zhiyun.mvp.ui.widget;

import android.support.v7.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.mvp.model.adapterEntity.CorrectCardMultipleItem;
import com.example.df.zhiyun.mvp.model.adapterEntity.QuestionCompearMultipleItem;

import java.util.List;

public class QuestionCompearSpanLookup implements BaseQuickAdapter.SpanSizeLookup {
    List<QuestionCompearMultipleItem> mItems;
    public QuestionCompearSpanLookup(List<QuestionCompearMultipleItem> items){
        mItems = items;
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        QuestionCompearMultipleItem item = mItems.get(position);


        return (item.getItemType()==QuestionCompearMultipleItem.TYPE_TITLE_1 ||
                item.getItemType()==QuestionCompearMultipleItem.TYPE_VALUE_1)?1:2;
    }
}

package com.example.df.zhiyun.preview.mvp.ui.widget;

import android.support.v7.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.common.mvp.model.entity.CommonExpandableItem;

import java.util.List;

/**
 * 班级题目分析页面里的选择题recyclerview   有选项和选择这个选项的学生们, 学生一行最多放5个
 */
public class SelMemberSpanLookup implements BaseQuickAdapter.SpanSizeLookup {
    List<CommonExpandableItem> list;

    public void setData(List<CommonExpandableItem> l){
        this.list = l;
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        CommonExpandableItem item = list.get(position);
        return item.getLevel()== 0?5:1;
    }
}

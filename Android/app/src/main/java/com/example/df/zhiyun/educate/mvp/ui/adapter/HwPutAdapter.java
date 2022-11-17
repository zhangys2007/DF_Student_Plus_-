package com.example.df.zhiyun.educate.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.CompoundDrawableUtil;
import com.example.df.zhiyun.common.mvp.model.entity.CommonExpandableItem;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 布置作业页面的选择某章节下的题目，可收缩展开
 */
public class HwPutAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public HwPutAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(0, R.layout.item_book_category);
        addItemType(1, R.layout.item_book_category);
        addItemType(2, R.layout.item_book_category_end);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        CommonExpandableItem item1 = (CommonExpandableItem)item;
        if(item1.getLevel() != 2){
            renderBranch(helper,item1);
        }else{
            renderLeaf(helper,item1);
        }
    }


    //渲染枝干，就是有下一级的那些层级
    private void renderBranch(BaseViewHolder helper, CommonExpandableItem expandableItem){
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = helper.getAdapterPosition();
                if(expandableItem.isExpanded()){
                    collapse(pos);
                }else{
                    expand(pos);
                }
            }
        });
        TextView tvTitle = helper.getView(R.id.tv_book_title);
        if(expandableItem.getLevel() == 0){
            CompoundDrawableUtil.setCompoundDrawable(mContext,tvTitle
                    ,expandableItem.isExpanded()?R.mipmap.ic_3_grey:R.mipmap.ic_3_grey_h
                    ,0,0,0);
        }else{
            CompoundDrawableUtil.setCompoundDrawable(mContext,tvTitle
                    ,expandableItem.isExpanded()?R.mipmap.ic_up_circle:R.mipmap.ic_down_circle
                    ,0,0,0);
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)tvTitle.getLayoutParams();
        params.setMargins(ArmsUtils.dip2px(mContext,15+15*expandableItem.getLevel()),0,0,0);
        tvTitle.setText("第一章");
    }

    //渲染叶子，就是没下一级的那些层级
    private void renderLeaf(BaseViewHolder helper, CommonExpandableItem info){
        helper.addOnClickListener(R.id.fl_table);
        helper.addOnClickListener(R.id.fl_all);
        helper.addOnClickListener(R.id.fl_sel);
        helper.setText(R.id.tv_cat_end_name,"集合的含义与表示");
        helper.setText(R.id.tv_numb,"题量：20     布置次数：2");
        RatingBar ratingBar = helper.getView(R.id.rb_dif);
        ratingBar.setProgress(2);
    }
}

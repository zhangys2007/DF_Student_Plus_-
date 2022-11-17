package com.example.df.zhiyun.mvp.ui.adapter;

import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.model.adapterEntity.QuestionCompearMultipleItem;

import java.util.List;

/***
 * 班级成绩对比-小题成绩对比
 */
public class QuestionCompearAdapter extends BaseMultiItemQuickAdapter<QuestionCompearMultipleItem, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */

    private int colorBlue = 0;
    private int colorGray = 0;
    public QuestionCompearAdapter(List<QuestionCompearMultipleItem> data) {
        super(data);
        addItemType(QuestionCompearMultipleItem.TYPE_TITLE_1, R.layout.item_tab_name);
        addItemType(QuestionCompearMultipleItem.TYPE_TITLE_2, R.layout.item_tab_value);
        addItemType(QuestionCompearMultipleItem.TYPE_VALUE_1, R.layout.item_tab_name);
        addItemType(QuestionCompearMultipleItem.TYPE_VALUE_2, R.layout.item_tab_value);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuestionCompearMultipleItem item) {
        if(colorBlue == 0){
            colorBlue = ContextCompat.getColor(helper.itemView.getContext(),R.color.blue_bg);
            colorGray = ContextCompat.getColor(helper.itemView.getContext(),R.color.bg_grey);
        }

        switch (helper.getItemViewType()) {
            case QuestionCompearMultipleItem.TYPE_TITLE_1:
                helper.setBackgroundRes(R.id.tv_name,R.drawable.border_recangle_fill_blue_light);
                helper.setText(R.id.tv_name,item.getValue());
                break;
            case QuestionCompearMultipleItem.TYPE_TITLE_2:
                helper.setBackgroundRes(R.id.tv_value,R.drawable.border_recangle_fill_blue_light);
                helper.setText(R.id.tv_value,item.getValue());
                break;
            case QuestionCompearMultipleItem.TYPE_VALUE_1:
                helper.setBackgroundRes(R.id.tv_name,R.drawable.border_recangle_fill_white);
                helper.setText(R.id.tv_name,item.getValue());
                break;
            case QuestionCompearMultipleItem.TYPE_VALUE_2:
                helper.setBackgroundRes(R.id.tv_value,R.drawable.border_recangle_fill_white);
                helper.setText(R.id.tv_value,item.getValue());
                break;
        }
    }
}

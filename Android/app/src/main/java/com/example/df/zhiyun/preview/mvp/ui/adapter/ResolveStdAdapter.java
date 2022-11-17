package com.example.df.zhiyun.preview.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.CompoundDrawableUtil;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.common.mvp.model.entity.CommonExpandableItem;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;

import java.util.List;

/**
 * 班级题目分析页面里的选择题  有选项和选这个选项的学生
 */
public class ResolveStdAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public ResolveStdAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(0, R.layout.item_ques_title);
        addItemType(1, R.layout.item_ques_content);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        if(((CommonExpandableItem)item).getLevel() == 0){
            renderTitle(helper,item);
        }else{
            renderContent(helper,item);
        }
    }

    /**
     * 渲染题干
     * @param item
     */
    private void renderTitle(BaseViewHolder helper, MultiItemEntity item) {
        HtmlTextView htmlTextView = helper.getView(R.id.tv_name);
        RichTextViewHelper.setContent(htmlTextView,"一. 选择题（本大题共5题，每题4分，共20分）");
    }

    /**
     * 渲染题内容
     * @param item
     */
    private void renderContent(BaseViewHolder helper, MultiItemEntity item) {

    }
}

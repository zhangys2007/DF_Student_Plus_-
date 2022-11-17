package com.example.df.zhiyun.analy.mvp.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.mvp.model.adapterEntity.QuestionMultipleItem;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.mvp.model.entity.QuestionOption;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;

import java.util.List;

/**
 * 学业水平报告  错题情况统计adapter
 */
public class HWQuesAdapter extends BaseMultiItemQuickAdapter<QuestionMultipleItem, BaseViewHolder> {
    String strCount;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HWQuesAdapter(List<QuestionMultipleItem> data) {
        super(data);
        addItemType(Api.QUESTION_HEAD, R.layout.item_question_head);
        addItemType(Api.QUESTION_SELECT, R.layout.item_question_select);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuestionMultipleItem item) {
        Question data = item.getData();
        switch (helper.getItemViewType()) {
            case Api.QUESTION_HEAD:
                renderHead(helper,item);
                break;
            default:
                renderSelect(helper,item);
                break;
        }
    }

    private void renderHead(BaseViewHolder helper, QuestionMultipleItem item){
//        if(strCount == null){
//            strCount = helper.itemView.getContext().getString(R.string.total_error_question);
//        }
//        helper.setText(R.id.tv_count, String.format(strCount,item.getData().getCount()));
//        try {
//            helper.setText(R.id.tv_time,TimeUtils.getYmd(Long.parseLong(item.getData().getTime())) );
//        }catch (NumberFormatException e){
//
//        }
    }

    private void renderSelect(BaseViewHolder helper, QuestionMultipleItem item){
//        Question question = item.getData();
//        StringBuilder strContent = new StringBuilder();
//        if(question.getOptionList() != null){
//            for(int index=0 ; index < question.getOptionList().size();index++){
//                QuestionOption questionOption = question.getOptionList().get(index);
//                strContent.append(questionOption.getOption()).append("   ").append(questionOption.getOptionContent());
//                if(index < question.getOptionList().size()-1){
//                    strContent.append('\n');
//                }
//            }
//        }
//
//        helper
//                .setText(R.id.tv_from,"来源："+ (TextUtils.isEmpty(question.getSource())?"":question.getSource()));
//
//        HtmlTextView tvName = (HtmlTextView)helper.getView(R.id.tv_name);
//        RichTextViewHelper.setContentNoP(tvName,question.getContent());
        helper.addOnClickListener(R.id.tv_detail);
    }
}

package com.example.df.zhiyun.correct.mvp.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.model.adapterEntity.CardMultipleItem;
import com.example.df.zhiyun.mvp.model.adapterEntity.CorrectCardMultipleItem;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.Question;

import java.util.ArrayList;
import java.util.List;
/***
 * 批改卡的adapter
 */
public class CorrectCardAdapter extends BaseMultiItemQuickAdapter<CorrectCardMultipleItem, BaseViewHolder> {
    public final static int STATUS_DONE_ALL = 2;
    public final static int STATUS_DONE_PARTE = 1;
    public final static int STATUS_DONE_NONE = 0;

    int mUnDoCount = 0;

    /**
     * 返回为做题目数
     * @return
     */
    public int getUnDoCount(){
        return mUnDoCount;
    }
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public CorrectCardAdapter(List<CorrectCardMultipleItem> data) {
        super(data);
        addItemType(CorrectCardMultipleItem.TYPE_TITLE, R.layout.item_card_title);
        addItemType(CorrectCardMultipleItem.TYPE_INDEX, R.layout.item_card_question);
    }

    @Override
    protected void convert(BaseViewHolder helper, CorrectCardMultipleItem item) {
        switch (helper.getItemViewType()) {
            case CardMultipleItem.TYPE_TITLE:
                renderTitle(helper,item);
                break;
            case CardMultipleItem.TYPE_INDEX:
                renderIndex(helper,item);
                break;
            default:
                renderTitle(helper,item);
                break;
        }
    }

    private void renderTitle(BaseViewHolder helper, CorrectCardMultipleItem item){
        helper.setText(R.id.tv_answer_name,item.getTitle());
    }

    private void renderIndex(BaseViewHolder helper, CorrectCardMultipleItem item){
        helper.setText(R.id.tv_answer_index,item.getTitle());
        Context context =helper.itemView.getContext();

        int status = parseCorrectStatus(item.getmData());
        if(status == STATUS_DONE_ALL){   //全做了
            helper.setBackgroundRes(R.id.tv_answer_index,R.mipmap.full_blue)
                    .setTextColor(R.id.tv_answer_index, ContextCompat.getColor(context,R.color.white));
        }else if(status == STATUS_DONE_PARTE){   //部分做了
            helper.setBackgroundRes(R.id.tv_answer_index,R.mipmap.half_blue)
                    .setTextColor(R.id.tv_answer_index, ContextCompat.getColor(context,R.color.text_666));
            mUnDoCount++;
        }else{   //没做
            helper.setBackgroundRes(R.id.tv_answer_index,R.mipmap.empty_grey)
                    .setTextColor(R.id.tv_answer_index, ContextCompat.getColor(context,R.color.text_666));
            mUnDoCount++;
        }
    }

    private void markeCorrectStatus(Question question, List<Boolean> value){
        if(question.getSubQuestion() != null && question.getSubQuestion().size() > 0){
            for(Question subq: question.getSubQuestion()){
                markeCorrectStatus(subq,value);
            }
        }else{
            if(question.getStudentAnswerStatus() == Api.STUDENT_ANSWER_STATUS_READED){  //已批的默认true
                value.add(true);
            }else{
                value.add(question.isScorreChanged());
            }
        }
    }

    private int parseCorrectStatus(Question question){
        if(question == null){
            return STATUS_DONE_NONE;
        }

        List<Boolean> listStatus = new ArrayList<>();
        markeCorrectStatus(question,listStatus);

        boolean hasCorrectItem = false;
        boolean hasUnCorrectItem = false;

        for(Boolean value: listStatus){
            if(value){
                hasCorrectItem = true;
            }else{
                hasUnCorrectItem = true;
            }
        }

        if(hasCorrectItem && !hasUnCorrectItem){
            return STATUS_DONE_ALL;
        }else if(hasCorrectItem && hasUnCorrectItem){
            return STATUS_DONE_PARTE;
        }else{
            return STATUS_DONE_NONE;
        }
    }

}

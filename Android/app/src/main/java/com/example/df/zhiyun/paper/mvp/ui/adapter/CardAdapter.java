package com.example.df.zhiyun.paper.mvp.ui.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.mvp.model.adapterEntity.CardMultipleItem;

import java.util.ArrayList;
import java.util.List;

import com.example.df.zhiyun.R;

import com.example.df.zhiyun.mvp.model.entity.Answer;

import timber.log.Timber;

/***
 * 答题卡的adapter
 */
public class CardAdapter extends BaseMultiItemQuickAdapter<CardMultipleItem, BaseViewHolder> {
    /**
     * 返回未做题目数
     * @return
     */
    public int getUnDoCount(){
        int count = 0;
        List<CardMultipleItem> listData = getData();
        if(listData != null){
            for(CardMultipleItem item: listData){
                if(item.getItemType() == CardMultipleItem.TYPE_INDEX){
                    List<String> listAnsw = new ArrayList<>();
                    getOptionList(item.getmData(),listAnsw);
                    if(listAnsw == null || listAnsw.size() == 0){
                        count++;
                    }else{
                        boolean valueOption = false;
                        boolean emptyOption = false;

                        for(int i=0;i<listAnsw.size();i++){
                            if(TextUtils.isEmpty(listAnsw.get(i))){
                                emptyOption = true;
                            }else{
                                valueOption = true;
                            }
                        }

                        if(!valueOption){
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    public int getQuestionCount(){
        int count = 0;
        List<CardMultipleItem> listData = getData();
        if(listData != null){
            for(CardMultipleItem item: listData){
                if(item.getItemType() == CardMultipleItem.TYPE_INDEX){
                    count++;
                }
            }
        }
        return count;
    }
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public CardAdapter(List<CardMultipleItem> data) {
        super(data);
        addItemType(CardMultipleItem.TYPE_TITLE, R.layout.item_card_title);
        addItemType(CardMultipleItem.TYPE_INDEX, R.layout.item_card_question);
    }

    @Override
    protected void convert(BaseViewHolder helper, CardMultipleItem item) {
        switch (helper.getItemViewType()) {
            case CardMultipleItem.TYPE_TITLE:
                renderTitle(helper,item);
                break;
            case CardMultipleItem.TYPE_INDEX:
                renderIndex(helper,item);
                break;
            default:
                Timber.tag("CardAdapter").d("===========default");
                renderTitle(helper,item);
                break;
        }
    }

    private void renderTitle(BaseViewHolder helper, CardMultipleItem item){
        helper.setText(R.id.tv_answer_name,item.getTitle());
    }

    private void renderIndex(BaseViewHolder helper, CardMultipleItem item){
        List<String> listAnsw = new ArrayList<>();
        getOptionList(item.getmData(),listAnsw);
        Context context =helper.itemView.getContext();


        if(listAnsw == null || listAnsw.size() == 0){
            helper.setBackgroundRes(R.id.tv_answer_index,R.mipmap.empty_grey)
                    .setTextColor(R.id.tv_answer_index, ContextCompat.getColor(context,R.color.text_666));
        }else{  //多选或多填
            boolean valueOption = false;
            boolean emptyOption = false;

            for(int i=0;i<listAnsw.size();i++){
                if(TextUtils.isEmpty(listAnsw.get(i))){
                    emptyOption = true;
                }else{
                    valueOption = true;
                }
            }


            if(valueOption && !emptyOption){   //全做了
                helper.setBackgroundRes(R.id.tv_answer_index,R.mipmap.full_blue)
                        .setTextColor(R.id.tv_answer_index, ContextCompat.getColor(context,R.color.white));
            }else if(valueOption && emptyOption){   //部分做了
                helper.setBackgroundRes(R.id.tv_answer_index,R.mipmap.half_blue)
                        .setTextColor(R.id.tv_answer_index, ContextCompat.getColor(context,R.color.text_666));
            }else{   //没做
                helper.setBackgroundRes(R.id.tv_answer_index,R.mipmap.empty_grey)
                        .setTextColor(R.id.tv_answer_index, ContextCompat.getColor(context,R.color.text_666));
            }
        }



        helper.setText(R.id.tv_answer_index,item.getTitle());
    }


    private void getOptionList(Answer answer,List<String> optionList){
        if(answer == null){
            return ;
        }


        List<Answer> subAnswerList = answer.getSubAnswer();
        if(subAnswerList !=null){    //有子答案则必无主答案
            for(Answer subAnswer:subAnswerList){
                getOptionList(subAnswer,optionList);
            }
        }else{
            if(answer.getAnswer() != null && answer.getAnswer().size() > 0){
                optionList.addAll(answer.getAnswer());
            }else{
                optionList.add("");
            }
        }
    }
}

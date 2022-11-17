package com.example.df.zhiyun.mvp.model.adapterEntity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.mvp.model.entity.QuestionOption;

/***
 * 做作业 里的复合题型的 选择，输入
 */
public class CpQuestionMultipleItem implements MultiItemEntity {
    public static final int TYPE_TITLE_INPUT = 1;
    public static final int TYPE_TITLE_SELECT = 2;
    public static final int TYPE_OPTION_INPUT = 3;
    public static final int TYPE_OPTION_SELECT = 4;
    public static final int TYPE_OPTION_SIMPLE = 5;
    public static final int TYPE_OPTION_MULTIPLE = 6;
    private int itemType;
    private Question mData;
    private QuestionOption mOption;
    private int optIndex;   // option的选项下标

    public int getOptIndex() {
        return optIndex;
    }

    public void setOptIndex(int optIndex) {
        this.optIndex = optIndex;
    }

    public QuestionOption getOption() {
        return mOption;
    }

    public Question getData(){
        return mData;
    }


    @Override
    public int getItemType() {
        return itemType;
    }

    public CpQuestionMultipleItem(int itemType, Question data, QuestionOption option){
        this.itemType = itemType;
        this.mData = data;
        this.mOption = option;
    }
}

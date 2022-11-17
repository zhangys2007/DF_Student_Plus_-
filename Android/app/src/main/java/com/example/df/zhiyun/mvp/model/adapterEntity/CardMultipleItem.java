package com.example.df.zhiyun.mvp.model.adapterEntity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import com.example.df.zhiyun.mvp.model.entity.Answer;

/***
 * 答题卡adapter的各种条目渲染
 */
public class CardMultipleItem implements MultiItemEntity {
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_INDEX = 2;
    private int itemType;
    private Answer mData;
    private String index;
    private String title;

    public Answer getmData() {
        return mData;
    }

    public String getIndex() {
        return index;
    }

    public String getTitle(){
        return title;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public CardMultipleItem(int itemType, Answer data, String i, String title){
        this.itemType = itemType;
        this.mData = data;
        this.index = i;
        this.title = title;
    }
}

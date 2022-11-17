package com.example.df.zhiyun.mvp.model.adapterEntity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.mvp.model.entity.Question;

/***
 * 辅助练习adapter的各种条目渲染
 */
public class QuestionMultipleItem implements MultiItemEntity {
    private int itemType;
    private Question mData;
    private int count;

    public Question getData(){
        return mData;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public QuestionMultipleItem(int itemType, Question data){
        this.itemType = itemType;
        this.mData = data;
    }
}

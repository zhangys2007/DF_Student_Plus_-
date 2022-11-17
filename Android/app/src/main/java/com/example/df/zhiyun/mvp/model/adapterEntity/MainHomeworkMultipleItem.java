package com.example.df.zhiyun.mvp.model.adapterEntity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.mvp.model.entity.Homework;

/***
 * 主页最新作业的各种条目渲染
 */
public class MainHomeworkMultipleItem implements MultiItemEntity {
    public static final int TYPE_EMPTY = 0;
    public static final int TYPE_TOP = 1;
    public static final int TYPE_SUB = 2;

    private int itemType;
    private Homework mData;

    public Homework getData(){
        return mData;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public MainHomeworkMultipleItem(int itemType, Homework data){
        this.itemType = itemType;
        this.mData = data;
    }
}

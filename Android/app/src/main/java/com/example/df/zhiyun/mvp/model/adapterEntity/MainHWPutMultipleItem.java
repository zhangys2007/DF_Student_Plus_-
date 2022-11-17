package com.example.df.zhiyun.mvp.model.adapterEntity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import com.example.df.zhiyun.mvp.model.entity.HomeworkArrange;

/***
 * 主页最新布置的作业的各种条目渲染
 */
public class MainHWPutMultipleItem implements MultiItemEntity {
    public static final int TYPE_EMPTY = 0;
    public static final int TYPE_TOP = 1;
    public static final int TYPE_SUB = 2;

    private int itemType;
    private HomeworkArrange mData;

    public HomeworkArrange getData(){
        return mData;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public MainHWPutMultipleItem(int itemType, HomeworkArrange data){
        this.itemType = itemType;
        this.mData = data;
    }
}

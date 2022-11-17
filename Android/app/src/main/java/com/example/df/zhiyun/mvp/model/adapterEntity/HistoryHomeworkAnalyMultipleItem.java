package com.example.df.zhiyun.mvp.model.adapterEntity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/***
 * 搜索
 */
public class HistoryHomeworkAnalyMultipleItem implements MultiItemEntity {
    private int itemType;
    private Object data;

    public Object getData() {
        return data;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public HistoryHomeworkAnalyMultipleItem(int itemType, Object item){
        this.itemType = itemType;
        this.data = item;
    }
}

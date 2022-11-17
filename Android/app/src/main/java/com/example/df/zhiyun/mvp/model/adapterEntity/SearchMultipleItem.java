package com.example.df.zhiyun.mvp.model.adapterEntity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/***
 * 搜索
 */
public class SearchMultipleItem implements MultiItemEntity {
    private int itemType;
    private Object data;

    public Object getData() {
        return data;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public SearchMultipleItem(int itemType, Object item){
        this.itemType = itemType;
        this.data = item;
    }
}

package com.example.df.zhiyun.common.mvp.model.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public class CommonExpandableItem extends AbstractExpandableItem<CommonExpandableItem> implements MultiItemEntity {
    private Object data;
    private int mLevel;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public CommonExpandableItem(Object item,int level) {
        this.data = item;
        this.mLevel = level;
    }

    @Override
    public int getLevel() {
        return mLevel;
    }

    @Override
    public int getItemType() {
        return mLevel;
    }
}

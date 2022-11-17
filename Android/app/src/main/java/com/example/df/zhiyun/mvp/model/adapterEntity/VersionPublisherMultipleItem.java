package com.example.df.zhiyun.mvp.model.adapterEntity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public class VersionPublisherMultipleItem extends AbstractExpandableItem<VersionItemMultipleItem> implements MultiItemEntity {
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public VersionPublisherMultipleItem(Object item) {
        this.data = item;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return 0;
    }
}

package com.example.df.zhiyun.mvp.model.adapterEntity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.mvp.model.entity.VersionPublisher;

public class VersionItemMultipleItem implements MultiItemEntity {
    private Object data;
    private VersionPublisher parentData;
    private int selIndex;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public VersionPublisher getParentData() {
        return parentData;
    }

    public void setParentData(VersionPublisher parentData) {
        this.parentData = parentData;
    }

    public int getSelIndex() {
        return selIndex;
    }

    public void setSelIndex(int selIndex) {
        this.selIndex = selIndex;
    }

    public VersionItemMultipleItem(Object item,VersionPublisher parent, int sIndex) {
        this.data = item;
        this.parentData = parent;
        this.selIndex = sIndex;
    }

    @Override
    public int getItemType() {
        return 1;
    }
}

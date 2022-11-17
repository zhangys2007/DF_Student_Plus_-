package com.example.df.zhiyun.educate.mvp.model.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.mvp.model.adapterEntity.StudentsMultipleItem;

public class ClassPutMultipleItem extends AbstractExpandableItem<StudentsMultipleItem> implements MultiItemEntity {
    private Object data;
    private boolean selected;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ClassPutMultipleItem(Object item) {
        this.data = item;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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

package com.example.df.zhiyun.mvp.model.adapterEntity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class CategorySubMultipleItem implements MultiItemEntity  {
    private Object data;
    private boolean isLast;

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public CategorySubMultipleItem(Object item) {
        this.data = item;
    }


    @Override
    public int getItemType() {
        return 1;
    }
}

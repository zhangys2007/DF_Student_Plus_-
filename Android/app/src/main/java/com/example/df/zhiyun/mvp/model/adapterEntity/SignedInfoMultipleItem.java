package com.example.df.zhiyun.mvp.model.adapterEntity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/***
 * 辅助练习adapter的各种条目渲染
 */
public class SignedInfoMultipleItem implements MultiItemEntity {
    public static final int TYPE_EMPTY = 0;
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_DATE = 2;
    private int itemType;
    private String tag;
    private long time;

    public long getTime(){
        return time;
    }
    public void setTime(long time){
        this.time = time;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public SignedInfoMultipleItem(int itemType, String tag, long time){
        this.itemType = itemType;
        this.tag = tag;
        this.time = time;
    }
}

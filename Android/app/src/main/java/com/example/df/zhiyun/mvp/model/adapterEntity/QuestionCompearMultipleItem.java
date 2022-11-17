package com.example.df.zhiyun.mvp.model.adapterEntity;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.mvp.model.entity.Answer;

/***
 * 数据分析-班级成绩对比-小题得分对比
 */
public class QuestionCompearMultipleItem implements MultiItemEntity {
    public static final int TYPE_TITLE_1 = 1;
    public static final int TYPE_TITLE_2 = 2;
    public static final int TYPE_VALUE_1 = 3;
    public static final int TYPE_VALUE_2 = 4;
    private int itemType;
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public QuestionCompearMultipleItem(int itemType, String value){
        this.itemType = itemType;
        this.value = value;
    }
}

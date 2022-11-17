package com.example.df.zhiyun.mvp.model.entity;

import java.util.List;

/***
 * 我的收藏
 */
public class StoreSet {
    private int count;
    private List<Question> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Question> getList() {
        return list;
    }

    public void setList(List<Question> list) {
        this.list = list;
    }
}

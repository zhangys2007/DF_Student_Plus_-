package com.example.df.zhiyun.mvp.model.entity;

import com.example.df.zhiyun.educate.mvp.model.entity.BelongClass;

import java.util.List;

public class CommentListOther {
    List<BelongClass> list;
    String url;

    public List<BelongClass> getList() {
        return list;
    }

    public void setList(List<BelongClass> list) {
        this.list = list;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

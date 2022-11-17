package com.example.df.zhiyun.mvp.model.entity;

import java.util.List;

public class GradePerscent {
    List<PercentCount> top100List;
    List<PercentCount> top50List;

    public List<PercentCount> getTop100List() {
        return top100List;
    }

    public void setTop100List(List<PercentCount> top100List) {
        this.top100List = top100List;
    }

    public List<PercentCount> getTop50List() {
        return top50List;
    }

    public void setTop50List(List<PercentCount> top50List) {
        this.top50List = top50List;
    }
}

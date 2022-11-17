package com.example.df.zhiyun.mvp.model.entity;

import java.util.List;

public class GradePerscentItem {
    PercentCount top100;
    PercentCount top50;

    public PercentCount getTop100() {
        return top100;
    }

    public void setTop100(PercentCount top100) {
        this.top100 = top100;
    }

    public PercentCount getTop50() {
        return top50;
    }

    public void setTop50(PercentCount top50) {
        this.top50 = top50;
    }
}

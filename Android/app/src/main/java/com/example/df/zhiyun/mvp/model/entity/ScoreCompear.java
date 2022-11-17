/**
 * Copyright 2019 bejson.com
 */
package com.example.df.zhiyun.mvp.model.entity;

import java.util.List;

/**
 * Auto-generated: 2019-11-14 10:0:39
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class ScoreCompear {
    private List<ScoreCompearSumary> scoreOverviewList ;
    private List<ScoreCompearLevel> distributionList;
    private List<ScoreCompearLayer> arrangementContrastList ;

    public List<ScoreCompearSumary> getScoreOverviewList() {
        return scoreOverviewList;
    }

    public void setScoreOverviewList(List<ScoreCompearSumary> scoreOverviewList) {
        this.scoreOverviewList = scoreOverviewList;
    }

    public List<ScoreCompearLevel> getDistributionList() {
        return distributionList;
    }

    public void setDistributionList(List<ScoreCompearLevel> distributionList) {
        this.distributionList = distributionList;
    }

    public List<ScoreCompearLayer> getArrangementContrastList() {
        return arrangementContrastList;
    }

    public void setArrangementContrastList(List<ScoreCompearLayer> arrangementContrastList) {
        this.arrangementContrastList = arrangementContrastList;
    }
}
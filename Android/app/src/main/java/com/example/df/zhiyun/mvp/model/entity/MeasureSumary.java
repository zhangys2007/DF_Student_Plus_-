package com.example.df.zhiyun.mvp.model.entity;

import java.util.List;

public class MeasureSumary {
    List<LevelDivid> gradeDistributionList;
    List<ScoreSumary> scoreOverviewList;

    public List<LevelDivid> getGradeDistributionList() {
        return gradeDistributionList;
    }

    public void setGradeDistributionList(List<LevelDivid> gradeDistributionList) {
        this.gradeDistributionList = gradeDistributionList;
    }

    public List<ScoreSumary> getScoreOverviewList() {
        return scoreOverviewList;
    }

    public void setScoreOverviewList(List<ScoreSumary> scoreOverviewList) {
        this.scoreOverviewList = scoreOverviewList;
    }
}

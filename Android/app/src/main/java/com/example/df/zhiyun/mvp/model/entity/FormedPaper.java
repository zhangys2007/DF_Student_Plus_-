package com.example.df.zhiyun.mvp.model.entity;

public class FormedPaper {
    String homeworkId;
    String paperId;
    int type;
    String name;
    String uuid;
    int isput;

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getIsput() {
        return isput;
    }

    public void setIsput(int isput) {
        this.isput = isput;
    }
}

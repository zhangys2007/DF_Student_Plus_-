package com.example.df.zhiyun.main.mvp.model.entity;

import com.example.df.zhiyun.mvp.model.entity.BaseResponse;

import java.util.List;

/**
 * 首页的数据综合体，包含消息，推送，待做，关注的学生
 */
public class HomePageData {
    private BaseResponse<List<MsgItem>> messageData;
    private BaseResponse<List<FocusStd>> teacherFollowStudentData;
    private BaseResponse<List<PushData>> pushData;
    private BaseResponse<List<TodoItem>> homeWorkData;

    public BaseResponse<List<MsgItem>> getMessageData() {
        return messageData;
    }

    public void setMessageData(BaseResponse<List<MsgItem>> messageData) {
        this.messageData = messageData;
    }

    public BaseResponse<List<FocusStd>> getTeacherFollowStudentData() {
        return teacherFollowStudentData;
    }

    public void setTeacherFollowStudentData(BaseResponse<List<FocusStd>> teacherFollowStudentData) {
        this.teacherFollowStudentData = teacherFollowStudentData;
    }

    public BaseResponse<List<PushData>> getPushData() {
        return pushData;
    }

    public void setPushData(BaseResponse<List<PushData>> pushData) {
        this.pushData = pushData;
    }

    public BaseResponse<List<TodoItem>> getHomeWorkData() {
        return homeWorkData;
    }

    public void setHomeWorkData(BaseResponse<List<TodoItem>> homeWorkData) {
        this.homeWorkData = homeWorkData;
    }
}

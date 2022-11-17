package com.example.df.zhiyun.mvp.model.api;

public class IdentifyUtils {

    public static String getTitle(int type){
        if(type == Api.TYPE_STUDENT){
            return "学生";
        }else if(type == Api.TYPE_TEACHER){
            return "老师";
        }else if(type == Api.TYPE_PARENT){
            return "家长";
        }else{
            return "";
        }
    }
}

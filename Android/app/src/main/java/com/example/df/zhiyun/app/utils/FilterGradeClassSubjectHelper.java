package com.example.df.zhiyun.app.utils;

import android.text.TextUtils;

import com.example.df.zhiyun.mvp.model.entity.FilterClass;
import com.example.df.zhiyun.mvp.model.entity.FilterGrade;
import com.example.df.zhiyun.mvp.model.entity.FilterSubject;

import java.util.ArrayList;
import java.util.List;

public class FilterGradeClassSubjectHelper {
    /**
     * 解析第一层
     * @param data
     * @param op1
     * @param op2
     * @param op3
     */
    public static void parseLevel1(List<FilterGrade> data, List<String> op1, List<List<String>> op2, List<List<List<String>>> op3){
        if(data == null || data.size() == 0){
            return ;
        }

        for(FilterGrade grade : data){
            if(TextUtils.isEmpty(grade.getGrade())){
                op1.add("--");
            }else{
                op1.add(grade.getGrade());
            }
            List<String> op2Sub = new ArrayList<>();
            op2.add(op2Sub);

            List<List<String>> op3Sub = new ArrayList<>();
            op3.add(op3Sub);

            parseLevel2(grade.getClassList(),op2Sub,op3Sub);
        }
    }

    /**
     * 解析第二层
     * @param data
     * @param op2
     * @param op3
     */
    private static void parseLevel2(List<FilterClass> data, List<String> op2, List<List<String>> op3){
        if(data == null || data.size() == 0){
            op2.add("");
            List<String> op3Sub = new ArrayList<>();
            op3.add(op3Sub);
            parseLevel3(null,op3Sub);
            return ;
        }

        for(FilterClass filterClass : data){
            if(TextUtils.isEmpty(filterClass.getClassName())){
                op2.add("--");
            }else{
                op2.add(filterClass.getClassName());
            }
            List<String> op3Sub = new ArrayList<>();
            op3.add(op3Sub);

            parseLevel3(filterClass.getSubject(),op3Sub);
        }
    }

    /**
     * 解析第三层
     * @param data
     */
    private static void parseLevel3(List<FilterSubject> data, List<String> op3){
        if(data == null || data.size() == 0){
            op3.add("");
            return ;
        }

        for(FilterSubject filterSubject : data){
            if(TextUtils.isEmpty(filterSubject.getName())){
                op3.add("--");
            }else{
                op3.add(filterSubject.getName());
            }
        }
    }
}

/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.df.zhiyun.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.analy.mvp.contract.HistoryHomeworkAnalyContract;
import com.example.df.zhiyun.mvp.model.adapterEntity.HistoryHomeworkAnalyMultipleItem;
import com.example.df.zhiyun.mvp.model.entity.HistoryHWAnalyManager;
import com.example.df.zhiyun.mvp.model.entity.HistoryHWAnalyTeacher;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法 班级列表
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class HistoryHomeworkAnalyAdapter extends BaseMultiItemQuickAdapter<HistoryHomeworkAnalyMultipleItem, BaseViewHolder> {
    public HistoryHomeworkAnalyAdapter(@Nullable List<HistoryHomeworkAnalyMultipleItem> data) {
        super(data);
        addItemType(HistoryHomeworkAnalyContract.View.TYPE_TEACHER, R.layout.item_history_homework_analy);
        addItemType(HistoryHomeworkAnalyContract.View.TYPE_MANAGER, R.layout.item_history_homework_analy);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryHomeworkAnalyMultipleItem data) {
        if(helper.getItemViewType() == HistoryHomeworkAnalyContract.View.TYPE_MANAGER){
            renderManager(helper,data);
        }else{
            renderTeacher(helper,data);
        }
    }

    private void renderManager(BaseViewHolder helper, HistoryHomeworkAnalyMultipleItem data){
        HistoryHWAnalyManager item = (HistoryHWAnalyManager)data.getData();
        helper.setText(R.id.tv_time,StringCocatUtil.concat("日期：",item.getTime()))
                .setText(R.id.tv_subj,StringCocatUtil.concat("学科：",item.getSubjectName()))
                .setText(R.id.tv_grade, StringCocatUtil.concat("年级：",item.getGradeName()))
                .setText(R.id.tv_name,item.getHomeworkName())
                .setText(R.id.tv_full,String.format("满分：%d",(int)item.getScore()))
                .setText(R.id.tv_complete,Integer.toString(item.getFinishCount()))
                .setText(R.id.tv_all,"/"+Integer.toString(item.getUnFinishCount()+item.getFinishCount()))
                .addOnClickListener(R.id.btn_inspect)
                .addOnClickListener(R.id.btn_data);
    }

    private void renderTeacher(BaseViewHolder helper, HistoryHomeworkAnalyMultipleItem data){
        HistoryHWAnalyTeacher item = (HistoryHWAnalyTeacher)data.getData();
        helper.setText(R.id.tv_time,StringCocatUtil.concat("日期：",item.getTime()))
                .setText(R.id.tv_subj,StringCocatUtil.concat("学科：",item.getSubjectName()))
                .setText(R.id.tv_grade,StringCocatUtil.concat("班级：",item.getClassName()))
                .setText(R.id.tv_name,item.getHomeworkName())
                .setText(R.id.tv_complete_title,"已批/未批：")
                .setText(R.id.tv_full,String.format("满分：%d",(int)item.getScore()))
                .setText(R.id.tv_complete,Integer.toString(item.getCorrectCount()))
                .setText(R.id.tv_all,"/"+Integer.toString(item.getUnCorrectCount()))
                .addOnClickListener(R.id.btn_inspect)
                .setGone(R.id.btn_data,false)
                .addOnClickListener(R.id.btn_data);
    }
}

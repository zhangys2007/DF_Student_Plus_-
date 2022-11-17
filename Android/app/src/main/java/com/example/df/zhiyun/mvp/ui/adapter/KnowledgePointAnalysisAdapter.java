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
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.mvp.model.entity.ClassItem;
import com.example.df.zhiyun.mvp.model.entity.PointAnalysis;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class KnowledgePointAnalysisAdapter extends BaseQuickAdapter<PointAnalysis, BaseViewHolder> {
    public KnowledgePointAnalysisAdapter(@Nullable List<PointAnalysis> data) {
        super(R.layout.item_knowledge_points_analysis,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PointAnalysis data) {
        helper.setText(R.id.tv_subj_name, StringCocatUtil.concat("学科：",data.getSubject()))
                .setText(R.id.tv_create_time,StringCocatUtil.concat("布置日期：",data.getTime()))
                .setText(R.id.tv_name,data.getName())
                .setText(R.id.tv_grade,StringCocatUtil.concat("年级：",data.getGradeName()))
                .setText(R.id.tv_class,StringCocatUtil.concat("班级：",data.getClassName()))
                .setText(R.id.tv_score,String.format("%.1f",data.getScore()))
                .setText(R.id.tv_rate,String.format("%.1f%%",data.getScoreRate()));
    }

}

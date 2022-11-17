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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.model.entity.DetailTable;
import com.example.df.zhiyun.mvp.model.entity.KnowledgeGrasp;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法 知识点掌握
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class DetailTableAdapter extends BaseQuickAdapter<DetailTable, BaseViewHolder> {
    public DetailTableAdapter(@Nullable List<DetailTable> data) {
        super(R.layout.item_detail_table,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DetailTable data) {
        helper.setText(R.id.tv_dt_index,Integer.toString(helper.getAdapterPosition()+1))
                .setText(R.id.tv_dt_type,data.getTypeName())
                .setText(R.id.tv_dt_target,data.getMeasureTarget())
                .setText(R.id.tv_dt_content,data.getKnowledge())
                .setText(R.id.tv_dt_difficulty,data.getDifficulty())
                .setText(R.id.tv_dt_error,String.format("%.1f%%",data.getGradeErrorRate()))
                .setText(R.id.tv_dt_score,String.format("%.1f",data.getScore()))
                .setText(R.id.tv_dt_avg,String.format("%.1f",data.getAverage()))
                .setText(R.id.tv_dt_std,String.format("%.2f",data.getStandardDeviation()))
                .setText(R.id.tv_dt_identity,String.format("%.2f",data.getDiscriminativePower()))
                ;
    }

}

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
import com.example.df.zhiyun.mvp.model.entity.GradeAvg;
import com.example.df.zhiyun.mvp.model.entity.ScoreCompearLevel;
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
public class GradeLevelAdapter extends BaseQuickAdapter<ScoreCompearLevel, BaseViewHolder> {
    public GradeLevelAdapter(@Nullable List<ScoreCompearLevel> data) {
        super(R.layout.item_table_9,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScoreCompearLevel data) {
        helper.setText(R.id.tv_dd_0,data.getClassName())
                .setText(R.id.tv_dd_1,Integer.toString(data.getExcellentCount()))
                .setText(R.id.tv_dd_2,Integer.toString(data.getGoodCount()))
                .setText(R.id.tv_dd_3,Integer.toString(data.getMediumCount()))
                .setText(R.id.tv_dd_4,Integer.toString(data.getPassCount()))
                .setText(R.id.tv_dd_5,Integer.toString(data.getUnPassCount()))
                .setText(R.id.tv_dd_6,Integer.toString(data.getCount()))
                .setText(R.id.tv_dd_7,String.format("%d/%d",data.getAssignCount(),data.getUnAssignCount()))
                .setText(R.id.tv_dd_8,String.format("%d/%d",data.getSubmitCount(),data.getUnSubmitCount()));
    }

}

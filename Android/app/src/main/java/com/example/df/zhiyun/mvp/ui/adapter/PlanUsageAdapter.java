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
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.mvp.model.entity.PlanUsage;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法   公告
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class PlanUsageAdapter extends BaseQuickAdapter<PlanUsage, BaseViewHolder> {
    public PlanUsageAdapter(@Nullable List<PlanUsage> data) {
        super(R.layout.item_plan_usage,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlanUsage data) {
        helper.setText(R.id.tv_date,data.getTime())
        .setText(R.id.tv_cla, StringCocatUtil.concat("(",Integer.toString(data.getClassNum()),")个"))
        .setText(R.id.tv_name, StringCocatUtil.concat(data.getRealName(),"(",data.getUserName(),")"))
        .setText(R.id.tv_accese_total, Integer.toString(data.getTotalNum()))
        .setText(R.id.tv_cloud_count, Integer.toString(data.getCloudNum()))
        .setText(R.id.tv_self_count, Integer.toString(data.getSelfNum()))
        .setText(R.id.tv_file_total, Integer.toString(data.getCreateNum()))
        .setText(R.id.tv_plan_count, Integer.toString(data.getPlanNum()))
        .setText(R.id.tv_hw_count, Integer.toString(data.getHomeworkNum()));
    }
}

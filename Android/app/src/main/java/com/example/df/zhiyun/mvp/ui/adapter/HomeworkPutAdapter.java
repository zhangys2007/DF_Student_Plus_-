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
import com.example.df.zhiyun.mvp.model.api.Api;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.mvp.model.entity.HomeworkArrange;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法   教师主页上的作业
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class HomeworkPutAdapter extends BaseQuickAdapter<HomeworkArrange, BaseViewHolder> {

    public HomeworkPutAdapter(@Nullable List<HomeworkArrange> data) {
        super(R.layout.item_main_hw_top_tch,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeworkArrange data) {
        helper.setText(R.id.tv_hw_complete,data.getHomeworkStatus()== Api.FINISH?R.string.complete:R.string.uncomplete)
                .setText(R.id.tv_count_corrected,""+data.getApprovedNumber())
                .setText(R.id.tv_count_uncorrected,""+data.getUnApprovedNumber())
                .setText(R.id.tv_count_unsubmit,""+data.getUnpaidNumber())
                .setText(R.id.tv_hw_name,data.getHomeworkName());
        try {
            helper.setText(R.id.tv_hw_time, TimeUtils.getYmdDot(Long.parseLong(data.getCreateTime())));
        }catch (NumberFormatException e){

        }

        helper.addOnClickListener(R.id.ll_corrected);
        helper.addOnClickListener(R.id.ll_uncorrected);
        helper.addOnClickListener(R.id.ll_unsubmit);
        helper.addOnClickListener(R.id.iv_start);
    }
}

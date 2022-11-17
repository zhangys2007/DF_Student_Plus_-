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
package com.example.df.zhiyun.educate.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.analy.mvp.model.entity.KnowledgeHold;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.main.mvp.model.entity.TodoItem;
import com.example.df.zhiyun.mvp.ui.widget.RoundnessProgressBar;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法  教学-》作业管理 下的布置作业和历次作业
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class KnowledgeHoldAdapter extends BaseQuickAdapter<KnowledgeHold, BaseViewHolder> {
    public KnowledgeHoldAdapter(@Nullable List<KnowledgeHold> data) {
        super(R.layout.item_knowledge_hold,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeHold data) {
        helper.setText(R.id.tv_k_time,String.format("作业时间：%s",TimeUtils.getYmd(data.getTime())));
        helper.setText(R.id.tv_k_count,String.format("共有 %d 位学生做错",data.getCount()));
        helper.setText(R.id.tv_k_name,data.getName());
        helper.setText(R.id.tv_k_content,data.getContent());
        helper.setText(R.id.tv_k_rate_value,String.format("%.1f%%",data.getRate()));
        RoundnessProgressBar progressBar = helper.getView(R.id.pb_rate);
        progressBar.setProgress(data.getRate());
    }
}

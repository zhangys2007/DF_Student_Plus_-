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
package com.example.df.zhiyun.main.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.main.mvp.model.entity.TodoItem;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法   待办事项
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class TodoItemAdapter extends BaseQuickAdapter<TodoItem, BaseViewHolder> {
    public TodoItemAdapter(@Nullable List<TodoItem> data) {
        super(R.layout.view_backlog,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TodoItem data) {
        helper.setText(R.id.tv_todo_time, TimeUtils.getMd(data.getCreateTime()))
                .setText(R.id.tv_todo_title, data.getHomeworkName())
                .setText(R.id.tv_post_content, data.getClassName());
        TextView tvCount = helper.getView(R.id.tv_todo_count);
        tvCount.setText(makeCountStr(data.getApprovedNumber(),
                data.getPaidNumber(),data.getUnpaidNumber()));
    }

    /**
     * 根据已批，已交，未交人数生成对应颜色的字符串
     * @param corrected
     * @param pay
     * @param unpay
     * @return
     */
    private SpannableStringBuilder makeCountStr(int corrected,int pay,int unpay){
        String strCor = Integer.toString(corrected);
        String strPay = Integer.toString(pay);
        String strUnpay = Integer.toString(unpay);

        int startIndex = 0;
        SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append("已批：");
        startIndex = builder.length();
        builder.append(strCor);
        builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext,R.color.green)),
                startIndex,builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.append("人  提交：");
        startIndex = builder.length();
        builder.append(strPay);
        builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext,R.color.orange)),
                startIndex,builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        builder.append("人  未交：");
        startIndex = builder.length();
        builder.append(strUnpay);
        builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext,R.color.red)),
                startIndex,builder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append("人");

        return builder;
    }
}

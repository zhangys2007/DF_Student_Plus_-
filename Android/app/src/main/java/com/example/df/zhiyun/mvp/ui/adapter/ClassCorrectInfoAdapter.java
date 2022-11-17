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
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.mvp.model.entity.ClassCorrectInfo;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法 班级批改详情
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class ClassCorrectInfoAdapter extends BaseQuickAdapter<ClassCorrectInfo, BaseViewHolder> {
    ForegroundColorSpan fsRed ;
    ForegroundColorSpan fsGreen ;

    public ClassCorrectInfoAdapter(@Nullable List<ClassCorrectInfo> data) {
        super(R.layout.item_class_correct_info,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassCorrectInfo data) {
        if(fsRed == null){
            fsRed = new ForegroundColorSpan(ContextCompat.getColor(helper.itemView.getContext(),R.color.colorPrimary));
            fsGreen = new ForegroundColorSpan(ContextCompat.getColor(helper.itemView.getContext(),R.color.green));
        }

        helper.setText(R.id.tv_class_name,data.getClassName())
                .setText(R.id.tv_teacher_name, StringCocatUtil.concat("任课老师：",data.getRealName()))
                .setText(R.id.tv_complete,data.getHomeworkStatus()==1?"是":"否")
                .setText(R.id.tv_complete_time,data.getFinishTime())
                .addOnClickListener(R.id.btn_data);

        TextView tvSubmit = helper.getView(R.id.tv_submit);
        setSpanableString(tvSubmit,data.getSubmitCount(),data.getUnSubmitCount(),fsRed);
        TextView tvCorrect = helper.getView(R.id.tv_correct);
        setSpanableString(tvCorrect,data.getCorrectCount(),data.getUnCorrectCount(),fsGreen);
    }

    private void setSpanableString(TextView view,int value1, int value2, ForegroundColorSpan foregroundColorSpan){
        String strV1 = Integer.toString(value1);

        SpannableStringBuilder ssb = new SpannableStringBuilder(strV1);
        ssb.append("/").append(Integer.toString(value2));
        ssb.setSpan(foregroundColorSpan,0,strV1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(ssb);
    }
}

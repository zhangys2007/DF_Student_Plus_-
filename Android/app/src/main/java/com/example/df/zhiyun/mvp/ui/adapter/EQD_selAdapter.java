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
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.mvp.model.entity.QuestionOption;

import java.util.List;

import com.example.df.zhiyun.R;

/**
 * ================================================
 * 错题详解的选择题
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class EQD_selAdapter extends BaseQuickAdapter<QuestionOption, BaseViewHolder> {
    private String officeAnswer, stdAnsewer;
    public void setSelect(String office,String student){
        officeAnswer = office;
        stdAnsewer = student;
    }
    public EQD_selAdapter(@Nullable List<QuestionOption> data) {
        super(R.layout.item_error_question_option,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuestionOption data) {

        int textColor;
        if(TextUtils.equals(data.getOption(),officeAnswer)){
            textColor = ContextCompat.getColor(helper.itemView.getContext(),R.color.green);
            helper.setTextColor(R.id.tv_op_name, textColor)
                    .setBackgroundRes(R.id.ll_op_container,R.drawable.border_round_green)
                    .setBackgroundColor(R.id.tv_op_div,textColor);
        }else if(TextUtils.equals(data.getOption(),stdAnsewer)){
            textColor = ContextCompat.getColor(helper.itemView.getContext(),R.color.colorPrimary);
            helper.setTextColor(R.id.tv_op_name, textColor)
                    .setBackgroundRes(R.id.ll_op_container,R.drawable.border_round_red)
                    .setBackgroundColor(R.id.tv_op_div,textColor);
        }

        helper.setText(R.id.tv_op_name,data.getOption());

        HtmlTextView tvContent = (HtmlTextView)helper.getView(R.id.tv_op_content);
        RichTextViewHelper.setContent(tvContent,data.getOptionContent());
    }
}

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
package com.example.df.zhiyun.paper.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.app.TypefaceHolder;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.mvp.model.entity.Answer;
import com.example.df.zhiyun.mvp.model.entity.QuestionOption;

/**
 * ================================================
 * 做作业里的多项选择题类型
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class HWmultAdapter extends BaseQuickAdapter<QuestionOption, BaseViewHolder> {
    private Set<String> sels = new HashSet<>();
    private Answer mAnswer = new Answer();

    public Answer getAnswer(){
        mAnswer.getAnswer();
        mAnswer.getAnswer().clear();
        for(String sel:sels){
            mAnswer.getAnswer().add(sel);
        }
        return mAnswer;
    }

    public void initWithAnswer(Answer answer){
        this.mAnswer = answer;
        if(mAnswer.getAnswer() == null){
            fillWithEmptyAnswer();
        }else{
            for(String sel: mAnswer.getAnswer()){
                sels.add(sel);
            }
        }
        notifyDataSetChanged();
    }

    public void setSelect(int index){
        String option = getData().get(index).getOption();
        if(TextUtils.isEmpty(option)){
            return;
        }

        if(sels.contains(option)){
            sels.remove(option);
        }else {
            sels.add(option);
        }

        notifyItemChanged(index);
    }

    public HWmultAdapter(@Nullable List<QuestionOption> data) {
        super(R.layout.item_error_question_option,data);
    }

    @Override
    public void setNewData(@Nullable List<QuestionOption> data) {
        super.setNewData(data);
        fillWithEmptyAnswer();
    }

    private void fillWithEmptyAnswer(){
        List<String> ai = new ArrayList<>();
        mAnswer.setAnswer(ai);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuestionOption data) {
        int textColor;
        String option = data.getOption();

        if(!TextUtils.isEmpty(option) && sels.contains(option)){
            textColor = ContextCompat.getColor(helper.itemView.getContext(),R.color.orange);
            helper.setTextColor(R.id.tv_op_name, textColor)
                    .setBackgroundRes(R.id.ll_op_container,R.drawable.border_round_orange)
                    .setBackgroundColor(R.id.tv_op_div,textColor);
        }else{
            textColor = ContextCompat.getColor(helper.itemView.getContext(),R.color.text_666);
            helper.setTextColor(R.id.tv_op_name, textColor)
                    .setBackgroundRes(R.id.ll_op_container,R.drawable.border_round_grey)
                    .setBackgroundColor(R.id.tv_op_div,textColor);
        }

        helper.setText(R.id.tv_op_name,data.getOption());

        HtmlTextView tvContent = (HtmlTextView)helper.getView(R.id.tv_op_content);
        TypefaceHolder.getInstance().setHt(tvContent);
        if(data.getOptionContent() != null){
            RichTextViewHelper.setContent(tvContent,data.getOptionContent());
        }

    }
}

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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.app.TypefaceHolder;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.mvp.model.entity.Answer;
import com.example.df.zhiyun.mvp.model.entity.QuestionOption;

import java.util.ArrayList;
import java.util.List;

import com.example.df.zhiyun.R;

/**
 * ================================================
 * 做作业里的选择题类型
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class HWselAdapter extends BaseQuickAdapter<QuestionOption, BaseViewHolder> {
    private static final String PAY_LOAD = "sel";
    private int lastSel = -1;
    private Answer mAnswer = new Answer();

    public Answer getAnswer(){
        return mAnswer;
    }

    public void initWithAnswer(Answer answer){
        this.mAnswer = answer;
        if(mAnswer.getAnswer() == null || mAnswer.getAnswer().size() == 0){
            fillWithEmptyAnswer();
        }
        lastSel = querySel();
        notifyDataSetChanged();
    }

    public void setSelect(int index){
        if(index == lastSel){
            return;
        }
        mAnswer.getAnswer().set(0, getData().get(index).getOption());
        int currentSel = querySel();
        notifyItemChanged(currentSel,PAY_LOAD);
        if(lastSel != -1){
            notifyItemChanged(lastSel,PAY_LOAD);
        }
        lastSel = currentSel;
    }

    public HWselAdapter(@Nullable List<QuestionOption> data) {
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
        ai.add("");
        lastSel = -1;
    }

    //查找当前选择的position
    private int querySel(){
        if(mAnswer.getAnswer() == null || mAnswer.getAnswer().size() == 0 ||
                TextUtils.isEmpty(mAnswer.getAnswer().get(0))){
            return -1;
        }
        for(int i=0;i<getData().size();i++){
            if(TextUtils.equals(mAnswer.getAnswer().get(0),getData().get(i).getOption())){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        int textColor;
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            String tag = (String) payloads.get(0);
            if(TextUtils.equals(tag,PAY_LOAD)){
                QuestionOption data = getData().get(position);
                if(TextUtils.equals(mAnswer.getAnswer().get(0),data.getOption())){
                    textColor = ContextCompat.getColor(holder.itemView.getContext(),R.color.orange);
                    holder.setTextColor(R.id.tv_op_name, textColor)
                            .setBackgroundRes(R.id.ll_op_container,R.drawable.border_round_orange)
                            .setBackgroundColor(R.id.tv_op_div,textColor);
                }else{
                    textColor = ContextCompat.getColor(holder.itemView.getContext(),R.color.text_666);
                    holder.setTextColor(R.id.tv_op_name, textColor)
                            .setBackgroundRes(R.id.ll_op_container,R.drawable.border_round_grey)
                            .setBackgroundColor(R.id.tv_op_div,textColor);
                }
            }
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, QuestionOption data) {

        int textColor;
        if(TextUtils.equals(mAnswer.getAnswer().get(0),data.getOption())){
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

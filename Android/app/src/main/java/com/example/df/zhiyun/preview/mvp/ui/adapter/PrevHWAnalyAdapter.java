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
package com.example.df.zhiyun.preview.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.TypefaceHolder;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.mvp.model.entity.QuestionOption;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

/**
 * ================================================
 * 作业预览里的带好几个小题的解析
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class PrevHWAnalyAdapter extends BaseQuickAdapter<Question, BaseViewHolder> {
    private boolean showStudentAnswer = false;
    LayoutInflater inflater;
    int divHeight = 10;

    public void setShowStudentAnswer(boolean value){
        showStudentAnswer = value;
    }

    public PrevHWAnalyAdapter(@Nullable List<Question> data) {
        super(R.layout.item_subquestion_analy,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Question data) {
        HtmlTextView questionName = helper.getView(R.id.tv_subname);
        HtmlTextView answer = helper.getView(R.id.tv_answer);
        HtmlTextView analy = helper.getView(R.id.tv_analy);

        TypefaceHolder.getInstance().setFs(questionName);
        RichTextViewHelper.setContent(questionName,
                RichTextViewHelper.makeQuestionTitle(data.getQuestionNum(),data.getContent()));
        RichTextViewHelper.setContent(answer,data.getAnswer());
        RichTextViewHelper.setContent(analy,data.getAnalysis());

        TextView myTitle = helper.getView(R.id.tv_my_title);
        HtmlTextView myAnswer = helper.getView(R.id.tv_my_answer);
        myTitle.setVisibility(showStudentAnswer? View.VISIBLE:View.GONE);
        myAnswer.setVisibility(showStudentAnswer? View.VISIBLE:View.GONE);
        if(showStudentAnswer){
            if(TextUtils.isEmpty(data.getStudentAnswer())){
                RichTextViewHelper.setContent(myAnswer,"未作答");
            }else{
                RichTextViewHelper.setContent(myAnswer,data.getStudentAnswer());
            }
        }

        LinearLayout llSelContainer = (LinearLayout)helper.getView(R.id.ll_sel_container);     //设置选择题的选项
        if(TextUtils.equals(data.getQuestionType(),Integer.toString(Api.QUESTION_SELECT)) ||
                TextUtils.equals(data.getQuestionType(),Integer.toString(Api.QUESTION_MULTIPLE_SELECT))){
            llSelContainer.setVisibility(View.VISIBLE);
            llSelContainer.removeAllViews();
            if(data.getOptionList() != null){
                if(inflater == null){
                    inflater = LayoutInflater.from(llSelContainer.getContext());
                    divHeight = ArmsUtils.dip2px(llSelContainer.getContext(),5);
                }
                for(QuestionOption option:data.getOptionList()){
                    View vItem = inflater.inflate(R.layout.item_error_question_option,llSelContainer,false);
                    TextView opname = vItem.findViewById(R.id.tv_op_name);
                    opname.setText(option.getOption());

                    HtmlTextView opContent = (HtmlTextView)vItem.findViewById(R.id.tv_op_content);
                    TypefaceHolder.getInstance().setFs(opContent);
                    if(option.getOptionContent() != null){
                        RichTextViewHelper.setContent(opContent,option.getOptionContent());
                    }

                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)vItem.getLayoutParams();
                    params.setMargins(0,divHeight,0,divHeight);
                    llSelContainer.addView(vItem);
                }
            }
        }else{
            llSelContainer.setVisibility(View.GONE);
        }
    }
}

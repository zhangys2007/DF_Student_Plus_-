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
package com.example.df.zhiyun.correct.mvp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.app.TypefaceHolder;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.mvp.model.entity.QuestionOption;
import com.example.df.zhiyun.mvp.ui.widget.BFocusChangeListener;
import com.example.df.zhiyun.mvp.ui.widget.BOnOptionsSelectListener;
import com.example.df.zhiyun.mvp.ui.widget.BTextWatcher;
import com.example.df.zhiyun.mvp.ui.widget.BViewClickListener;
import com.example.df.zhiyun.mvp.ui.widget.PickViewHelper;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作业批改里的带好几个小题的解析
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class CorrectHWAnalyAdapter extends BaseQuickAdapter<Question, BaseViewHolder> {
    LayoutInflater inflater;
    int divHeight = 10;
    public CorrectHWAnalyAdapter(@Nullable List<Question> data) {
        super(R.layout.item_subquestion_correct,data);
    }


    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            String tag = (String) payloads.get(0);
            if(TextUtils.equals(tag,"score")){
                String initScore = Integer.toString((int)(getData().get(position).getStudentScore()));
                holder.setText(R.id.tv_score_set,initScore);

                holder.setGone(R.id.tv_unread,false);
                holder.setVisible(R.id.tv_readed,true);
            }else if(TextUtils.equals(tag,"mask")){
                holder.setVisible(R.id.ll_ll_remain,true);
                holder.setGone(R.id.fl_analy_mask,false);
            }
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, Question data) {
        int index = helper.getAdapterPosition();
        HtmlTextView questionName = helper.getView(R.id.tv_subname);
        TypefaceHolder.getInstance().setFs(questionName);
        RichTextViewHelper.setContent(questionName,
                RichTextViewHelper.makeQuestionTitle(data.getQuestionNum(),data.getContent()));

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


        RichTextViewHelper.setContent(helper.getView(R.id.tv_answer_standard),data.getAnswer());
        if(TextUtils.isEmpty(data.getStudentAnswer())){
            RichTextViewHelper.setContent(helper.getView(R.id.tv_answer_student),"未答");
        }else{
            RichTextViewHelper.setContent(helper.getView(R.id.tv_answer_student),data.getStudentAnswer());
        }


        if(data.getStudentAnswerStatus() == Api.STUDENT_ANSWER_STATUS_UNREAD) {  //未批
            helper.setGone(R.id.ll_container_analy,false);

            if(AccountManager.getInstance().getUserInfo().getRoleId() == Api.TYPE_TEACHER){   //当前教师
                if(data.isScorreChanged()){
                    helper.setGone(R.id.tv_unread,false);
                    helper.setVisible(R.id.tv_readed,true);
                }else{
                    helper.setGone(R.id.tv_readed,false);
                    helper.setVisible(R.id.tv_unread,true);
                }

                helper.setVisible(R.id.tv_score_set,true);
                helper.addOnClickListener(R.id.tv_score_set);
                EditText etComment = helper.getView(R.id.et_comment);
                etComment.setText(data.getComment());
                etComment.setSelection(etComment.length());

                String initScore = Integer.toString((int)(data.getStudentScore()));
                helper.setText(R.id.tv_score_set,initScore);
                TextView tvScoreSet = helper.getView(R.id.tv_score_set);
                tvScoreSet.setOnClickListener(new BViewClickListener(index){
                    @Override
                    public void onClick(View v) {
                        List<String> options = makeScoreOptions(data.getQuestionSum());
                        if(options.size() > 0){
                            PickViewHelper.getOptionPicker(v.getContext(),options
                                    ,new BOnOptionsSelectListener(mIndex){
                                        @Override
                                        public void onOptionsSelect(int options1, int options2, int options3, View v) {
                                            Question item = getData().get(mIndex);
                                            item.setStudentScore(options1);
                                            item.setScorreChanged(true);
                                            notifyItemChanged(mIndex,"score");
                                        }
                                    }).show();
                        }
                    }
                });

                etComment.setOnFocusChangeListener(new BFocusChangeListener(index) {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(hasFocus){
                            BTextWatcher watcher =
                                    new BTextWatcher(mIndex) {
                                        @Override
                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        }

                                        @Override
                                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                                        }

                                        @Override
                                        public void afterTextChanged(Editable s) {
                                            Question item = getData().get(mIndex);
                                            item.setComment(s.toString());
                                        }
                                    };
                            ((EditText)v).addTextChangedListener(watcher);
                            v.setTag(watcher);
                        }else{
                            if(v.getTag() != null && v.getTag() instanceof BTextWatcher){
                                ((EditText)v).removeTextChangedListener((BTextWatcher)v.getTag());
                            }
                        }
                    }
                });

            }else{ //当前学生
                helper.setGone(R.id.ll_container_comment,false);
                helper.setVisible(R.id.tv_unread,true);
            }
        }else { //已批
            helper.setGone(R.id.ll_container_comment,false);
            helper.setVisible(R.id.tv_score,true);
            helper.setVisible(R.id.tv_readed,true);

            helper.setText(R.id.tv_score,""+data.getStudentScore());
            if(TextUtils.isEmpty(data.getComment())){
                helper.setText(R.id.tv_comment,StringCocatUtil.concat("批注: ","暂无"));
            }else{
                helper.setText(R.id.tv_comment,StringCocatUtil.concat("批注: ",data.getComment()));
            }

            helper.setText(R.id.tv_avg_score,StringCocatUtil.concat("平均分: ",Float.toString(data.getAvg()),"分 (答对",
                    Integer.toString(data.getCorrectCount()),"人/答错",Integer.toString(data.getErrorCount())
                    ,"人/班级得分率",data.getScoring(),")"));
            helper.setText(R.id.tv_knowledge,StringCocatUtil.concat("知识内容: ",data.getKnowledgeSystem()));

            RichTextViewHelper.setContent(helper.getView(R.id.tv_gold),StringCocatUtil.concat("测量目标: ",data.getMeasureTarget()).toString());
            RichTextViewHelper.setContent(helper.getView(R.id.tv_question_analy),StringCocatUtil.concat("解析: ",data.getAnalysis()).toString());

            hideDetailWhenStudent(helper,data);
        }
    }

    /**
     * 当是学生时，先隐藏，如果点击展开后就一直展开
     */
    private void hideDetailWhenStudent(BaseViewHolder helper,Question data){
        if(AccountManager.getInstance().getUserInfo().getRoleId() != Api.TYPE_STUDENT){
            return;
        }

        int index = helper.getAdapterPosition();
        if(!data.isExpand()){
            helper.setGone(R.id.ll_ll_remain,false);
            helper.setVisible(R.id.fl_analy_mask,true);
            helper.getView(R.id.tv_expand).setOnClickListener(new BViewClickListener(index){
                @Override
                public void onClick(View v) {
                    Question item = getData().get(mIndex);
                    item.setExpand(true);
                    notifyItemChanged(mIndex,"mask");
                }
            });
        }
    }

    private List<String> makeScoreOptions(float score){
        List<String> options = new ArrayList<>();
        if(score > 0){
            int max = (int)(Math.ceil(score));
            for(int i=0;i<= max;i++){
                options.add(""+i);
            }
        }
        return options;
    }
}

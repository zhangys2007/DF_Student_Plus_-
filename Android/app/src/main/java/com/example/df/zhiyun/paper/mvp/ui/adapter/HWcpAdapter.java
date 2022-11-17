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

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.app.TypefaceHolder;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.ui.widget.BFocusChangeListener;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;
import com.example.df.zhiyun.app.utils.Base64BitmapTransformor;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.mvp.model.adapterEntity.CpQuestionMultipleItem;
import com.example.df.zhiyun.mvp.model.entity.Answer;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.mvp.model.entity.QuestionOption;
import com.example.df.zhiyun.mvp.ui.widget.BTextWatcher;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.example.df.zhiyun.R;

/**
 * ================================================
 * 做作业里的复合题类型， 选择，填空
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class HWcpAdapter extends BaseMultiItemQuickAdapter<CpQuestionMultipleItem, BaseViewHolder> {
    private LinkedHashMap<String, Answer> answers = new LinkedHashMap<String, Answer>(10,0.6f,false);

    public List<Answer> getAnswers(){
        List<Answer> list = new ArrayList<>();
        for(Answer answer: answers.values()){
            list.add(answer);
        }
        return list;
    }

    public void setInitAnsers(List<Answer> data){
        if(data != null){
//            answers.clear();
            for(Answer answer:data){
                if(answer.getAnswer() != null && answer.getAnswer().size() > 0){  //有答案
                    answers.put(answer.getQuestionId(),answer);
                }else{ //无答案就把预先填充的结构填入
                    Answer old = answers.get(answer.getQuestionId());
                    answer.setAnswer(old.getAnswer());
                    answer.setTypes(old.getTypes());
                    answers.put(answer.getQuestionId(),answer);
                }
            }
            notifyDataSetChanged();
        }
    }

    //设置是否画板输入
    public void setCanvasMethod(int position,boolean value){
        CpQuestionMultipleItem item = getData().get(position);
        Answer answer = answers.get(item.getData().getQuestionId());
        int optionIndex = item.getOptIndex();
        answer.getTypes().set(optionIndex,value?1:0);
        answer.getAnswer().set(optionIndex,"");
        notifyItemChanged(position);
    }

    public boolean isCanvasMethod(int position){
        CpQuestionMultipleItem item = getData().get(position);
        Answer answer = answers.get(item.getData().getQuestionId());
        int optionIndex = item.getOptIndex();
        return answer.getTypes().get(optionIndex) == 1;
    }

    public void setSelectIndex(int position){
        CpQuestionMultipleItem item = getData().get(position);


        if(item.getItemType() == CpQuestionMultipleItem.TYPE_OPTION_SELECT){
            Answer answer = answers.get(item.getData().getQuestionId());
            if(answer.getAnswer() == null){
                answer.setAnswer(new ArrayList<>());
            }
            if(!answer.getAnswer().contains(item.getOption().getOption())){
                answer.getAnswer().clear();
                answer.getAnswer().add(item.getOption().getOption());
                notifyDataSetChanged();
            }
        }else if(item.getItemType() == CpQuestionMultipleItem.TYPE_OPTION_MULTIPLE){
            Answer answer = answers.get(item.getData().getQuestionId());
            if(answer.getAnswer() == null){
                answer.setAnswer(new ArrayList<>());
            }
            String op = item.getOption().getOption();
            if(answer.getAnswer().contains(op)){
                answer.getAnswer().remove(op);
            }else{
                answer.getAnswer().add(op);
            }
            notifyDataSetChanged();
        }
    }


    public void insertBitmap(Bitmap bitmap,int position){
        CpQuestionMultipleItem item = getData().get(position);
        Answer answer = answers.get(item.getData().getQuestionId());
        int optionIndex = item.getOptIndex();
        answer.getAnswer().set(optionIndex, Base64BitmapTransformor.getString(bitmap));
        notifyItemChanged(position);
    }


    public HWcpAdapter(List<CpQuestionMultipleItem> data) {
        super(data);
        addItemType(CpQuestionMultipleItem.TYPE_TITLE_INPUT, R.layout.item_subquestion_title);
        addItemType(CpQuestionMultipleItem.TYPE_TITLE_SELECT, R.layout.item_subquestion_title);
        addItemType(CpQuestionMultipleItem.TYPE_OPTION_INPUT, R.layout.item_question_input);
        addItemType(CpQuestionMultipleItem.TYPE_OPTION_SELECT, R.layout.item_error_question_option);
        addItemType(CpQuestionMultipleItem.TYPE_OPTION_SIMPLE, R.layout.item_question_simple_answer);
        addItemType(CpQuestionMultipleItem.TYPE_OPTION_MULTIPLE, R.layout.item_error_question_option);
    }

    @Override
    public void setNewData(@Nullable List<CpQuestionMultipleItem> data) {
        super.setNewData(data);
        if(data != null){
            for(CpQuestionMultipleItem questionOption: data){
                if(questionOption.getItemType() == CpQuestionMultipleItem.TYPE_TITLE_INPUT ||
                        questionOption.getItemType() == CpQuestionMultipleItem.TYPE_TITLE_SELECT){
                    Question question = questionOption.getData();
                    Answer answer = new Answer();
                    answer.setQuestionId(question.getQuestionId());
                    answer.setQuestionNum(question.getOptionList()==null?"0":""+question.getOptionList().size());
                    answer.setSubAnswer(null);
                    List<String> ai = new ArrayList<>();
                    List<Integer> types = new ArrayList<>();
                    answer.setAnswer(ai);
                    answer.setTypes(types);
                    List<QuestionOption> optionList = question.getOptionList();
                    if(TextUtils.equals(question.getQuestionType(),Integer.toString(Api.QUESTION_INPUT))
                            &&  optionList != null && optionList.size()>0){
                        for(QuestionOption option: optionList){
                            ai.add("");
                            types.add(0);
                        }
                    }else{  //简答题里没有optionlist默认加一个，单选和多选也默认加一个
                        ai.add("");
                        types.add(0);
                    }
                    answers.put(question.getQuestionId(),answer);
                }
            }
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, CpQuestionMultipleItem data) {
        int positon = helper.getAdapterPosition();
        switch (helper.getItemViewType()) {
            case CpQuestionMultipleItem.TYPE_TITLE_INPUT:
                renderInputTitle(helper,positon,data);
                break;
            case CpQuestionMultipleItem.TYPE_TITLE_SELECT:
                renderSelTitle(helper,positon,data);
                break;
            case CpQuestionMultipleItem.TYPE_OPTION_INPUT:
                renderInputOption(helper,positon,data);
                break;
            case CpQuestionMultipleItem.TYPE_OPTION_SELECT:
                renderSelOption(helper,positon,data);
                break;
            case CpQuestionMultipleItem.TYPE_OPTION_MULTIPLE:
                renderMultipleSelOption(helper,positon,data);
                break;
            case CpQuestionMultipleItem.TYPE_OPTION_SIMPLE:
                renderSimpleInput(helper,positon,data);
                break;
            default:
                renderInputTitle(helper,positon,data);
                break;
        }
    }

    private void renderInputTitle(BaseViewHolder helper,int position,CpQuestionMultipleItem data){
//        helper.setText(R.id.tv_subname,data.getData().getQuestionNum()+data.getData().getContent());
        TypefaceHolder.getInstance().setHt(helper.getView(R.id.tv_subname));
        RichTextViewHelper.setContent(helper.getView(R.id.tv_subname),
                RichTextViewHelper.makeQuestionTitle(data.getData().getQuestionNum(),data.getData().getContent()));
    }

    private void renderSelTitle(BaseViewHolder helper,int position,CpQuestionMultipleItem data){
//        helper.setText(R.id.tv_subname,data.getData().getQuestionNum()+data.getData().getContent());
        TypefaceHolder.getInstance().setHt(helper.getView(R.id.tv_subname));
        RichTextViewHelper.setContent(helper.getView(R.id.tv_subname),
                RichTextViewHelper.makeQuestionTitle(data.getData().getQuestionNum(),data.getData().getContent()));
    }

    private void renderInputOption(BaseViewHolder helper,int position,CpQuestionMultipleItem data){
        int index = helper.getAdapterPosition();
        QuestionOption option = data.getOption();
        boolean isCanvas =isCanvasMethod(position);
        TypefaceHolder.getInstance().setHt(helper.getView(R.id.tv_op_name));
        helper.setText(R.id.tv_op_name,option.getOption())
                .setGone(R.id.tv_op_name,!TextUtils.isEmpty(option.getOption()))
                .addOnClickListener(R.id.tv_canvas)
                .addOnClickListener(R.id.tv_camera)
//                .setVisible(R.id.tv_rechoice,isCanvas)
                .setText(R.id.tv_canvas,isCanvas
                        ?R.string.answer_with_edite:R.string.answer_with_canvas);


        EditText editText = helper.getView(R.id.et_input);
        TypefaceHolder.getInstance().setHt(editText);
        ImageView imageView = helper.getView(R.id.iv_canvas);
        Answer answer = answers.get(data.getData().getQuestionId());
        String initStr = "";
        if(answer !=null && answer.getAnswer() != null && data.getOptIndex()<answer.getAnswer().size()){
            initStr = answer.getAnswer().get(data.getOptIndex());
        }

        if(isCanvas){  //canvas 输入方式
            helper.setVisible(R.id.iv_canvas,true);
            helper.setVisible(R.id.et_input,false);
            imageView.setImageBitmap(Base64BitmapTransformor.getBitmap(initStr,helper.itemView.getContext()));
        }else{  //输入框方式
            helper.setVisible(R.id.et_input,true);
            helper.setVisible(R.id.iv_canvas,false);
            editText.setText(initStr);
        }

        editText.setOnFocusChangeListener(new BFocusChangeListener(index) {
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
                                    CpQuestionMultipleItem item = getData().get(mIndex);
                                    Answer answerEdite = answers.get(item.getData().getQuestionId());
                                    answerEdite.getAnswer().set(item.getOptIndex(),s.toString());
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
    }

    /**
     * 多选
     * @param helper
     * @param position
     * @param data
     */
    private void renderMultipleSelOption(BaseViewHolder helper,int position,CpQuestionMultipleItem data){
        int textColor;
        QuestionOption option = data.getOption();
        Question question = data.getData();

        List<String> selAnswers = answers.get(question.getQuestionId()).getAnswer();

        if(selAnswers.contains(option.getOption())){
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


        helper.setText(R.id.tv_op_name,option.getOption());

        HtmlTextView tvContent = (HtmlTextView)helper.getView(R.id.tv_op_content);
        TypefaceHolder.getInstance().setHt(tvContent);
        if(option.getOptionContent() != null){
            RichTextViewHelper.setContent(tvContent,option.getOptionContent());
        }
    }
    private void renderSelOption(BaseViewHolder helper,int position,CpQuestionMultipleItem data){
        int textColor;
        QuestionOption option = data.getOption();
        Question question = data.getData();
//        String selOptName = "";
        List<String> selAnswers = answers.get(question.getQuestionId()).getAnswer();
//        if(selAnswers != null){
//            selOptName = selAnswers.get(0);
//        }

        if(selAnswers.contains(option.getOption())){
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


        helper.setText(R.id.tv_op_name,option.getOption());

        HtmlTextView tvContent = (HtmlTextView)helper.getView(R.id.tv_op_content);
        TypefaceHolder.getInstance().setHt(tvContent);
        if(option.getOptionContent() != null){
            RichTextViewHelper.setContent(tvContent,option.getOptionContent());
        }
    }

    private void renderSimpleInput(BaseViewHolder helper,int position,CpQuestionMultipleItem data){
        helper.addOnClickListener(R.id.tv_canvas)
                .addOnClickListener(R.id.tv_camera);

        ImageView imageView = helper.getView(R.id.iv_canvas);
        Answer answer = answers.get(data.getData().getQuestionId());
        String initStr = "";
        if(answer !=null && answer.getAnswer() != null && data.getOptIndex()<answer.getAnswer().size()){
            initStr = answer.getAnswer().get(data.getOptIndex());
        }
        imageView.setImageBitmap(Base64BitmapTransformor.getBitmap(initStr,helper.itemView.getContext()));
    }
}

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
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.app.TypefaceHolder;
import com.example.df.zhiyun.mvp.model.adapterEntity.CpQuestionMultipleItem;
import com.example.df.zhiyun.mvp.model.entity.Answer;
import com.example.df.zhiyun.mvp.model.entity.QuestionOption;

import java.util.ArrayList;
import java.util.List;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.Base64BitmapTransformor;
import com.example.df.zhiyun.mvp.ui.widget.BFocusChangeListener;
import com.example.df.zhiyun.mvp.ui.widget.BTextWatcher;

/**
 * ================================================
 * 做作业里的填空题类型
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class HWinputAdapter extends BaseQuickAdapter<QuestionOption, BaseViewHolder> {
    private Answer mAnswer = new Answer();
    public Answer getAnswer(){
        return mAnswer;
    }

    public void setCanvasMethod(int position,boolean value){
        mAnswer.getTypes().set(position,value?1:0);
        mAnswer.getAnswer().set(position, "");
        notifyItemChanged(position);
    }

    public boolean isCanvasMethod(int position){
        return mAnswer.getTypes().get(position)==1;
    }


    public void setInitAnsers(Answer answer){
        if(answer != null){
            mAnswer = answer;
            if(mAnswer.getAnswer() == null || mAnswer.getAnswer().size() == 0){
                if(getData() != null){
                    fillEmptyData(getData());
                }
            }

            notifyDataSetChanged();
        }
    }


    public void insertBitmap(Bitmap bitmap,int position){
        mAnswer.getAnswer().set(position, Base64BitmapTransformor.getString(bitmap));
        notifyItemChanged(position);
    }


    public HWinputAdapter(@Nullable List<QuestionOption> data) {
        super(R.layout.item_question_input,data);
    }

    @Override
    public void setNewData(@Nullable List<QuestionOption> data) {
        super.setNewData(data);
        if(data != null){
            fillEmptyData(data);
        }
    }

    private void fillEmptyData(List<QuestionOption> data){
        List<String> ai = new ArrayList<>();
        List<Integer> types = new ArrayList<>();
        mAnswer = new Answer();
        mAnswer.setAnswer(ai);
        mAnswer.setTypes(types);
        for(QuestionOption questionOption: data){
            ai.add("");
            types.add(0);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, QuestionOption data) {
        int index = helper.getAdapterPosition();
        boolean isCanvas =isCanvasMethod(index);

        TypefaceHolder.getInstance().setHt(helper.getView(R.id.tv_op_name));
        helper.setText(R.id.tv_op_name,data.getOption())
                .setGone(R.id.tv_op_name, !TextUtils.isEmpty(data.getOption()))
                .addOnClickListener(R.id.tv_canvas)
                .addOnClickListener(R.id.tv_camera)
//                .setVisible(R.id.tv_rechoice,isCanvas)
                .setText(R.id.tv_canvas,isCanvas
                        ?R.string.answer_with_edite:R.string.answer_with_canvas);

        EditText editText = helper.getView(R.id.et_input);
        TypefaceHolder.getInstance().setEditTextHt(editText);
        ImageView imageView = helper.getView(R.id.iv_canvas);
        String initStr = mAnswer.getAnswer().get(index);
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
                                    if(!isCanvasMethod(mIndex)){
                                        mAnswer.getAnswer().set(mIndex,s.toString());
                                    }
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
}

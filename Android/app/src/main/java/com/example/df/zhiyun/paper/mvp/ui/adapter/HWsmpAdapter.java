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
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.mvp.model.entity.Answer;
import com.example.df.zhiyun.mvp.model.entity.QuestionOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.Base64BitmapTransformor;

/**
 * ================================================
 * 做作业里的简答题类型
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class HWsmpAdapter extends BaseQuickAdapter<QuestionOption, BaseViewHolder> {
    private Map<Integer,Bitmap> mBitmaps = new HashMap<>();
    private Answer mAnswer = new Answer();

    //没作答的选项要填充""来代表，
    public Answer getAnswer(){
        List<String> list = mAnswer.getAnswer();
        if(list != null){
            for(int i=0;i<list.size();i++){
                if(mBitmaps.get(i) != null){
                    list.set(i,Base64BitmapTransformor.getCompressString(mBitmaps.get(i)));
                }else{
                    list.set(i,"");
                }
            }
        }
        return mAnswer;
    }

    public void setInitAnsers(Answer answer){
        if(answer != null){
            mAnswer = answer;
            mBitmaps.clear();
            if(mAnswer.getAnswer() == null || mAnswer.getAnswer().size() == 0){
                fillEmptyData();
            }else{
                for(int i=0;i<mAnswer.getAnswer().size();i++){
                    mBitmaps.put(i,Base64BitmapTransformor.getBitmap(mAnswer.getAnswer().get(i)));
                }
            }


            notifyDataSetChanged();
        }
    }


    public void insertBitmap(Bitmap bitmap,int position){
        mBitmaps.put(position,bitmap);
        notifyItemChanged(position);
    }


    public HWsmpAdapter(@Nullable List<QuestionOption> data) {
        super(R.layout.item_question_simple_answer,data);
    }

    @Override
    public void setNewData(@Nullable List<QuestionOption> data) {
        super.setNewData(data);
        fillEmptyData();
    }

    private void fillEmptyData(){
        List<QuestionOption> data = getData();
        if(data != null){
            List<String> ai = new ArrayList<>();
            List<Integer> types = new ArrayList<>();
            mAnswer.setAnswer(ai);
            mAnswer.setTypes(types);
            for(QuestionOption questionOption: data){
                ai.add("");
                types.add(0);
            }
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, QuestionOption data) {
        helper.addOnClickListener(R.id.tv_canvas)
                .addOnClickListener(R.id.tv_camera);

        ImageView imageView = helper.getView(R.id.iv_canvas);
        if(mBitmaps.get(helper.getAdapterPosition()) != null){
            imageView.setImageBitmap(mBitmaps.get(helper.getAdapterPosition()) );
        }else{
            imageView.setImageBitmap(null);
        }
    }
}

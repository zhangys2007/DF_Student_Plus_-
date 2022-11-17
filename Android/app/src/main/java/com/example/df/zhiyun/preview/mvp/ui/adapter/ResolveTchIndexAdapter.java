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
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.R;

import java.util.List;

/**
 * ================================================
 * 班级作业解析页面的题号列表
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class ResolveTchIndexAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    private int mSelIndex = -1;

    public void setSelIndex(int index){
        if(index <0 || index >= getData().size() || index == mSelIndex){
            return;
        }
        notifyItemChanged(mSelIndex);
        mSelIndex = index;
        notifyItemChanged(mSelIndex);
    }

    public ResolveTchIndexAdapter(@Nullable List<Integer> data) {
        super(R.layout.item_question_index,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer data) {
        helper.setText(R.id.tv_answer_index,Integer.toString(data));
        if(mSelIndex == helper.getAdapterPosition()){
            helper.setTextColor(R.id.tv_answer_index,
                    ContextCompat.getColor(mContext,R.color.white));
            helper.setBackgroundRes(R.id.tv_answer_index,R.drawable.shape_circle_cyan);
            helper.setVisible(R.id.v_bottom,true);
        }else{
            helper.setTextColor(R.id.tv_answer_index,
                    ContextCompat.getColor(mContext,R.color.text_333));
            helper.setBackgroundRes(R.id.tv_answer_index,R.drawable.shape_circle_gray);
            helper.setVisible(R.id.v_bottom,false);
        }
    }
}

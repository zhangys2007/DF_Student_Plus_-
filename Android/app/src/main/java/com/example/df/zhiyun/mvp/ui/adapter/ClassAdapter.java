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

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.base.DefaultAdapter;
import com.example.df.zhiyun.mvp.model.entity.ClassBrief;

import java.util.List;

import com.example.df.zhiyun.R;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法   班级列表
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class ClassAdapter extends BaseQuickAdapter<ClassBrief, BaseViewHolder> {
    private String strIntro;
    private String strInfo;

    public ClassAdapter(@Nullable List<ClassBrief> data) {
        super(R.layout.item_class,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassBrief data) {
        Context context = helper.itemView.getContext();
        if(strInfo == null || strIntro == null){
            strInfo = context.getString(R.string.class_info);
            strIntro = context.getString(R.string.class_intro);
        }

        helper.setText(R.id.tv_class_name,data.getClassName())
        .setText(R.id.tv_class_other, String.format(strInfo,data.getCreater(),data.getClassId(),data.getMemberCount()))
        .setText(R.id.tv_class_brief,String.format(strIntro,data.getClassInfo()))
        .setText(R.id.tv_status,data.getIsPass()==1?R.string.pass:R.string.un_pass)
        .setTextColor(R.id.tv_status,ContextCompat.getColor(context,data.getIsPass()==1?R.color.green:R.color.colorPrimary));
    }
}

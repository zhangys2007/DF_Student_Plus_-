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
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.model.entity.ClassItem;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法 班级列表
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class ClassItemAdapter extends BaseQuickAdapter<ClassItem, BaseViewHolder> {
    public ClassItemAdapter(@Nullable List<ClassItem> data) {
        super(R.layout.item_grade,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassItem data) {
        StringBuilder builder = new StringBuilder();
        if(data.getIsclass() == 1){
            builder.append("创建人: ").append(data.getClassCreate()).append("   班号: ")
                    .append(data.getClassNumber()).append("   成员: ").append(data.getMembersNumber());
            helper.setGone(R.id.tv_name,true)
                    .setVisible(R.id.iv_arrow,true);
        }else{
            builder.append("创建人: ").append(data.getClassCreate());
            helper.setGone(R.id.tv_name,false)
            .setVisible(R.id.iv_arrow,false);
        }

        helper.setText(R.id.tv_name,data.getClassName())
        .setText(R.id.tv_brief,builder.toString())
        .setText(R.id.tv_desc, TextUtils.isEmpty(data.getClassSynopsis())?"班级简介：暂无简介":"班级简介："+data.getClassSynopsis());
    }

}

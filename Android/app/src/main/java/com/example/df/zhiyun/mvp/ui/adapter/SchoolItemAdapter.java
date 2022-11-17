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

import com.example.df.zhiyun.mvp.model.entity.SchoolItem;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法 学校列表
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class SchoolItemAdapter extends BaseQuickAdapter<SchoolItem, BaseViewHolder> {
    public SchoolItemAdapter(@Nullable List<SchoolItem> data) {
        super(R.layout.item_school,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SchoolItem data) {
        StringBuilder builder = new StringBuilder();
        builder.append("创建人: ").append(data.getCreateName()).append("   学校成员: ")
                .append(data.getSchoolMemberNumber()).append("   年级数: ").append(data.getGraderNumber());
        helper.setText(R.id.tv_name,data.getSchoolName())
        .setText(R.id.tv_brief,builder.toString())
        .setText(R.id.tv_desc, TextUtils.isEmpty(data.getSchoolSynopsis())?"学校简介：暂无简介":"学校简介："+data.getSchoolSynopsis());
    }

}

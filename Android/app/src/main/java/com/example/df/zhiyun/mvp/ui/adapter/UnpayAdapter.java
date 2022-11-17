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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.model.entity.StudentHomework;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法   未交作业学生列表
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class UnpayAdapter extends BaseQuickAdapter<StudentHomework, BaseViewHolder> {

    public UnpayAdapter(@Nullable List<StudentHomework> data) {
        super(R.layout.item_unpay_student,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StudentHomework data) {
        helper.setText(R.id.tv_name,data.getStudentName());
    }

}

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
package com.example.df.zhiyun.plan.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.plan.mvp.model.entity.Template;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法 教案列表
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class TemplateAdapter extends BaseQuickAdapter<Template, BaseViewHolder> {
    public TemplateAdapter(@Nullable List<Template> data) {
        super(R.layout.item_plan_cloud,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Template data) {
        helper.setText(R.id.tv_template_name,data.getName())
            .setText(R.id.tv_time,data.getTime());
//            .setText(R.id.tv_publisher,data.getPress());
        Glide.with(helper.itemView.getContext())
                .load(data.getCover())
                .into((ImageView) helper.getView(R.id.iv_cover));

//        Glide.with(helper.itemView.getContext())
//                .load(Base64BitmapTransformor.getBitmap(data.getCover()))
//                .into((ImageView) helper.getView(R.id.iv_cover));
    }

}

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
package com.example.df.zhiyun.analy.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.Base64BitmapTransformor;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.mvp.model.entity.GrowthTraceItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * ================================================
 * 学生成长轨迹列表
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class GrowthTraceItemAdapter extends BaseQuickAdapter<GrowthTraceItem, BaseViewHolder> {
    public GrowthTraceItemAdapter(@Nullable List<GrowthTraceItem> data) {
        super(R.layout.item_growth_trace,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GrowthTraceItem data) {
        helper.setText(R.id.tv_name,data.getRealName())
            .setText(R.id.tv_grade, StringCocatUtil.concat("年级：",data.getGradeName()))
            .setText(R.id.tv_class, StringCocatUtil.concat("班级：",data.getClassName()));
        ((CircleImageView)helper.getView(R.id.civ_thumb)).setImageBitmap(Base64BitmapTransformor.getThumb(
                data.getPicture(),helper.itemView.getContext()));
    }

}

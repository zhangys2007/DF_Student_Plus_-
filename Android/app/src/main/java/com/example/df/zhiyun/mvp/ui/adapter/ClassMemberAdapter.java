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
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.mvp.model.entity.MemberItem;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.app.utils.Base64BitmapTransformor;
import com.example.df.zhiyun.mvp.model.entity.ClassMember;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
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
public class ClassMemberAdapter extends BaseQuickAdapter<MemberItem, BaseViewHolder> {
    private String strQuote;
    private String strNoEmail;
    private ImageLoader mImageLoader;

    public ClassMemberAdapter(@Nullable List<MemberItem> data) {
        super(R.layout.item_class_member,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MemberItem data) {
        Context context = helper.itemView.getContext();
        if(strQuote == null || strNoEmail == null){
            strQuote = context.getString(R.string.quote);
            strNoEmail = context.getString(R.string.has_no_email);
        }
        if(mImageLoader == null){
            mImageLoader = ArmsUtils.obtainAppComponentFromContext(helper.itemView.getContext()).imageLoader();
        }

        if(data == null){
            return;
        }

        if(TextUtils.isEmpty(data.getSubjects())){
            helper.setText(R.id.tv_title, "");
        }else{
            helper.setText(R.id.tv_title, String.format(strQuote, data.getSubjects()));
        }

        helper.setText(R.id.tv_name,data.getName())

                .setText(R.id.tv_email, TextUtils.isEmpty(data.getEmail())?strNoEmail:data.getEmail());
        ((CircleImageView)helper.getView(R.id.civ_thumb)).setImageBitmap(Base64BitmapTransformor.getThumb(
                data.getImage(),helper.itemView.getContext()));
    }

}

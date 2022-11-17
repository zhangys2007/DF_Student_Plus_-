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
import com.jess.arms.http.imageloader.ImageLoader;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.mvp.model.entity.Post;

import java.util.List;

import com.example.df.zhiyun.R;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法   公告
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class PostAdapter extends BaseQuickAdapter<Post, BaseViewHolder> {
    private String strQuote;
    private String strNoEmail;
    private ImageLoader mImageLoader;

    public PostAdapter(@Nullable List<Post> data) {
        super(R.layout.item_post,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Post data) {
        String strTime = "";
        try {
            long time = Long.parseLong(data.getMoticeTime());
            strTime = TimeUtils.getYmdhm(time);
        }catch (Exception e){

        }

        helper.setText(R.id.tv_time,strTime)
        .setText(R.id.tv_content, data.getNoticeContent())
        .setVisible(R.id.v_red, data.getStatus()==Post.TYPE_UNREAD);
    }
}

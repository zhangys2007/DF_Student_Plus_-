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
package com.example.df.zhiyun.main.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.Base64BitmapTransformor;
import com.example.df.zhiyun.app.utils.CompoundDrawableUtil;
import com.example.df.zhiyun.main.mvp.model.entity.FocusStd;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法   关注的学生
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class FocusStdAdapter extends BaseQuickAdapter<FocusStd, BaseViewHolder> {
    private int[] stateRes = {R.string.score_sink,R.string.score_rise,R.string.score_none};
    private int[] iconRes = {R.mipmap.ic_down,R.mipmap.ic_up,R.mipmap.ic_h};
    public FocusStdAdapter(@Nullable List<FocusStd> data) {
        super(R.layout.item_focus_std,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FocusStd data) {
        ImageView iv = (ImageView)helper.getView(R.id.iv_thumb_std);
        Glide.with(mContext)
                .load(Base64BitmapTransformor.getBitmap(data.getPictureBase64()))
                .placeholder(R.mipmap.thumb)
                .into(iv);

        helper.setText(R.id.tv_name_std,data.getRealName())
                .setText(R.id.tv_cls_std,data.getClassName())
                .setText(R.id.tv_numb,""+data.getRink());

        TextView tvState = (TextView) helper.getView(R.id.tv_state);
        tvState.setText(stateRes[data.getRise()]);
        CompoundDrawableUtil.setCompoundDrawable(mContext,tvState,iconRes[data.getRise()],
                0,0,0);
    }
}

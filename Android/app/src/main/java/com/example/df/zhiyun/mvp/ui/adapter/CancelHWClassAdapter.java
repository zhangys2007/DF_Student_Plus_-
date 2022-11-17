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

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.educate.mvp.model.entity.BelongClass;
import com.jess.arms.base.DefaultAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法   布置作业时选择班级列表
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class CancelHWClassAdapter extends BaseQuickAdapter<BelongClass, BaseViewHolder> {
    private Map<Integer,Boolean> selItems = new HashMap<>();
    Drawable check ;
    Drawable uncheck ;
    public void clickItem(int position){
        int isArrangement = getData().get(position).getIsArrangement();
        if(isArrangement != 1){
            return;
        }

        if(selItems.containsKey(position)){
            selItems.remove(position);
        }else{
            selItems.put(position,true);
        }
        notifyItemChanged(position);
    }

    public String getSelectedClasses(){
        StringBuilder builder = new StringBuilder("");
        for(Integer position: selItems.keySet()){
            if(builder.length()>0){
                builder.append(",");
            }
            builder.append(getData().get(position).getClassId());
        }

        return builder.toString();
    }

    public CancelHWClassAdapter(@Nullable List<BelongClass> data) {
        super(R.layout.item_class_sel,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BelongClass data) {
        if(check == null){
            check = ContextCompat.getDrawable(helper.itemView.getContext(),R.mipmap.check);
            check.setBounds(0,0,check.getIntrinsicWidth(),check.getIntrinsicHeight());
        }
        if(uncheck == null){
            uncheck = ContextCompat.getDrawable(helper.itemView.getContext(),R.mipmap.uncheck);
            uncheck.setBounds(0,0,uncheck.getIntrinsicWidth(),uncheck.getIntrinsicHeight());
        }

//        TextView tvName = helper.getView(R.id.rb_class);
//        tvName.setText(data.getClassName());
//        if(data.getIsArrangement() != 1 ){
//            tvName.setCompoundDrawables(uncheck,null,null,null);
//            tvName.setTextColor(ContextCompat.getColor(helper.itemView.getContext(),R.color.text_999));
//        }else if(selItems.containsKey(helper.getAdapterPosition())){
//            tvName.setCompoundDrawables(check,null,null,null);
//            tvName.setTextColor(ContextCompat.getColor(helper.itemView.getContext(),R.color.text_666));
//        }else{
//            tvName.setCompoundDrawables(uncheck,null,null,null);
//            tvName.setTextColor(ContextCompat.getColor(helper.itemView.getContext(),R.color.text_666));
//        }
    }

}

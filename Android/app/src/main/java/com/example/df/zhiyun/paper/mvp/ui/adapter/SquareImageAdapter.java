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
package com.example.df.zhiyun.paper.mvp.ui.adapter;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.base.DefaultAdapter;
import com.example.df.zhiyun.app.utils.Base64BitmapTransformor;
import com.example.df.zhiyun.mvp.contract.DoHomeworkContract;
import com.example.df.zhiyun.mvp.model.entity.Answer;
import com.example.df.zhiyun.paper.mvp.ui.fragment.HWcomFragment;

import java.util.ArrayList;
import java.util.List;

import com.example.df.zhiyun.R;

import timber.log.Timber;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法   正方形图片列表,没满3个，就在最后添一个""代表添加按钮,  "1"代表有图片
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class SquareImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements DoHomeworkContract {
    private List<Bitmap> mBitmaps = new ArrayList<>();


    public int getImageCount(){
        return mBitmaps.size();
    }

    @Override
    public void initeWhiteAnswer(Answer answer) {
        if(answer ==null || answer.getAnswer() == null){
            return;
        }

        getData().clear();
        mBitmaps.clear();
        Bitmap temp;
        for(int i=0;i<answer.getAnswer().size();i++){
            String data = answer.getAnswer().get(i);
            if(!TextUtils.isEmpty(data)){
                temp = Base64BitmapTransformor.getBitmap(data);
                if(temp != null){
                    getData().add("1");
                    mBitmaps.add(temp);
                }
            }
        }

        if(getData().size() < HWcomFragment.MAX_PIC_MUN){
            getData().add("");
        }
        notifyDataSetChanged();
    }

    @Override
    public Answer getAnswer() {
        Answer answer = new Answer();
        List<String> strImgs = new ArrayList<>();

        if(mBitmaps.size() == 0){
            strImgs.add("");
        }else{
            for(int i=0;i<mBitmaps.size();i++){
                strImgs.add(Base64BitmapTransformor.getCompressString(mBitmaps.get(i)));
            }
        }
        Timber.tag("submitQuestion").d("strImgs:"+ strImgs.size());
        answer.setAnswer(strImgs);
        return answer;
    }

    public void removeImageItem(int position){
        if(mBitmaps.size() == HWcomFragment.MAX_PIC_MUN){
            getData().add("");
        }
        mBitmaps.remove(position);
        getData().remove(position);
        notifyDataSetChanged();
    }

    public void inserteImageItem(List<Bitmap> listBitmap){
        if(listBitmap == null || listBitmap.size() == 0){
            return;
        }

        getData().remove(getData().size()-1);  //去掉最后一张添加按钮项
        int remainCount = HWcomFragment.MAX_PIC_MUN - mBitmaps.size();  //可以添加的张数
        for(int i=0;i<listBitmap.size() && i<remainCount ;i++){
            mBitmaps.add(listBitmap.get(i));
            getData().add("1");
        }

        if(mBitmaps.size() < HWcomFragment.MAX_PIC_MUN){
            getData().add("");
        }

        notifyDataSetChanged();
    }

    public SquareImageAdapter(@Nullable List<String> data) {
        super(R.layout.item_img,data);
        data.add("");
    }

    @Override
    protected void convert(BaseViewHolder helper, String data) {
        boolean isAddItem = TextUtils.isEmpty(data);
        helper.addOnClickListener(R.id.ib_img_del)
                .setVisible(R.id.iv_image,!isAddItem)
                .setVisible(R.id.ib_img_del,!isAddItem);
        if(!isAddItem){
            ImageView imageView = helper.getView(R.id.iv_image);
            imageView.setImageBitmap(mBitmaps.get(helper.getAdapterPosition()));
        }
    }
}

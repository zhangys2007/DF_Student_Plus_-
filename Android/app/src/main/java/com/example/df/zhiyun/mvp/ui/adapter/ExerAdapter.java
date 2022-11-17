package com.example.df.zhiyun.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.mvp.model.entity.Exer;

import java.util.List;

import com.example.df.zhiyun.R;

/***
 * 我的练习列表
 */
public class ExerAdapter extends BaseQuickAdapter<Exer,BaseViewHolder> {

    public ExerAdapter(@Nullable List<Exer> data) {
        super(R.layout.item_exer,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Exer item) {
        String strTime = "";
        String strLength = "00:00";
        try {
            long time = Long.parseLong(item.getTime());
            strTime = TimeUtils.getYmdhm(time);

            long length = Math.abs(Long.parseLong(item.getTimeLength()));
            strLength = TimeUtils.formatMusicTime(length);
        }catch (Exception e){

        }
        helper.setText(R.id.tv_time_duration,strLength)
                .setText(R.id.tv_subject, item.getSubject())
                .setText(R.id.tv_name,item.getName())
                .setText(R.id.tv_time, strTime);
    }
}

package com.example.df.zhiyun.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.mvp.model.entity.Homework;

import java.util.List;

public class HomeworkOldAdapter extends BaseQuickAdapter<Homework,BaseViewHolder> {

    public HomeworkOldAdapter(@Nullable List<Homework> data) {
        super(R.layout.item_homework_old,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Homework item) {
        try {
            helper.setText(R.id.tv_do,mContext.getString(item.getStatus()==0?R.string.undo:R.string.done))
                    .setText(R.id.tv_subjuet_name, item.getSubject())
                    .setText(R.id.tv_correct,mContext.getString(item.getCorrectionStatus()==0?R.string.uncorrected:R.string.corrected))
                    .setText(R.id.tv_homewwork_name,item.getHomeworkName())
                    .setText(R.id.tv_time, TimeUtils.getYmdhm(Long.parseLong(item.getEndTime())))
                    .setVisible(R.id.ib_report,item.getCorrectionStatus()==1)
                    .addOnClickListener(R.id.ib_detail)
                    .addOnClickListener(R.id.ib_report);
        }catch (NumberFormatException e){

        }

    }
}

package com.example.df.zhiyun.educate.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.R;

import java.util.List;

/**
 * 教材选择页面
 */
public class TextbookSelAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public TextbookSelAdapter(@Nullable List<String> data) {
        super(R.layout.item_version_item,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String data) {
        TextView tvName = helper.getView(R.id.tv_name);
        tvName.setText(data);
        if(helper.getAdapterPosition() == 0){
            tvName.setTextColor(ContextCompat.getColor(mContext,R.color.white));
            tvName.setBackgroundResource(R.drawable.shape_round_cyan);
        }else{
            tvName.setTextColor(ContextCompat.getColor(mContext,R.color.text_666));
            tvName.setBackgroundResource(R.drawable.shape_round_grey);
        }

    }
}

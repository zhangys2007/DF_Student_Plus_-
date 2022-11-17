package com.example.df.zhiyun.educate.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.R;

import java.util.List;

/**
 * 双向细目表
 */
public class DualTableAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public DualTableAdapter(@Nullable List<String> data) {
        super(R.layout.item_dual_table,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String data) {

    }
}

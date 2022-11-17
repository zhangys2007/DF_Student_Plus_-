package com.example.df.zhiyun.mvp.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.mvp.model.adapterEntity.SignedInfoMultipleItem;

import java.util.List;

import com.example.df.zhiyun.R;

/***
 * 签到日期的adapter
 */
public class SignedAdapter extends BaseMultiItemQuickAdapter<SignedInfoMultipleItem, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public SignedAdapter(List<SignedInfoMultipleItem> data) {
        super(data);
        addItemType(SignedInfoMultipleItem.TYPE_TITLE, R.layout.item_signed_date);
        addItemType(SignedInfoMultipleItem.TYPE_EMPTY, R.layout.item_signed_date);
        addItemType(SignedInfoMultipleItem.TYPE_DATE, R.layout.item_signed_date);
    }

    @Override
    protected void convert(BaseViewHolder helper, SignedInfoMultipleItem item) {
        switch (helper.getItemViewType()) {
            case SignedInfoMultipleItem.TYPE_TITLE:
                renderTitle(helper,item);
                break;
            case SignedInfoMultipleItem.TYPE_DATE:
                renderDate(helper,item);
                break;
            default:
                renderEmpty(helper,item);
                break;
        }
    }

    private void renderTitle(BaseViewHolder helper, SignedInfoMultipleItem item){
        TextView textView = helper.getView(R.id.tv_date);
        textView.setText(item.getTag());
        textView.setSelected(false);
    }

    private void renderDate(BaseViewHolder helper, SignedInfoMultipleItem item){
        TextView textView = helper.getView(R.id.tv_date);
        textView.setText(item.getTag());
        textView.setSelected(item.getTime()!= 0);
    }

    private void renderEmpty(BaseViewHolder helper, SignedInfoMultipleItem item){
        TextView textView = helper.getView(R.id.tv_date);
        textView.setText(item.getTag());
        textView.setSelected(false);
    }
}

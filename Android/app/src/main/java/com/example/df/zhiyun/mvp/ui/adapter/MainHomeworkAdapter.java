package com.example.df.zhiyun.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.mvp.model.adapterEntity.MainHomeworkMultipleItem;
import com.example.df.zhiyun.mvp.model.entity.Homework;

import java.util.List;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.TimeUtils;

/***
 * 主页上的那三个最新作业的adapter
 */
public class MainHomeworkAdapter extends BaseMultiItemQuickAdapter<MainHomeworkMultipleItem, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MainHomeworkAdapter(List<MainHomeworkMultipleItem> data) {
        super(data);
        addItemType(MainHomeworkMultipleItem.TYPE_EMPTY, R.layout.item_main_hw_empty);
        addItemType(MainHomeworkMultipleItem.TYPE_TOP, R.layout.item_main_hw_top);
        addItemType(MainHomeworkMultipleItem.TYPE_SUB, R.layout.item_main_hw_top);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainHomeworkMultipleItem item) {
        Homework data = item.getData();
        switch (helper.getItemViewType()) {
            case MainHomeworkMultipleItem.TYPE_EMPTY:
                renderEmpty(helper);
                break;
            case MainHomeworkMultipleItem.TYPE_TOP:
                renderTopItem(helper,data);
                break;
            case MainHomeworkMultipleItem.TYPE_SUB:
                renderSubItem(helper,data);
                break;
            default:
                renderEmpty(helper);
                break;
        }
    }

    private void renderTopItem(BaseViewHolder helper, Homework item){
        helper.setText(R.id.tv_hw_status,item.getStatus()==0?R.string.undo:R.string.done)
                .setText(R.id.tv_hw_subject,item.getSubject())
                .setText(R.id.tv_hw_name,item.getHomeworkName())
                .setGone(R.id.iv_start,true);
        try {
            helper.setText(R.id.tv_hw_time, TimeUtils.getYmdDot(Long.parseLong(item.getBeginTime())));
        }catch (NumberFormatException e){

        }
    }

    private void renderSubItem(BaseViewHolder helper, Homework item){
        renderTopItem(helper,item);
        helper.setGone(R.id.iv_start,false);
    }

    private void renderEmpty(BaseViewHolder helper){

    }
}

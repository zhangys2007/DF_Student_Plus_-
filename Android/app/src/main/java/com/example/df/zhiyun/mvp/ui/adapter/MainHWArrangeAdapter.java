package com.example.df.zhiyun.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.mvp.model.adapterEntity.MainHWPutMultipleItem;

import java.util.List;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.mvp.model.adapterEntity.MainHomeworkMultipleItem;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.HomeworkArrange;

/***
 * 主页上的那三个最新布置的作业的adapter
 */
public class MainHWArrangeAdapter extends BaseMultiItemQuickAdapter<MainHWPutMultipleItem, BaseViewHolder> {

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public MainHWArrangeAdapter(List<MainHWPutMultipleItem> data) {
        super(data);
        addItemType(MainHomeworkMultipleItem.TYPE_EMPTY, R.layout.item_main_hw_empty);
        addItemType(MainHomeworkMultipleItem.TYPE_TOP, R.layout.item_main_hw_top_tch);
        addItemType(MainHomeworkMultipleItem.TYPE_SUB, R.layout.item_main_hw_bottom_tch);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainHWPutMultipleItem item) {
        HomeworkArrange data = item.getData();
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

    private void renderTopItem(BaseViewHolder helper, HomeworkArrange item){
        helper.setText(R.id.tv_hw_complete,item.getHomeworkStatus()== Api.FINISH?R.string.complete:R.string.uncomplete)
                .setText(R.id.tv_count_corrected,""+item.getApprovedNumber())
                .setText(R.id.tv_count_uncorrected,""+item.getUnApprovedNumber())
                .setText(R.id.tv_count_unsubmit,""+item.getUnpaidNumber())
                .setText(R.id.tv_hw_name,item.getHomeworkName())
                ;
        try {
            helper.setText(R.id.tv_hw_time, TimeUtils.getYmdDot(Long.parseLong(item.getCreateTime())));
        }catch (NumberFormatException e){

        }
        helper.addOnClickListener(R.id.ll_corrected);
        helper.addOnClickListener(R.id.ll_uncorrected);
        helper.addOnClickListener(R.id.ll_unsubmit);
        helper.addOnClickListener(R.id.iv_start);
    }

    private void renderSubItem(BaseViewHolder helper, HomeworkArrange item){
        helper.setText(R.id.tv_hw_complete,item.getHomeworkStatus()== Api.FINISH?R.string.complete:R.string.uncomplete)
                .setText(R.id.tv_count_corrected,""+item.getApprovedNumber())
                .setText(R.id.tv_count_uncorrected,""+item.getUnApprovedNumber())
                .setText(R.id.tv_count_unsubmit,""+item.getUnpaidNumber())
                .setText(R.id.tv_hw_name,item.getHomeworkName())
                ;
        try {
            helper.setText(R.id.tv_hw_time, TimeUtils.getYmdDot(Long.parseLong(item.getCreateTime())));
        }catch (NumberFormatException e){

        }
        helper.addOnClickListener(R.id.ll_corrected);
        helper.addOnClickListener(R.id.ll_uncorrected);
        helper.addOnClickListener(R.id.ll_unsubmit);
    }

    private void renderEmpty(BaseViewHolder helper){

    }
}

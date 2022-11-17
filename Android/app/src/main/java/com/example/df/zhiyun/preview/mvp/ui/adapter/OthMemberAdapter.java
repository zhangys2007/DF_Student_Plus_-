package com.example.df.zhiyun.preview.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.common.mvp.model.entity.CommonExpandableItem;

import java.util.List;

/**
 * 班级题目分析页面里的除了选择题  有做错和做对的学生
 */
public class OthMemberAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public OthMemberAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(0, R.layout.item_oth_member_title);
        addItemType(1, R.layout.item_option_member_member);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        if(((CommonExpandableItem)item).getLevel() == 0){
            renderTitle(helper,item);
        }else{
            renderMember(helper,item);
        }
    }

    /**
     * 渲染学生
     * @param item
     */
    private void renderMember(BaseViewHolder helper, MultiItemEntity item) {
        helper.setText(R.id.tv_member,"赵敏");
    }

    /**
     * 渲染选项
     * @param item
     */
    private void renderTitle(BaseViewHolder helper, MultiItemEntity item) {
        helper.setText(R.id.tv_title,"作答正确人数：8人");
    }
}

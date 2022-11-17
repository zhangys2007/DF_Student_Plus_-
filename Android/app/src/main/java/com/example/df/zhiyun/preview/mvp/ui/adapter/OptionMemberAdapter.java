package com.example.df.zhiyun.preview.mvp.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.CompoundDrawableUtil;
import com.example.df.zhiyun.common.mvp.model.entity.CommonExpandableItem;

import java.util.List;

/**
 * 班级题目分析页面里的选择题  有选项和选这个选项的学生
 */
public class OptionMemberAdapter  extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public OptionMemberAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(0, R.layout.item_option_member_opt);
        addItemType(1, R.layout.item_option_member_member);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        if(((CommonExpandableItem)item).getLevel() == 0){
            renderOption(helper,item);
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
    private void renderOption(BaseViewHolder helper, MultiItemEntity item) {
        helper.setText(R.id.tv_option,"A");
        helper.setText(R.id.tv_numb,"50人");
        helper.setProgress(R.id.pb_numb,50);
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = helper.getAdapterPosition();
                if(((CommonExpandableItem)item).isExpanded()){
                    collapse(pos);
                }else{
                    expand(pos);
                }
            }
        });

        TextView tvNubmer = helper.getView(R.id.tv_numb);
        if(((CommonExpandableItem)item).isExpanded()){
            CompoundDrawableUtil.setCompoundDrawable(mContext,
                    tvNubmer,0,0,R.mipmap.arrow_up_grey,0);
        }else{
            CompoundDrawableUtil.setCompoundDrawable(mContext,
                    tvNubmer,0,0,R.mipmap.arrow_down_grey,0);
        }
    }
}

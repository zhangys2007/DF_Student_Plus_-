package com.example.df.zhiyun.mvp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.mvp.model.entity.CategoryMain;
import com.example.df.zhiyun.mvp.model.entity.CategoryNode;

import java.util.List;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.model.adapterEntity.CategoryMainMultipleItem;
import com.example.df.zhiyun.mvp.model.adapterEntity.CategorySubMultipleItem;

public class ExpandableCategoryAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int LEVEL_0 = 0;
    public static final int LEVEL_1 = 1;

    public ExpandableCategoryAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(LEVEL_0, R.layout.item_category_main);
        addItemType(LEVEL_1, R.layout.item_category_sub);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()){
            case 0:
                renderCategory0(helper,item);
                break;
            case 1:
                renderCategory1(helper,item);
                break;
        }
    }

    private void renderCategory0(BaseViewHolder helper, MultiItemEntity item){
        final CategoryMainMultipleItem data = (CategoryMainMultipleItem) item;
        CategoryMain categoryMain = (CategoryMain)(data.getData());

        int numb = categoryMain.getCount();
        boolean isExpanded = data.isExpanded();

        helper.setText(R.id.tv_category_name_0,categoryMain.getName());
        helper.setText(R.id.tv_question_numb,""+numb+"题");
        helper.setImageResource(R.id.iv_add_red,isExpanded?R.mipmap.sub_red:R.mipmap.add_red);
        helper.setVisible(R.id.v_add_line_down,isExpanded);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = helper.getAdapterPosition();
                if(data.isExpanded()){
                    collapse(pos);
                }else{
                    expand(pos);
                }
            }
        });
    }

    private void renderCategory1(BaseViewHolder helper, MultiItemEntity item){
        final CategorySubMultipleItem data = (CategorySubMultipleItem) item;
        CategoryNode categoryNode = (CategoryNode)(data.getData());

        helper.setText(R.id.tv_category_name_1,categoryNode.getName());
        helper.setVisible(R.id.v_dot_line_down,!data.isLast());
        helper.addOnClickListener(R.id.tv_question_practice);
    }
}

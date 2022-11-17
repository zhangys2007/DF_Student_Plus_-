package com.example.df.zhiyun.mvp.ui.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.mvp.model.entity.VersionPublisher;

import java.util.List;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.model.adapterEntity.VersionItemMultipleItem;
import com.example.df.zhiyun.mvp.model.adapterEntity.VersionPublisherMultipleItem;
import com.example.df.zhiyun.mvp.model.entity.VersionItem;

public class VersionAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int LEVEL_0 = 0;
    public static final int LEVEL_1 = 1;

    public VersionAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(LEVEL_0, R.layout.item_version_publisher);
        addItemType(LEVEL_1, R.layout.item_version_item);
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
        final VersionPublisherMultipleItem data = (VersionPublisherMultipleItem) item;
        VersionPublisher versionPublisher = (VersionPublisher)(data.getData());
        helper.setText(R.id.tv_name,versionPublisher.getName())
            .setImageResource(R.id.iv_version_arrow,data.isExpanded()?R.mipmap.arrow_up_grey:R.mipmap.arrow_down_grey);

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
        final VersionItemMultipleItem data = (VersionItemMultipleItem) item;
        VersionItem versionItem = (VersionItem)(data.getData());
        helper.setText(R.id.tv_name,versionItem.getName());
    }
}

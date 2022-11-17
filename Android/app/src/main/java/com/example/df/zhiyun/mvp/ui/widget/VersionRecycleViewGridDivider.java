package com.example.df.zhiyun.mvp.ui.widget;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import com.example.df.zhiyun.mvp.model.adapterEntity.VersionItemMultipleItem;

/**
 * Created by _SOLID
 * Date:2016/10/8
 * Time:16:50
 * Desc:
 */


public class VersionRecycleViewGridDivider extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private int mDividerWidth;

    public VersionRecycleViewGridDivider(int height, @ColorInt int color) {
        mDividerWidth = height;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int itemPosition = (Integer) (view.getTag());
        BaseMultiItemQuickAdapter adapter = (BaseMultiItemQuickAdapter)parent.getAdapter();
        MultiItemEntity multiItemEntity = (MultiItemEntity)adapter.getData().get(itemPosition);

        if(multiItemEntity.getItemType() == 0){
            outRect.set(0, 0, 0, 0);
        }else{
            int subIndex = ((VersionItemMultipleItem)multiItemEntity).getSelIndex();
            if(subIndex <= 2){
                outRect.set(mDividerWidth, 0, mDividerWidth, 0);
            }else{
                outRect.set(mDividerWidth, mDividerWidth, mDividerWidth, 0);
            }
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }
}

package com.example.df.zhiyun.mvp.ui.widget;

import android.app.Activity;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.ui.widget.smartPopupWindow.SmartPopupWindow;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DeviceUtils;

public class PopupWindowHelper {

    /**
     * 在指定anchor下展开的列表弹出窗
     * @param anchor
     * @return
     */
    public static SmartPopupWindow getFilterContentPopupWindow(View anchor, BaseQuickAdapter adapter,BaseQuickAdapter.OnItemClickListener listener){
        if(anchor == null){
            return null;
        }

        SmartPopupWindow popupWindow;
        int[] location = new int[2];
        Rect rect = new Rect();

        anchor.getWindowVisibleDisplayFrame(rect);
        anchor.getLocationInWindow(location);
        int height = rect.height() - (location[1]+anchor.getHeight()- DeviceUtils.getStatuBarHeight(anchor.getContext()));

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                height);
        RecyclerView recyclerView = new RecyclerView(anchor.getContext());
        recyclerView.setLayoutParams(params);
        int padding = ArmsUtils.dip2px(anchor.getContext(),10);
        recyclerView.setPadding(padding,padding,padding,padding);
        recyclerView.setBackgroundResource(R.color.mask_30);

        RecyclerView.LayoutManager  manager = new GridLayoutManager(anchor.getContext(), 4);
        GridDividerItemDecoration itemDecoration = new GridDividerItemDecoration(ArmsUtils.dip2px(anchor.getContext(),4),
                ContextCompat.getColor(anchor.getContext(),android.R.color.transparent));
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(itemDecoration);
        adapter.setOnItemClickListener(listener);
        recyclerView.setAdapter(adapter);

        popupWindow = SmartPopupWindow.Builder.build((Activity)anchor.getContext(),recyclerView)
                .setOutsideTouchDismiss(true)
                .setSize(ViewGroup.LayoutParams.MATCH_PARENT,
                        height)
                .setAnimationStyle(R.style.popupwindowAnim)
                .createPopupWindow();
        popupWindow.setOnDismissListener(null);
//        popupWindow.showAtAnchorView(heads.get(index)
//                , VerticalPosition.BELOW, HorizontalPosition.CENTER);
        return popupWindow;
    }
}

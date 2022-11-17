package com.example.df.zhiyun.mvp.ui.widget;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.mvp.model.api.Api;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 */
public class PaginatorHelper {
    private int pageStart = Api.PageStart;
    private int pageIndex = pageStart;

    public PaginatorHelper(){
    }

    public PaginatorHelper(int start){
        pageStart = start;
    }

    public int getPageIndex(boolean pullToRefresh) {
        if(pullToRefresh){
            pageIndex = pageStart;
        }
        return pageIndex;
    }

    public <T> void onDataArrive(BaseQuickAdapter mAdapter, boolean pullToRefresh, List<T> tempDatas, IPaginator iPaginator){
        if(tempDatas == null || tempDatas.size() == 0){
            if(pullToRefresh){
                mAdapter.setNewData(new ArrayList());
                if(iPaginator != null){
                    iPaginator.setEmpty();
                }
            }
            mAdapter.loadMoreEnd();
        }else if(tempDatas.size() < Api.PageSize){
            if(pullToRefresh){
                mAdapter.setNewData(tempDatas);
            }else{
                mAdapter.addData(tempDatas);
            }
            mAdapter.loadMoreEnd();
            if(iPaginator != null) {
                iPaginator.setHasMoreDataToLoad(false);
            }
        }else{
            if(pullToRefresh){
                mAdapter.setNewData(tempDatas);
            }else{
                mAdapter.addData(tempDatas);
            }
            mAdapter.loadMoreComplete();
            if(iPaginator != null) {
                iPaginator.setHasMoreDataToLoad(true);
            }
            pageIndex++;
        }
    }

    public interface IPaginator{
        void setHasMoreDataToLoad(boolean hasMoreDataToLoad);
        void setEmpty();
    }

    public static <T> void onAllDataArrive(BaseQuickAdapter mAdapter,List<T> tempDatas){
        if(tempDatas == null){
            mAdapter.setNewData(new ArrayList());
        }else{
            mAdapter.setNewData(tempDatas);
        }
        mAdapter.loadMoreEnd();
    }
}

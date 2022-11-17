package com.example.df.zhiyun.common.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.jess.arms.mvp.IPresenter;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 基本带刷新的列表页面
 * @param <P>
 */
public abstract class BaseRefreshListActivity<P extends IPresenter> extends BaseStatusActivity<P> implements
        SwipeRefreshLayout.OnRefreshListener , BaseQuickAdapter.OnItemClickListener
    , BaseQuickAdapter.RequestLoadMoreListener{
    @BindView(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;

    @Inject
    public BaseQuickAdapter mAdapter;
    @Inject
    public RecyclerView.LayoutManager layoutManager;
    @Inject
    public RecyclerView.ItemDecoration itemDecoration;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.base_activity_refresh_recycler;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        initRefreshLayout();
        initRecyclerView();
    }


    private void initRefreshLayout(){
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(itemDecoration);
        mAdapter.setOnItemClickListener(this);

        if(canLoadMore()){
            mAdapter.setOnLoadMoreListener(this, recyclerView);
            mAdapter.setEnableLoadMore(true);
            mAdapter.setPreLoadNumber(2);   //预加载
        }

        recyclerView.setAdapter(mAdapter);
    }

    public abstract boolean canLoadMore();
}

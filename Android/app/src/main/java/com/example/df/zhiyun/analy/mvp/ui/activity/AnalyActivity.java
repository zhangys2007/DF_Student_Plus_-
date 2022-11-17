package com.example.df.zhiyun.analy.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.analy.di.component.DaggerAnalyComponent;
import com.example.df.zhiyun.mvp.model.entity.AnalyItem;
import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.example.df.zhiyun.comment.mvp.ui.activity.CommentUsageActivity;
import com.example.df.zhiyun.plan.mvp.ui.activity.PlanUsingActivity;
import com.example.df.zhiyun.mvp.ui.activity.SubmitCorrectActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.df.zhiyun.analy.mvp.contract.AnalyContract;
import com.example.df.zhiyun.analy.mvp.presenter.AnalyPresenter;

import com.example.df.zhiyun.R;


import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 教师端数据分析
 * <p>
 * Created by MVPArmsTemplate on 09/04/2019 10:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AnalyActivity extends BaseStatusActivity<AnalyPresenter> implements AnalyContract.View
    ,SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener{
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Inject
    BaseQuickAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager layoutManager;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAnalyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.base_activity_refresh_recycler; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {}

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        initRefreshLayout();
        mPresenter.requestData();
    }

    private void initRefreshLayout(){
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
        mAdapter.setOnItemClickListener(this::onItemClick);

//        mAdapter.setOnLoadMoreListener(this, recyclerView);
//        mAdapter.setEnableLoadMore(true);
//        mAdapter.setPreLoadNumber(2);   //预加载
//        mAdapter.setLoadMoreView(new ListLoadmoreView()); //自定义的loadmoreview
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        AnalyItem item = (AnalyItem)adapter.getData().get(position);
        if(item.getSort() == 4){
            ArmsUtils.startActivity(GrowthTraceListActivity.class);
        }else if(item.getSort() == 1){
            ArmsUtils.startActivity(HistoryHomeworkAnalyActivity.class);
        }else if(item.getSort() == 2){
            ArmsUtils.startActivity(HistoryKnowledgePointActivity.class);
        }else if(item.getSort() == 3){
            ArmsUtils.startActivity(GrowthTraceClassActivity.class);
        }else if(item.getSort() == 5){
            ArmsUtils.startActivity(SubmitCorrectActivity.class);
        }else if(item.getSort() == 6){
            ArmsUtils.startActivity(PlanUsingActivity.class);
        }else if(item.getSort() == 7){
            ArmsUtils.startActivity(CommentUsageActivity.class);
        }else{
            AnalyDetailActivity.launcheDetailActivity(this,item.getAnalysisName(),item.getAnalysisUrl());
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.requestData();
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }
    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }
}

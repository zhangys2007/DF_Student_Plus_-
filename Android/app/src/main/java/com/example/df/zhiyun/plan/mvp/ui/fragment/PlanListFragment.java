package com.example.df.zhiyun.plan.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.plan.di.component.DaggerPlanListComponent;
import com.example.df.zhiyun.plan.mvp.contract.PlanListContract;
import com.example.df.zhiyun.mvp.model.entity.Plan;
import com.example.df.zhiyun.plan.mvp.presenter.PlanListPresenter;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;


import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:新作业列表
 * <p>
 * Created by MVPArmsTemplate on 07/22/2019 11:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PlanListFragment extends BaseLazyLoadFragment<PlanListPresenter> implements PlanListContract.View
        ,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener
        ,BaseQuickAdapter.OnItemClickListener{
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Inject
    BaseQuickAdapter mAdapter;
//    @Inject
//    RecyclerView.LayoutManager layoutManager;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;

    @Inject
    @Named("parentId")
    String mParentId;

    public static PlanListFragment newInstance(int isClass, int isCloud, String parentId) {
        PlanListFragment fragment = new PlanListFragment();
        Bundle data = new Bundle();
        data.putInt(KEY_CLASS,isClass);
        data.putInt(KEY_CLOUD,isCloud);
        data.putString(KEY_PARENT_ID,parentId);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerPlanListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.base_fragment_refresh_recycler, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(itemDecoration);
        mAdapter.setOnItemClickListener(this);

        //只有第一级才有加载更多
        if(TextUtils.isEmpty(mParentId)){
            mAdapter.setOnLoadMoreListener(this, recyclerView);
            mAdapter.setEnableLoadMore(true);
        }

//        mAdapter.setPreLoadNumber(2);   //预加载
//        mAdapter.setLoadMoreView(new ListLoadmoreView()); //自定义的loadmoreview
        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
    }



    @Nullable
    @Override
    public Context getPageContext() {
        return this.getContext();
    }

    @Override
    public Fragment getFragment() {
        return this;
    }


    @Override
    public void onLoadMoreRequested() {
        mPresenter.requestData(false);
    }


    @Override
    public void onRefresh() {
        mPresenter.requestData(true);
    }


    @Override
    public void setData(@Nullable Object data) {

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

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Plan plan = (Plan) adapter.getData().get(position);
        mPresenter.clickItem(plan);
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.requestData(true);
    }

}

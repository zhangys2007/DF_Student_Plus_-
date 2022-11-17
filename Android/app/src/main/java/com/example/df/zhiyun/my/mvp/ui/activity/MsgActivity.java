package com.example.df.zhiyun.my.mvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.example.df.zhiyun.my.di.component.DaggerMsgComponent;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.my.mvp.contract.MsgContract;
import com.example.df.zhiyun.my.mvp.presenter.MsgPresenter;
import com.example.df.zhiyun.mvp.ui.widget.CommonDialog.CommonDialogs;

import javax.inject.Inject;

import butterknife.BindView;

import com.example.df.zhiyun.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/24/2019 13:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MsgActivity extends BaseStatusActivity<MsgPresenter> implements MsgContract.View
    , SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener
        ,BaseQuickAdapter.OnItemChildClickListener{

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar_left_title)
    TextView tvParent;

    @Inject
    BaseQuickAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager layoutManager;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;

    Dialog mMsgDialog;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMsgComponent //如找不到该类,请编译一下项目
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
    public void initData(@Nullable Bundle savedInstanceState) {
//        initRecyclerView();
//        initRefreshLayout();
//        tvParent.setText(R.string.my);
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        initRefreshLayout();
        tvParent.setText(R.string.my);
        mPresenter.requestData(true);
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
        mAdapter.setOnItemClickListener(this::onItemChildClick);

        mAdapter.setOnLoadMoreListener(this, recyclerView);
        mAdapter.setEnableLoadMore(true);
//        mAdapter.setPreLoadNumber(2);   //预加载
//        mAdapter.setLoadMoreView(new ListLoadmoreView()); //自定义的loadmoreview
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.requestData(false);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        mPresenter.clickItem(position);
    }
    @Override
    public void onRefresh() {
        mPresenter.requestData(true);
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

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mMsgDialog != null && mMsgDialog.isShowing()){
            mMsgDialog.dismiss();
            mMsgDialog = null;
        }
    }

    @Override
    public void showDialog(String msg){
        mMsgDialog = new CommonDialogs(this)
                .setMessage(msg)
                .setTitle(R.string.system_post)
                .setPositiveButton(getString(R.string.sure),null)
                .setNegativeButton(getString(R.string.cancel),null)
                .setNegativeButtonColor(R.color.blue)
                .builder();
        mMsgDialog.show();
    }

}

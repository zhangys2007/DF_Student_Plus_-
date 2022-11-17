package com.example.df.zhiyun.analy.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.common.mvp.ui.activity.BaseRefreshListActivity;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.df.zhiyun.analy.di.component.DaggerKnowledgeHoldComponent;
import com.example.df.zhiyun.analy.mvp.contract.KnowledgeHoldContract;
import com.example.df.zhiyun.analy.mvp.presenter.KnowledgeHoldPresenter;

import com.example.df.zhiyun.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 知识点掌握情况列表
 * <p>
 * Created by MVPArmsTemplate on 05/14/2020 11:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class KnowledgeHoldActivity extends BaseRefreshListActivity<KnowledgeHoldPresenter> implements KnowledgeHoldContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerKnowledgeHoldComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        super.initAll(savedInstanceState);
        setTitle("语序不当");
        int padding = ArmsUtils.dip2px(this,15);
        recyclerView.setPadding(padding,padding,padding,padding);
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
    public boolean canLoadMore() {
        return false;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onLoadMoreRequested() {

    }
}

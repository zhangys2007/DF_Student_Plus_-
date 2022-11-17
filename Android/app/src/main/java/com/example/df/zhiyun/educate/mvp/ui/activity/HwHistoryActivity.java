package com.example.df.zhiyun.educate.mvp.ui.activity;

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

import com.example.df.zhiyun.educate.di.component.DaggerHwHistoryComponent;
import com.example.df.zhiyun.educate.mvp.contract.HwHistoryContract;
import com.example.df.zhiyun.educate.mvp.presenter.HwHistoryPresenter;

import com.example.df.zhiyun.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 历次作业
 * <p>
 * Created by MVPArmsTemplate on 05/07/2020 17:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HwHistoryActivity extends BaseRefreshListActivity<HwHistoryPresenter> implements HwHistoryContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHwHistoryComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
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

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        super.initAll(savedInstanceState);
        int paddingH = ArmsUtils.dip2px(this,7);
        int paddingV = ArmsUtils.dip2px(this,20);
        recyclerView.setPadding(paddingH,paddingV,paddingH,0);
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

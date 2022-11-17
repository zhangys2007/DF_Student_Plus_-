package com.example.df.zhiyun.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.mvp.contract.VersionSwitchContract;
import com.example.df.zhiyun.mvp.model.adapterEntity.VersionItemMultipleItem;
import com.example.df.zhiyun.mvp.model.entity.VersionItem;
import com.example.df.zhiyun.mvp.model.entity.VersionPublisher;
import com.example.df.zhiyun.mvp.presenter.VersionSwitchPresenter;
import com.example.df.zhiyun.mvp.ui.adapter.VersionAdapter;
import com.example.df.zhiyun.mvp.ui.widget.VersionSpanLookup;

import javax.inject.Inject;

import butterknife.BindView;
import com.example.df.zhiyun.di.component.DaggerVersionSwitchComponent;

import com.example.df.zhiyun.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 同步练习  版本切换
 * <p>
 * Created by MVPArmsTemplate on 08/21/2019 13:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class VersionSwitchActivity extends BaseStatusActivity<VersionSwitchPresenter> implements VersionSwitchContract.View
    ,BaseQuickAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Inject
    BaseMultiItemQuickAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager layoutManager;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;

    public static void startVersionSwitchPage(Activity context, String subject,int reqCode){
        Intent intent = new Intent(context,VersionSwitchActivity.class);
        intent.putExtra(VersionSwitchContract.View.KEY_SUBJ,subject);
        context.startActivityForResult(intent,reqCode);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerVersionSwitchComponent //如找不到该类,请编译一下项目
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
//        initRefreshLayout();
//        initRecyclerView();
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        initRefreshLayout();
        initRecyclerView();
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
        mAdapter.setOnItemClickListener(this);
//        recyclerView.addItemDecoration(itemDecoration);
        mAdapter.setSpanSizeLookup(new VersionSpanLookup(mAdapter));
//        mAdapter.setPreLoadNumber(2);   //预加载
//        mAdapter.setLoadMoreView(new ListLoadmoreView()); //自定义的loadmoreview
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        MultiItemEntity multiItemEntity = (MultiItemEntity)adapter.getData().get(position);
        if(multiItemEntity.getItemType() == VersionAdapter.LEVEL_1){
            VersionItemMultipleItem item = (VersionItemMultipleItem) multiItemEntity;
            VersionPublisher versionPublisher = (VersionPublisher)(item.getParentData());
            VersionItem versionItem = (VersionItem)item.getData();

            Intent intent = new Intent();
            intent.putExtra(VersionSwitchContract.View.DATA_PUBLISHER,versionPublisher.getId());
            intent.putExtra(VersionSwitchContract.View.DATA_ITEM,versionItem.getId());
            setResult(Activity.RESULT_OK,intent);
            killMyself();
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

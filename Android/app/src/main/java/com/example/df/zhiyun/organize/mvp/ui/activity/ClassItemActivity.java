package com.example.df.zhiyun.organize.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.example.df.zhiyun.mvp.ui.activity.MemberItemActivity;
import com.example.df.zhiyun.organize.di.component.DaggerClassItemComponent;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import com.example.df.zhiyun.organize.mvp.contract.ClassItemContract;
import com.example.df.zhiyun.mvp.model.entity.ClassItem;
import com.example.df.zhiyun.organize.mvp.presenter.ClassItemPresenter;

import com.example.df.zhiyun.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 15:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ClassItemActivity extends BaseStatusActivity<ClassItemPresenter> implements ClassItemContract.View
        , SwipeRefreshLayout.OnRefreshListener
        ,BaseQuickAdapter.OnItemClickListener{
    @BindView(R.id.toolbar_left_title)
    TextView tvParent;
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

    @Inject
    @Named("name")
    String mName;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerClassItemComponent //如找不到该类,请编译一下项目
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
//        tvParent.setText(getString(R.string.member_of_school));
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        initRefreshLayout();
        tvParent.setText(getString(R.string.member_of_school));
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
        mAdapter.setOnItemClickListener(this::onItemClick);

        View head = LayoutInflater.from(this).inflate(R.layout.view_grade_head,null);
        ((TextView)head.findViewById(R.id.tv_grade_name)).setText(mName);
        mAdapter.addHeaderView(head);
//        mAdapter.setOnLoadMoreListener(this, recyclerView);
//        mAdapter.setEnableLoadMore(true);
//        mAdapter.setPreLoadNumber(2);   //预加载
//        mAdapter.setLoadMoreView(new ListLoadmoreView()); //自定义的loadmoreview
        recyclerView.setAdapter(mAdapter);
    }

//    @Override
//    public void onLoadMoreRequested() {
//        mPresenter.requestData(false);
//    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        ClassItem item = (ClassItem)adapter.getData().get(position);
        if(item.getIsclass() == 1){
            Intent intent = new Intent(this, MemberItemActivity.class);
            intent.putExtra(ClassItemContract.View.KEY,item.getClassNumber());
            intent.putExtra(ClassItemContract.View.Name,item.getClassName());
            ArmsUtils.startActivity(intent);
        }
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
}

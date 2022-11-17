package com.example.df.zhiyun.correct.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.EventBusTags;
import com.example.df.zhiyun.correct.di.component.DaggerCorrectHomeworkComponent;
import com.example.df.zhiyun.mvp.model.entity.HomeworkArrange;
import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import javax.inject.Inject;

import butterknife.BindView;
import com.example.df.zhiyun.correct.mvp.contract.CorrectHomeworkContract;
import com.example.df.zhiyun.correct.mvp.presenter.CorrectHomeworkPresenter;

import com.example.df.zhiyun.R;


import org.simple.eventbus.Subscriber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:  已批和未批的作业列表
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 18:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CorrectHomeworkActivity extends BaseStatusActivity<CorrectHomeworkPresenter> implements CorrectHomeworkContract.View
        , SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener
        ,BaseQuickAdapter.OnItemChildClickListener{
    @BindView(R.id.toolbar_left_title)
    TextView tvParent;
    @BindView(R.id.toolbar_title)
    TextView tvTitle;
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
    Boolean isCorrect;

    public static void toCorrectPage(Context context, HomeworkArrange data, boolean correct){
        Intent intent = new Intent(context, CorrectHomeworkActivity.class);
        intent.putExtra(CorrectHomeworkContract.View.KEY,""+data.getHomeworkId());
        intent.putExtra(CorrectHomeworkContract.View.CORRECT,correct);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCorrectHomeworkComponent //如找不到该类,请编译一下项目
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
////        tvTitle.setText(getString(isCorrect?R.string.already_correct:R.string.uncorrected));
//        setTitle(isCorrect?R.string.already_correct:R.string.uncorrected);
//        tvParent.setText(getString(R.string.correct_homework));
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        initRefreshLayout();
//        tvTitle.setText(getString(isCorrect?R.string.already_correct:R.string.uncorrected));
        setTitle(isCorrect?R.string.corrected:R.string.uncorrected);
        tvParent.setText(getString(R.string.correct_homework));
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
        mAdapter.setOnItemChildClickListener(this);

        mAdapter.setOnLoadMoreListener(this, recyclerView);
        mAdapter.setEnableLoadMore(false);
//        mAdapter.setPreLoadNumber(2);   //预加载
//        mAdapter.setLoadMoreView(new ListLoadmoreView()); //自定义的loadmoreview
        recyclerView.setAdapter(mAdapter);
    }



    @Override
    public void onLoadMoreRequested() {
        mPresenter.requestData();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if(view.getId() == R.id.iv_correct){
            mPresenter.correctHW(position);
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


    @Subscriber(tag = EventBusTags.UPDATE_HW_LIST)
    private void requestNewData(Integer value){
        mPresenter.requestData();
    }
}

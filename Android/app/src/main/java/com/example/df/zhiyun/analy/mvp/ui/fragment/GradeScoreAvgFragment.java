package com.example.df.zhiyun.analy.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.analy.di.component.DaggerGradeScoreAvgComponent;
import com.example.df.zhiyun.analy.mvp.contract.GradeScoreAvgContract;
import com.example.df.zhiyun.analy.mvp.presenter.GradeScoreAvgPresenter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:知识点掌握详情
 * <p>
 * Created by MVPArmsTemplate on 07/22/2019 11:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class GradeScoreAvgFragment extends BaseFragment<GradeScoreAvgPresenter> implements GradeScoreAvgContract.View
        ,SwipeRefreshLayout.OnRefreshListener , BaseQuickAdapter.RequestLoadMoreListener{
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

    public static GradeScoreAvgFragment newInstance(int fzId, int gradeId, int schoolId, int subjId, int type) {
        GradeScoreAvgFragment fragment = new GradeScoreAvgFragment();
        Bundle data = new Bundle();
        data.putInt(GradeScoreAvgContract.View.KEY_FZ,fzId);
        data.putInt(GradeScoreAvgContract.View.KEY_GRADEID,gradeId);
//        data.putString(GradeScoreAvgContract.View.KEY_HOMEWORK_ID,hwId);
        data.putInt(GradeScoreAvgContract.View.KEY_SCHOOLID,schoolId);
        data.putInt(GradeScoreAvgContract.View.KEY_SUBJID,subjId);
        data.putInt(GradeScoreAvgContract.View.KEY_TYPE,type);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerGradeScoreAvgComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_grade_avg, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        mPresenter.requestData(true);
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(itemDecoration);
//        mAdapter.setOnItemClickListener(this);

        //只有第一级才有加载更多
//        if(TextUtils.isEmpty(mParentId)){
            mAdapter.setOnLoadMoreListener(this, recyclerView);
            mAdapter.setEnableLoadMore(true);
//        }

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
    public void onLoadMoreRequested() {
    }

}

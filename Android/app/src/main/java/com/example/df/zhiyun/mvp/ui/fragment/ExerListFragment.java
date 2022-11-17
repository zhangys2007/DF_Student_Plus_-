package com.example.df.zhiyun.mvp.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.mvp.model.entity.Exer;
import com.example.df.zhiyun.preview.mvp.ui.activity.PreviewHomeworkActivity;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.mvp.contract.ExerListContract;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.Subject;
import com.example.df.zhiyun.mvp.presenter.ExerListPresenter;
import com.example.df.zhiyun.mvp.ui.widget.ViewSubjDateRecyclerHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import com.example.df.zhiyun.di.component.DaggerExerListComponent;

import com.example.df.zhiyun.R;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/25/2019 17:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ExerListFragment extends BaseLazyLoadFragment<ExerListPresenter> implements ExerListContract.View
        ,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener
        ,BaseQuickAdapter.OnItemClickListener, ViewSubjDateRecyclerHelper.SubjDateInterface {
    public static final String KEY_SUBJ = "subjectId";
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView_content)
    RecyclerView recyclerView;

    @Inject
    BaseQuickAdapter mAdapter;
//    @Inject
//    RecyclerView.LayoutManager layoutManager;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;

    ViewSubjDateRecyclerHelper helper = new ViewSubjDateRecyclerHelper();


    public static ExerListFragment newInstance(int subjectId) {
        ExerListFragment fragment = new ExerListFragment();
        Bundle data = new Bundle();
        data.putInt(KEY_SUBJ,subjectId);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerExerListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homework_list, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        helper.create(this,this, getArguments().getInt(KEY_SUBJ, Api.SUBJECT_ALL));
        initRecyclerView();
        mPresenter.changeSubject(getArguments().getInt(KEY_SUBJ, Api.SUBJECT_ALL));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        helper.destroy();
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(itemDecoration);
        mAdapter.setOnItemClickListener(this);

        mAdapter.setOnLoadMoreListener(this, recyclerView);
        mAdapter.setEnableLoadMore(true);
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
    public void recvSubjDatas(List<Subject> datas) {
        helper.getSubjectDatas(datas);
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
        Exer exer = (Exer) adapter.getData().get(position);
        PreviewHomeworkActivity.launchActivity(getContext(),exer.getId(),
                Api.STUDUNTEN_ANSWER_TYPE_HISTORY,0);
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.requestData(true);
        mPresenter.getSubjectList();
    }

    public void getInitData(){
        mPresenter.requestData(true);
        mPresenter.getSubjectList();
    }


    @Override
    public void onSubjectChange(String subjId, String subName) {
        mPresenter.changeSubject(Integer.parseInt(subjId));
        Activity activity = getActivity();
        if(activity instanceof ViewSubjDateRecyclerHelper.SubjDateInterface){
            ((ViewSubjDateRecyclerHelper.SubjDateInterface) activity).onSubjectChange(subjId,subName);
        }
    }

    @Override
    public void onTimeChange(String start, String end) {
        mPresenter.changeTime(start,end);
        Activity activity = getActivity();
        if(activity instanceof ViewSubjDateRecyclerHelper.SubjDateInterface){
            ((ViewSubjDateRecyclerHelper.SubjDateInterface) activity).onTimeChange(start,end);
        }
    }
}

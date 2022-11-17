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
import com.example.df.zhiyun.app.EventBusTags;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.mvp.contract.HomeworkNewContract;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.Homework;
import com.example.df.zhiyun.mvp.presenter.HomeworkNewPresenter;
import com.example.df.zhiyun.paper.mvp.ui.activity.SetHomeworkActivity;
import com.example.df.zhiyun.mvp.ui.widget.ViewSubjDateRecyclerHelper;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import com.example.df.zhiyun.di.component.DaggerHomeworkNewComponent;

import com.example.df.zhiyun.R;

import org.simple.eventbus.Subscriber;

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
public class HomeworkNewFragment extends BaseLazyLoadFragment<HomeworkNewPresenter> implements HomeworkNewContract.View
        ,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener
        ,BaseQuickAdapter.OnItemChildClickListener, ViewSubjDateRecyclerHelper.SubjDateInterface,ViewSubjDateRecyclerHelper.SubjDateSyncInterface{
    public static final String KEY_SUBJ = "subjectId";
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView_content)
    RecyclerView recyclerView;

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    IRepositoryManager iRepositoryManager;

    @Inject
    BaseQuickAdapter mAdapter;
//    @Inject
//    RecyclerView.LayoutManager layoutManager;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;

    ViewSubjDateRecyclerHelper helper = new ViewSubjDateRecyclerHelper();


    public static HomeworkNewFragment newInstance(int subjectId) {
        HomeworkNewFragment fragment = new HomeworkNewFragment();
        Bundle data = new Bundle();
        data.putInt(KEY_SUBJ,subjectId);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeworkNewComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Subscriber(tag = EventBusTags.UPDATE_HW_LIST)
    private void requestNewHomework(Integer value){
        mPresenter.requestHomework(true);
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
        if(savedInstanceState != null){
            lazyLoadData();
        }
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
        mAdapter.setOnItemClickListener(this::onItemChildClick);

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
    public void onLoadMoreRequested() {
        mPresenter.requestHomework(false);
    }


    @Override
    public void onRefresh() {
        mPresenter.requestHomework(true);
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
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Homework homework = (Homework)adapter.getData().get(position);
//        SetHomeworkActivity.launchActivity(getContext(),homework.getStudentHomeworkId(),Api.STUDUNTEN_ANSWER_TYPE_HOMEWORK);
        SetHomeworkActivity.launchActivity(getContext(),homework.getStudentHomeworkId(),"",0,"",0);
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.requestHomework(true);
        helper.getSubjectData(iRepositoryManager,this,mErrorHandler);
    }

    public void getInitData(){
        mPresenter.requestHomework(true);
        helper.getSubjectData(iRepositoryManager,this,mErrorHandler);
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


    @Override
    public void syncSubject(String subjId, String subName) {
        mPresenter.changeSubject(Integer.parseInt(subjId));
        helper.initSubjectTheme(Integer.parseInt(subjId));
        helper.preSetSubjName(subName);
    }

    @Override
    public void syncTime(String start, String end) {
        mPresenter.changeTime(start,end);
    }
}

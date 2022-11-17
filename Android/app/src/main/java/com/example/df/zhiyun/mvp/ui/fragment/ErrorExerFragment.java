package com.example.df.zhiyun.mvp.ui.fragment;

import android.content.Context;
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
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.EventBusTags;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.mvp.contract.ErrorExerContract;
import com.example.df.zhiyun.mvp.model.adapterEntity.QuestionMultipleItem;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.Subject;
import com.example.df.zhiyun.mvp.presenter.ErrorExerPresenter;
import com.example.df.zhiyun.mvp.ui.activity.EQDetailActivity;
import com.example.df.zhiyun.mvp.ui.activity.SearchActivity;
import com.example.df.zhiyun.mvp.ui.widget.ViewSubjDateRecyclerHelper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import com.example.df.zhiyun.di.component.DaggerErrorExerComponent;

import com.example.df.zhiyun.R;

import org.simple.eventbus.Subscriber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 主页面上的错题集子页面
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 13:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ErrorExerFragment extends BaseLazyLoadFragment<ErrorExerPresenter> implements ErrorExerContract.View
    , ViewSubjDateRecyclerHelper.SubjDateInterface,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener,
    View.OnClickListener{
    @BindView(R.id.toolbar_left_title)
    TextView leftTitle;
    @BindView(R.id.title)
    TextView tvTitle;
    @BindView(R.id.tv_search_homework)
    TextView tvRight;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recyclerView_content)
    RecyclerView recyclerViewContent;

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    IRepositoryManager iRepositoryManager;

    @Inject
    BaseMultiItemQuickAdapter mAdapter;
//    @Inject
//    RecyclerView.LayoutManager layoutManager;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;

    ViewSubjDateRecyclerHelper helper = new ViewSubjDateRecyclerHelper();


    public static ErrorExerFragment newInstance() {
        ErrorExerFragment fragment = new ErrorExerFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerErrorExerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_error_exer, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initRecyclerView();
        initToolbar();
        helper.create(getActivity(),this, Api.SUBJECT_ALL);
        helper.needNotifyWhenLoaded(true);
        tvRight.setOnClickListener(this);
        tvRight.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search_homework:
                SearchActivity.startSearchPage(getContext(),Api.SEARCH_PAPER
                        ,Integer.toString(mPresenter.getSubjectId()));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        helper.destroy();
    }

    @Override
    public void setData(@Nullable Object data) { }

    private void initToolbar(){
        tvTitle.setText(R.string.error_exer);
        leftTitle.setVisibility(View.INVISIBLE);
        tvRight.setText(R.string.search_error);
    }

    @Nullable
    @Override
    public Context getPageContext() {
        return this.getContext();
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewContent.setLayoutManager(manager);
        recyclerViewContent.addItemDecoration(itemDecoration);

        mAdapter.setOnLoadMoreListener(this, recyclerViewContent);
        mAdapter.setEnableLoadMore(true);
//        mAdapter.setPreLoadNumber(2);   //预加载
//        mAdapter.setLoadMoreView(new ListLoadmoreView()); //自定义的loadmoreview
        recyclerViewContent.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                QuestionMultipleItem item = (QuestionMultipleItem)adapter.getData().get(position);
                if(item.getItemType() != Api.QUESTION_HEAD){
                    EQDetailActivity.startErrorDetailPage(getContext(),item.getData().getQuestionId(),
                            item.getData().getSubjectId());
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
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
    public void killMyself() {}

    @Override
    public void onSubjectChange(String subjId, String subName) {
        mPresenter.changeSubject(Integer.parseInt(subjId));
        mPresenter.requesData(true);
    }

    @Override
    public void onTimeChange(String start, String end) {
        mPresenter.changeTime(start,end);
        mPresenter.requesData(true);
    }

    @Override
    public void onRefresh() {
        if(helper.isDataReady()){
            mPresenter.requesData(true);
        }else{
            helper.getSubjectData(iRepositoryManager,this,mErrorHandler);
        }

    }


    @Override
    public void onLoadMoreRequested() {
        mPresenter.requesData(false);
    }

    @Override
    protected void lazyLoadData() {
        helper.getSubjectData(iRepositoryManager,this,mErrorHandler);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            lazyLoadData();
        }
    }

    @Subscriber(tag = EventBusTags.UPDATE_FRAGMENT)
    private void updateFragemtn(Integer value){
        if(value == 2){
            onRefresh();
        }
    }
}

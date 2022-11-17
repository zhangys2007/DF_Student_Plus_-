package com.example.df.zhiyun.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.EventBusTags;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.mvp.contract.HomeworkContract;
import com.example.df.zhiyun.mvp.model.adapterEntity.MainHomeworkMultipleItem;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.presenter.HomeworkPresenter;
import com.example.df.zhiyun.mvp.ui.activity.AllHomeworkActivity;
import com.example.df.zhiyun.mvp.ui.activity.CategoryActivity;
import com.example.df.zhiyun.mvp.ui.activity.HomeworkListActivity;
import com.example.df.zhiyun.mvp.ui.activity.SearchActivity;
import com.example.df.zhiyun.paper.mvp.ui.activity.SetHomeworkActivity;
import com.example.df.zhiyun.mvp.ui.widget.MainHWRecycleViewGridDivider;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import com.example.df.zhiyun.di.component.DaggerHomeworkComponent;

import com.example.df.zhiyun.R;

import org.simple.eventbus.Subscriber;

import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 10:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HomeworkFragment extends BaseLazyLoadFragment<HomeworkPresenter> implements HomeworkContract.View
    ,View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.toolbar_left_title)
    TextView leftTitle;
    @BindView(R.id.title)
    TextView tvTitle;
    @BindView(R.id.tv_search_homework)
    TextView tvRight;

    @BindView(R.id.tv_yuwen)
    TextView tvChinese;
    @BindView(R.id.tv_math)
    TextView tvMath;
    @BindView(R.id.tv_english)
    TextView tvEnglish;

    @BindView(R.id.tv_hw_more)
    ImageView ivMoreHomeWork;

    @BindView(R.id.cv_english)
    CardView cvEnglish;
    @BindView(R.id.cv_math)
    CardView cvMath;
    @BindView(R.id.cv_chinese)
    CardView cvChinese;

    @BindView(R.id.swipeRefreshLayout_hw)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerView_hw)
    RecyclerView recyclerViewContent;
    @Inject
    BaseMultiItemQuickAdapter mAdapter;
//    @Inject
//    RecyclerView.LayoutManager layoutManager;
    @Inject
    BaseQuickAdapter.SpanSizeLookup spanSizeLookup;
    @Inject
    MainHWRecycleViewGridDivider itemDecoration;


    public static HomeworkFragment newInstance() {
        HomeworkFragment fragment = new HomeworkFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHomeworkComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homework, container, false);
    }

    @Nullable
    @Override
    public Context getPageContext() {
        return this.getContext();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initToolbar();
        initClickListener();
        initHomeworkRecyclerView();
        initRefreshLayout();
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

    private void initToolbar(){
        tvTitle.setText(R.string.home_work);
        leftTitle.setVisibility(View.INVISIBLE);
        tvRight.setText(R.string.search);
    }

    @Override
    public void onRefresh() {
        mPresenter.requestData();
    }


    private void initClickListener(){
        tvChinese.setOnClickListener(this);
        tvMath.setOnClickListener(this);
        tvEnglish.setOnClickListener(this);
        ivMoreHomeWork.setOnClickListener(this);
        cvChinese.setOnClickListener(this);
        cvEnglish.setOnClickListener(this);
        cvMath.setOnClickListener(this);
        tvRight.setOnClickListener(this);
    }

    //初始化最新作业的recyclerview
    private void initHomeworkRecyclerView(){
        recyclerViewContent.addItemDecoration(itemDecoration);
        recyclerViewContent.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mAdapter.setOnItemClickListener(hwItemClickListener);
        mAdapter.setEnableLoadMore(false);
        mAdapter.setSpanSizeLookup(spanSizeLookup);
        recyclerViewContent.setAdapter(mAdapter);
    }

    private BaseQuickAdapter.OnItemClickListener hwItemClickListener = new BaseQuickAdapter.OnItemClickListener(){

        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            Timber.tag(TAG).d("homework click: "+ position);
            MainHomeworkMultipleItem item = (MainHomeworkMultipleItem)adapter.getData().get(position);
            if(item.getItemType() != MainHomeworkMultipleItem.TYPE_EMPTY){
//                SetHomeworkActivity.launchActivity(getContext(),item.getData().getStudentHomeworkId()
//                        ,Api.STUDUNTEN_ANSWER_TYPE_HOMEWORK);
                SetHomeworkActivity.launchActivity(getContext(),
                        item.getData().getStudentHomeworkId(),"",0,"",0);

            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_yuwen:
                startHomeworkListPage(Api.SUBJECT_CHINESE);
                break;
            case R.id.tv_math:
                startHomeworkListPage(Api.SUBJECT_MATH);
                break;
            case R.id.tv_english:
                startHomeworkListPage(Api.SUBJECT_ENGLISH);
                break;
            case R.id.tv_hw_more:
                ArmsUtils.startActivity(AllHomeworkActivity.class);
                break;
            case R.id.cv_chinese:
                CategoryActivity.startSyncExerById(getContext(),getResources().getString(R.string.chinese));
                break;
            case R.id.cv_english:
                CategoryActivity.startSyncExerById(getContext(),getResources().getString(R.string.english));
                break;
            case R.id.cv_math:
                CategoryActivity.startSyncExerById(getContext(),getResources().getString(R.string.math));
                break;
            case R.id.tv_search_homework:
                SearchActivity.startSearchPage(getContext(),Api.SEARCH_HOMEWORK);
                break;
            default:
                break;
        }
    }

    private void startHomeworkListPage(int type){
        HomeworkListActivity.launchActivity(this.getContext(),type);
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
    public void bindHomeworkData(List<MainHomeworkMultipleItem> data){
        mAdapter.setNewData(data);
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.requestData();
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

    @Subscriber(tag = EventBusTags.UPDATE_FRAGMENT)
    private void updateFragemtn(Integer value){
        if(value == 1){
            mPresenter.requestData();
        }
    }
}

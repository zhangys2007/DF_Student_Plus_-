package com.example.df.zhiyun.comment.mvp.ui.fragment;

import android.app.Activity;
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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.comment.di.component.DaggerCommentListComponent;
import com.example.df.zhiyun.mvp.model.entity.CommentItem;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.Subject;
import com.example.df.zhiyun.mvp.ui.activity.ClassSelActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import com.example.df.zhiyun.comment.mvp.contract.CommentListContract;
import com.example.df.zhiyun.educate.mvp.model.entity.BelongClass;
import com.example.df.zhiyun.comment.mvp.presenter.CommentListPresenter;

import com.example.df.zhiyun.R;

import com.example.df.zhiyun.mvp.ui.widget.ViewSubjDateRecyclerHelper;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 讲评列表
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 10:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CommentListFragment extends BaseLazyLoadFragment<CommentListPresenter> implements CommentListContract.View
        ,SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener
        ,BaseQuickAdapter.OnItemChildClickListener,ViewSubjDateRecyclerHelper.SubjDateInterface{
    private static final int REQ_SEL = 99;
    public static final String KEY_SUBJ = "subjectId";
    @BindView(R.id.tv_class)
    TextView tvClass;

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

    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    IRepositoryManager iRepositoryManager;

    ViewSubjDateRecyclerHelper helper = new ViewSubjDateRecyclerHelper();


    public static CommentListFragment newInstance(int subjectId) {
        CommentListFragment fragment = new CommentListFragment();
        Bundle data = new Bundle();
        data.putInt(KEY_SUBJ,subjectId);
        fragment.setArguments(data);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCommentListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment_list, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        helper.create(this,this, getArguments().getInt(KEY_SUBJ, Api.SUBJECT_ALL));
        initRecyclerView();
        mPresenter.changeSubject(getArguments().getInt(KEY_SUBJ, Api.SUBJECT_ALL),"全部");
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(itemDecoration);
        mAdapter.setOnItemChildClickListener(this);

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

    @Override
    public void recvSubjDatas(List<Subject> datas) {
        helper.getSubjectDatas(datas);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.requestComment(false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            lazyLoadData();
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.getOtherData();
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
    protected void lazyLoadData() {
        helper.getSubjectData(iRepositoryManager,this,mErrorHandler);
        mPresenter.getOtherData();
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void bindClassInfo(BelongClass belongClass) {
        tvClass.setText(belongClass.getClassName());
        tvClass.setOnClickListener(classClickListener);
    }

    private View.OnClickListener classClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), ClassSelActivity.class);
            startActivityForResult(intent,REQ_SEL);
        }
    };

    @Override
    public void killMyself() {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()){
            case R.id.iv_comment_analy:
                mPresenter.previewDetail((CommentItem)adapter.getData().get(position));
                break;
            case R.id.iv_comment_summary:
                mPresenter.previewSummary((CommentItem)adapter.getData().get(position));
                break;
            default:
                break;
        }
    }

    @Override
    public void onSubjectChange(String subjId, String subName) {
        mPresenter.changeSubject(Integer.parseInt(subjId),subName);
        mPresenter.requestComment(true);
    }

    @Override
    public void onTimeChange(String start, String end) {
        mPresenter.changeTime(start,end);
        mPresenter.requestComment(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_SEL && resultCode == Activity.RESULT_OK){
            onClassChange(data.getStringExtra(ClassSelActivity.KEY_NAME),
                    data.getStringExtra(ClassSelActivity.KEY_ID));
        }
    }

    private void onClassChange(String name,String id){
        tvClass.setText(name);
        mPresenter.changeClass(name,id);
        mPresenter.requestComment(true);
    }
}

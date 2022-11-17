package com.example.df.zhiyun.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.analy.mvp.ui.activity.ClassDataAnalyActivity;
import com.example.df.zhiyun.di.component.DaggerClassCorrectInfoComponent;
import com.example.df.zhiyun.mvp.contract.ClassCorrectInfoContract;
import com.example.df.zhiyun.mvp.model.entity.ClassCorrectInfo;
import com.example.df.zhiyun.mvp.presenter.ClassCorrectInfoPresenter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:班级批改详情
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 15:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ClassCorrectInfoActivity extends BaseStatusActivity<ClassCorrectInfoPresenter> implements ClassCorrectInfoContract.View
        , SwipeRefreshLayout.OnRefreshListener ,BaseQuickAdapter.OnItemChildClickListener{
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
    @Named(ClassCorrectInfoContract.View.KEY_FZ)
    Integer mFz;
    @Inject
    @Named(ClassCorrectInfoContract.View.KEY_TYPE)
    Integer mType;
    @Inject
    @Named(ClassCorrectInfoContract.View.KEY_SCHOOLID)
    Integer mSchoolId;
    @Inject
    @Named(ClassCorrectInfoContract.View.KEY_GRADEID)
    Integer mGradeId;
    @Inject
    @Named(ClassCorrectInfoContract.View.KEY_SUBJID)
    Integer mSubjId;

    public static void launcheActivity(Context context, int fzPaperId, int type, int schoolId, int gradeId,int subjId){
        Intent intent = new Intent(context, ClassCorrectInfoActivity.class);
        intent.putExtra(ClassCorrectInfoContract.View.KEY_FZ,fzPaperId);
        intent.putExtra(ClassCorrectInfoContract.View.KEY_TYPE,type);
        intent.putExtra(ClassCorrectInfoContract.View.KEY_SCHOOLID,schoolId);
        intent.putExtra(ClassCorrectInfoContract.View.KEY_GRADEID,gradeId);
        intent.putExtra(ClassCorrectInfoContract.View.KEY_SUBJID,subjId);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerClassCorrectInfoComponent //如找不到该类,请编译一下项目
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
        mAdapter.setOnItemChildClickListener(this);

//        View head = LayoutInflater.from(this).inflate(R.layout.view_grade_head,null);
//        ((TextView)head.findViewById(R.id.tv_grade_name)).setText(mName);
//        mAdapter.addHeaderView(head);
//        mAdapter.setOnLoadMoreListener(this, recyclerView);
//        mAdapter.setEnableLoadMore(true);
//        mAdapter.setPreLoadNumber(2);   //预加载
//        mAdapter.setLoadMoreView(new ListLoadmoreView()); //自定义的loadmoreview
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if(view.getId() == R.id.btn_data){
            ClassCorrectInfo info = (ClassCorrectInfo)adapter.getData().get(position);
            ClassDataAnalyActivity.launcheActivity(this,info.getHomeworkId(),mFz,mType,mSchoolId,mGradeId,mSubjId);
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

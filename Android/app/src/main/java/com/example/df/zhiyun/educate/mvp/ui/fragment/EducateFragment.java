package com.example.df.zhiyun.educate.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.educate.di.component.DaggerEducateComponent;
import com.example.df.zhiyun.educate.mvp.contract.EducateContract;
import com.example.df.zhiyun.educate.mvp.presenter.EducatePresenter;
import com.example.df.zhiyun.educate.mvp.ui.activity.HwHistoryActivity;
import com.example.df.zhiyun.educate.mvp.ui.activity.HwPutActivity;
import com.example.df.zhiyun.plan.mvp.ui.activity.PlanCloudActivity;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 首页的教学页面
 * <p>
 * Created by MVPArmsTemplate on 05/06/2020 09:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class EducateFragment extends BaseFragment<EducatePresenter> implements EducateContract.View,
    View.OnClickListener{
    @BindView(R.id.iv_plan_cloud)
    ImageButton ibtnPlanCloud;
    @BindView(R.id.iv_plan_self)
    ImageButton ibtnPlanSelf;
    @BindView(R.id.fl_hw_his)
    FrameLayout flHwHistory;
    @BindView(R.id.fl_hw_put)
    FrameLayout flHwPut;

    @BindView(R.id.recyclerView_hw)
    RecyclerView recyclerViewHW;
    @BindView(R.id.recyclerView_manager)
    RecyclerView recyclerViewPaper;

    @Inject
    @Named(EducateContract.View.KEY_HW)
    RecyclerView.ItemDecoration mItemDecorationHW;
    @Inject
    @Named(EducateContract.View.KEY_HW)
    BaseQuickAdapter mAdapterHW;
    @Inject
    @Named(EducateContract.View.KEY_HW)
    RecyclerView.LayoutManager layoutManagerHW;


    @Inject
    @Named(EducateContract.View.KEY_PAPER)
    RecyclerView.ItemDecoration mItemDecorationPaper;
    @Inject
    @Named(EducateContract.View.KEY_PAPER)
    BaseQuickAdapter mAdapterPaper;
    @Inject
    @Named(EducateContract.View.KEY_PAPER)
    RecyclerView.LayoutManager layoutManagerPaper;


    public static EducateFragment newInstance() {
        EducateFragment fragment = new EducateFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerEducateComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_educate, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ibtnPlanCloud.setOnClickListener(this);
        ibtnPlanSelf.setOnClickListener(this);
        flHwHistory.setOnClickListener(this);
        flHwPut.setOnClickListener(this);

        recyclerViewHW.addItemDecoration(mItemDecorationHW);
        recyclerViewHW.setLayoutManager(layoutManagerHW);
//        mAdapter.setOnItemClickListener(hwItemClickListener);
        mAdapterHW.setEnableLoadMore(false);
        recyclerViewHW.setAdapter(mAdapterHW);

        recyclerViewPaper.addItemDecoration(mItemDecorationPaper);
        recyclerViewPaper.setLayoutManager(layoutManagerPaper);
//        mAdapter.setOnItemClickListener(hwItemClickListener);
        mAdapterPaper.setEnableLoadMore(false);
        recyclerViewPaper.setAdapter(mAdapterPaper);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_plan_cloud:
                ArmsUtils.startActivity(PlanCloudActivity.class);
                break;
            case R.id.iv_plan_self:
                break;
            case R.id.fl_hw_his:
                ArmsUtils.startActivity(HwHistoryActivity.class);
                break;
            case R.id.fl_hw_put:
                ArmsUtils.startActivity(HwPutActivity.class);
                break;
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
}

package com.example.df.zhiyun.analy.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.analy.di.component.DaggerClsHwReportComponent;
import com.example.df.zhiyun.analy.mvp.contract.ClsHwReportContract;
import com.example.df.zhiyun.analy.mvp.presenter.ClsHwReportPresenter;
import com.example.df.zhiyun.preview.mvp.ui.activity.ResolveTchActivity;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:班级作业报告
 * <p>
 * Created by MVPArmsTemplate on 04/25/2020 10:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ClsHwReportActivity extends BaseActivity<ClsHwReportPresenter> implements ClsHwReportContract.View
    , View.OnClickListener{
    @BindView(R.id.fl_resolve)
    FrameLayout flResolve;

    @BindView(R.id.recyclerView_section)
    RecyclerView recyclerViewSection;  //区段
    @BindView(R.id.recyclerView_std)
    RecyclerView recyclerViewStd;  //学生

    @Inject
    @Named(ClsHwReportContract.View.KEY_SECTION)
    RecyclerView.ItemDecoration mItemDecoration;
    @Inject
    @Named(ClsHwReportContract.View.KEY_SECTION)
    BaseQuickAdapter mAdapterSection;
    @Inject
    @Named(ClsHwReportContract.View.KEY_SECTION)
    RecyclerView.LayoutManager layoutManagerSection;

    @Inject
    @Named(ClsHwReportContract.View.KEY_STUDENT)
    BaseQuickAdapter mAdapterSectionStd;
    @Inject
    @Named(ClsHwReportContract.View.KEY_STUDENT)
    RecyclerView.LayoutManager layoutManagerStd;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerClsHwReportComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_cls_hw_report; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        flResolve.setOnClickListener(this);
        recyclerViewSection.addItemDecoration(mItemDecoration);
        recyclerViewSection.setLayoutManager(layoutManagerSection);
        recyclerViewSection.setAdapter(mAdapterSection);

        recyclerViewStd.setLayoutManager(layoutManagerStd);
        recyclerViewStd.setAdapter(mAdapterSectionStd);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fl_resolve:
                ArmsUtils.startActivity(ResolveTchActivity.class);
                break;
        }
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
        finish();
    }
}

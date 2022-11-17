package com.example.df.zhiyun.preview.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.preview.mvp.ui.adapter.ResolveTchIndexAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.df.zhiyun.preview.di.component.DaggerResolveStdComponent;
import com.example.df.zhiyun.preview.mvp.contract.ResolveStdContract;
import com.example.df.zhiyun.preview.mvp.presenter.ResolveStdPresenter;

import com.example.df.zhiyun.R;


import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:某个学生的题目分析页面
 * <p>
 * Created by MVPArmsTemplate on 04/30/2020 11:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ResolveStdActivity extends BaseActivity<ResolveStdPresenter> implements ResolveStdContract.View {
    @BindView(R.id.recyclerView_question)
    RecyclerView recyclerView;

    @Inject
    RecyclerView.ItemDecoration mItemDecoration;
    @Inject
    BaseMultiItemQuickAdapter mAdapterIndex;
    @Inject
    RecyclerView.LayoutManager layoutManagerIndex;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerResolveStdComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_resolve_std; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitle("宋茜茜");
        recyclerView.addItemDecoration(mItemDecoration);
        recyclerView.setLayoutManager(layoutManagerIndex);
        recyclerView.setAdapter(mAdapterIndex);
//        mAdapterIndex.setOnItemClickListener(itemClickListenerIndex);
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

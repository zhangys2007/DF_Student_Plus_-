package com.example.df.zhiyun.educate.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.df.zhiyun.educate.di.component.DaggerHwPutComponent;
import com.example.df.zhiyun.educate.mvp.contract.HwPutContract;
import com.example.df.zhiyun.educate.mvp.presenter.HwPutPresenter;

import com.example.df.zhiyun.R;


import javax.inject.Inject;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/08/2020 18:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HwPutActivity extends BaseActivity<HwPutPresenter> implements HwPutContract.View ,
    BaseMultiItemQuickAdapter.OnItemChildClickListener{
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.recyclerView_version)
    RecyclerView recyclerView;

    @Inject
    BaseMultiItemQuickAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager layoutManager;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHwPutComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_hw_put; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(this);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(this);

        tvVersion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ArmsUtils.startActivity(TextbookSelActivity.class);
            }
        });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if(view.getId() == R.id.fl_table){
            ArmsUtils.startActivity(DualTableActivity.class);
        }else if(view.getId() == R.id.fl_all){
            ArmsUtils.startActivity(PutClsHWActivity.class);
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

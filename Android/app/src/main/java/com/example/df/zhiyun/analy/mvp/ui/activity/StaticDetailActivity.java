package com.example.df.zhiyun.analy.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.example.df.zhiyun.preview.mvp.ui.fragment.ResolveTchOthFragment;
import com.example.df.zhiyun.preview.mvp.ui.fragment.ResolveTchSelFragmentFragment;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.df.zhiyun.analy.di.component.DaggerStaticDetailComponent;
import com.example.df.zhiyun.analy.mvp.contract.StaticDetailContract;
import com.example.df.zhiyun.analy.mvp.presenter.StaticDetailPresenter;

import com.example.df.zhiyun.R;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:错题统计详情
 * <p>
 * Created by MVPArmsTemplate on 05/16/2020 16:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class StaticDetailActivity extends BaseActivity<StaticDetailPresenter> implements StaticDetailContract.View {
    @BindView(R.id.fl_fragment)
    FrameLayout frameLayout;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerStaticDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_static_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if(Math.random() > 0.5){
            transaction.add(R.id.fl_fragment,ResolveTchSelFragmentFragment.newInstance(true));
        }else{
            transaction.add(R.id.fl_fragment, ResolveTchOthFragment.newInstance(true));
        }

        transaction.commit();
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

package com.example.df.zhiyun.login.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;

import com.example.df.zhiyun.login.di.component.DaggerIndexComponent;
import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.jaeger.library.StatusBarUtil;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.login.mvp.contract.IndexContract;
import com.example.df.zhiyun.login.mvp.presenter.IndexPresenter;

import javax.inject.Inject;

import butterknife.BindView;

import com.example.df.zhiyun.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/17/2019 09:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class IndexActivity extends BaseStatusActivity<IndexPresenter> implements IndexContract.View {
    @BindView(R.id.iv_splash)
    AppCompatImageView ivSplash;
    @Inject
    ImageLoader mImageLoader;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerIndexComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_index; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        mImageLoader.loadImage(this,
//                ImageConfigImpl
//                        .builder()
//                        .url(DrawableUrlUtils.imageTranslateUri(this,R.mipmap.splash))
//                        .isCrossFade(true)
//                        .imageView(ivSplash)
//                        .build());

//        StatusBarUtil.setTranslucentForImageViewInFragment(this, Color.TRANSPARENT ,null);  //透明状态栏
//        BarTextColorUtil.StatusBarLightMode(this);  //状态栏文字颜色
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageView(this, Color.TRANSPARENT ,null);  //透明状态栏
        mPresenter.startCountTime();
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

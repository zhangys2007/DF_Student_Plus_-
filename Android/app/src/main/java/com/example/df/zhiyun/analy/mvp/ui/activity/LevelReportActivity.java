package com.example.df.zhiyun.analy.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.example.df.zhiyun.analy.di.component.DaggerLevelReportComponent;
import com.example.df.zhiyun.analy.mvp.contract.LevelReportContract;
import com.example.df.zhiyun.analy.mvp.presenter.LevelReportPresenter;

import com.example.df.zhiyun.R;


import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 学业水平报告查询页面
 * <p>
 * Created by MVPArmsTemplate on 05/14/2020 09:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class LevelReportActivity extends BaseActivity<LevelReportPresenter> implements LevelReportContract.View
    , View.OnClickListener{
    @BindView(R.id.tv_search_paper)
    TextView tvSearchPaper;
    @BindView(R.id.tv_search_hw)
    TextView tvSearchHw;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLevelReportComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_level_report; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvSearchHw.setOnClickListener(this);
        tvSearchPaper.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_search_paper:
//                ArmsUtils.startActivity(KnowledgeHoldActivity.class);
                ArmsUtils.startActivity(LevelReportDetailActivity.class);
                break;
            case R.id.tv_search_hw:
                ArmsUtils.startActivity(LevelReportDetailActivity.class);
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

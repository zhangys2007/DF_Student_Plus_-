package com.example.df.zhiyun.analy.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.mvp.model.entity.QuestionScore;
import com.example.df.zhiyun.mvp.ui.adapter.QuestionScoreAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.analy.mvp.contract.AnalyReportContract;
import com.example.df.zhiyun.analy.mvp.model.AnalyReportModel;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/25/2019 15:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class AnalyReportModule {

    @Binds
    abstract AnalyReportContract.Model bindAnalyReportModel(AnalyReportModel model);

    @ActivityScope
    @Provides
    static KProgressHUD provideKP(AnalyReportContract.View view) {
        return KProgressHUD.create((Context) view)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @ActivityScope
    @Provides
    static String provideName(AnalyReportContract.View view) {
        return ((Activity)view).getIntent().getStringExtra(AnalyReportContract.View.KEY_ID);
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(AnalyReportContract.View view) {
        RecyclerView.LayoutManager  manager = new LinearLayoutManager((Context) view);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<QuestionScore> list){
        return new QuestionScoreAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<QuestionScore> provideDatas() {
        return new ArrayList<>();
    }
}
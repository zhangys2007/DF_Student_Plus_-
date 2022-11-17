package com.example.df.zhiyun.analy.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.analy.mvp.contract.KnowledgePointAnalysisContract;
import com.example.df.zhiyun.analy.mvp.model.KnowledgePointAnalysisModel;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.PointAnalysis;
import com.example.df.zhiyun.mvp.ui.adapter.KnowledgePointAnalysisAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 15:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class KnowledgePointAnalysisModule {

    @Binds
    abstract KnowledgePointAnalysisContract.Model bindClassItemModel(KnowledgePointAnalysisModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(KnowledgePointAnalysisContract.View view) {
        Context context = (Context)view;
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, ArmsUtils.dip2px(context,5)
                , ContextCompat.getColor(context, android.R.color.transparent));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(KnowledgePointAnalysisContract.View view) {
        Context context = (Context)view;
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(context);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<PointAnalysis> list){
        return new KnowledgePointAnalysisAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<PointAnalysis> provideDatas() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    @Named(KnowledgePointAnalysisContract.View.KEY_KNOWLEDGEID)
    static String provideKnowledgeId(KnowledgePointAnalysisContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), KnowledgePointAnalysisContract.View.KEY_KNOWLEDGEID);
    }


    @ActivityScope
    @Provides
    @Named(KnowledgePointAnalysisContract.View.KEY_CREATETIME)
    static String provideCreateTIme(KnowledgePointAnalysisContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), KnowledgePointAnalysisContract.View.KEY_CREATETIME);
    }

    @ActivityScope
    @Provides
    @Named(KnowledgePointAnalysisContract.View.KEY_CLASSID)
    static String provideClassId(KnowledgePointAnalysisContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), KnowledgePointAnalysisContract.View.KEY_CLASSID);
    }

    @ActivityScope
    @Provides
    @Named(KnowledgePointAnalysisContract.View.KEY_TITLE)
    static String provideTitle(KnowledgePointAnalysisContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), KnowledgePointAnalysisContract.View.KEY_TITLE);
    }

    @ActivityScope
    @Provides
    @Named(KnowledgePointAnalysisContract.View.KEY_SUBJECTID)
    static Integer provideSubjectId(KnowledgePointAnalysisContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(KnowledgePointAnalysisContract.View.KEY_SUBJECTID, Api.SUBJECT_CHINESE);
    }
}
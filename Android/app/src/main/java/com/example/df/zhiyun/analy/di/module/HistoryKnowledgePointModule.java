package com.example.df.zhiyun.analy.di.module;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.analy.mvp.contract.HistoryKnowledgePointContract;
import com.example.df.zhiyun.analy.mvp.model.HistoryKnowledgePointModel;
import com.example.df.zhiyun.mvp.model.entity.HistoryKnowledgePoint;
import com.example.df.zhiyun.mvp.ui.adapter.HistoryKnowledgePointsAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

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
public abstract class HistoryKnowledgePointModule {

    @Binds
    abstract HistoryKnowledgePointContract.Model bindClassItemModel(HistoryKnowledgePointModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(HistoryKnowledgePointContract.View view) {
        return new RecycleViewDivider((Context)view, LinearLayoutManager.VERTICAL,ArmsUtils.dip2px((Context)view,5)
                , ContextCompat.getColor((Context)view, R.color.divider));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(HistoryKnowledgePointContract.View view) {
        RecyclerView.LayoutManager  manager = new LinearLayoutManager((Context)view);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<HistoryKnowledgePoint> list){
        return new HistoryKnowledgePointsAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<HistoryKnowledgePoint> provideDatas() {
        return new ArrayList<>();
    }
}
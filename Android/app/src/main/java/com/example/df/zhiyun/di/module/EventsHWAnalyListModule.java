package com.example.df.zhiyun.di.module;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.contract.EventsHWAnalyListContract;
import com.example.df.zhiyun.mvp.model.EventsHWAnalyListModel;
import com.example.df.zhiyun.mvp.model.entity.GrowthTraceItem;
import com.example.df.zhiyun.analy.mvp.ui.adapter.GrowthTraceItemAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 10:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class EventsHWAnalyListModule {

    @Binds
    abstract EventsHWAnalyListContract.Model bindGrowthTraceListModel(EventsHWAnalyListModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(EventsHWAnalyListContract.View view) {
        return new RecycleViewDivider((Context)view, LinearLayoutManager.VERTICAL,1
                , ContextCompat.getColor((Context)view, R.color.divider));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(EventsHWAnalyListContract.View view) {
        RecyclerView.LayoutManager  manager = new LinearLayoutManager((Context)view);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<GrowthTraceItem> list){
        return new GrowthTraceItemAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<GrowthTraceItem> provideDatas() {
        return new ArrayList<>();
    }
}
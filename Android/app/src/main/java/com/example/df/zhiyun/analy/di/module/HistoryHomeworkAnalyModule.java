package com.example.df.zhiyun.analy.di.module;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.analy.mvp.contract.HistoryHomeworkAnalyContract;
import com.example.df.zhiyun.analy.mvp.model.HistoryHomeworkAnalyModel;
import com.example.df.zhiyun.mvp.model.adapterEntity.HistoryHomeworkAnalyMultipleItem;
import com.example.df.zhiyun.mvp.ui.adapter.HistoryHomeworkAnalyAdapter;
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
public abstract class HistoryHomeworkAnalyModule {

    @Binds
    abstract HistoryHomeworkAnalyContract.Model bindClassItemModel(HistoryHomeworkAnalyModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(HistoryHomeworkAnalyContract.View view) {
        return new RecycleViewDivider((Context)view, LinearLayoutManager.VERTICAL,ArmsUtils.dip2px((Context)view,10)
                , ContextCompat.getColor((Context)view, R.color.divider));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(HistoryHomeworkAnalyContract.View view) {
        RecyclerView.LayoutManager  manager = new LinearLayoutManager((Context)view);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<HistoryHomeworkAnalyMultipleItem> list){
        return new HistoryHomeworkAnalyAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<HistoryHomeworkAnalyMultipleItem> provideDatas() {
        return new ArrayList<>();
    }
}
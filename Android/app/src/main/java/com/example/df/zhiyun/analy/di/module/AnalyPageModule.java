package com.example.df.zhiyun.analy.di.module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.analy.mvp.contract.AnalyPageContract;
import com.example.df.zhiyun.analy.mvp.model.AnalyPageModel;
import com.example.df.zhiyun.analy.mvp.model.entity.LevelItem;
import com.example.df.zhiyun.analy.mvp.model.entity.RateItem;
import com.example.df.zhiyun.analy.mvp.ui.adapter.LevelAdapter;
import com.example.df.zhiyun.analy.mvp.ui.adapter.RateAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.jess.arms.di.scope.FragmentScope;

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
 * Created by MVPArmsTemplate on 04/23/2020 15:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class AnalyPageModule {

    @Binds
    abstract AnalyPageContract.Model bindAnalyPageModel(AnalyPageModel model);

    @FragmentScope
    @Named(AnalyPageContract.View.KEY_RATE)
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(AnalyPageContract.View view) {
        Context context = ((Fragment)view).getContext();
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }


    @FragmentScope
    @Named(AnalyPageContract.View.KEY_RATE)
    @Provides
    static BaseQuickAdapter provideAdapter(List<RateItem> list){
        return new RateAdapter(list);
    }

    @FragmentScope
    @Provides
    static List<RateItem> provideList() {
        List<RateItem> list = new ArrayList<>();
        for(int i=0;i<20;i++){
            RateItem item = new RateItem();
            list.add(item);
        }
        return list;
    }

    @FragmentScope
    @Provides
    static RecyclerView.ItemDecoration provideDecoration(AnalyPageContract.View view) {
        Context context = ((Fragment)view).getContext();
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,10
                , ContextCompat.getColor(context, R.color.white));
    }

    @FragmentScope
    @Named(AnalyPageContract.View.KEY_LEVEL)
    @Provides
    static RecyclerView.LayoutManager provideTodoLayoutManager(AnalyPageContract.View view) {
        Context context = ((Fragment)view).getContext();
        LinearLayoutManager  manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @FragmentScope
    @Named(AnalyPageContract.View.KEY_LEVEL)
    @Provides
    static BaseQuickAdapter provideTodoAdapter(List<LevelItem> list){
        return new LevelAdapter(list);
    }

    @FragmentScope
    @Provides
    static List<LevelItem> provideListTodo() {
        List<LevelItem> list = new ArrayList<>();
        for(int i=0;i<2;i++){
            LevelItem item = new LevelItem();
            list.add(item);
        }
        return list;
    }
}
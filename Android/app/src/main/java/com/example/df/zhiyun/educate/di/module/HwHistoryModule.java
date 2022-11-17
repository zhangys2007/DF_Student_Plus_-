package com.example.df.zhiyun.educate.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.analy.mvp.model.entity.LevelItem;
import com.example.df.zhiyun.analy.mvp.ui.adapter.LevelAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.educate.mvp.contract.HwHistoryContract;
import com.example.df.zhiyun.educate.mvp.model.HwHistoryModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/07/2020 17:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HwHistoryModule {

    @Binds
    abstract HwHistoryContract.Model bindHwHistoryModel(HwHistoryModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideDecoration(HwHistoryContract.View view) {
        Context context = (Activity)view;
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,10
                , ContextCompat.getColor(context, R.color.white));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideTodoLayoutManager(HwHistoryContract.View view) {
        Context context = (Activity)view;
        LinearLayoutManager  manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideTodoAdapter(List<LevelItem> list){
        return new LevelAdapter(list);
    }

    @ActivityScope
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
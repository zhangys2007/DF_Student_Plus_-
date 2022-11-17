package com.example.df.zhiyun.plan.di.module;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.model.entity.Plan;
import com.example.df.zhiyun.mvp.ui.adapter.PlanAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.plan.mvp.contract.PlanContract;
import com.example.df.zhiyun.plan.mvp.model.PlanModel;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/03/2019 16:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class PlanModule {

    @Binds
    abstract PlanContract.Model bindPlanModel(PlanModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(PlanContract.View view) {
        Activity activity = (Activity)view;
        return new RecycleViewDivider(activity, LinearLayoutManager.VERTICAL,1
                , ContextCompat.getColor(activity, R.color.divider));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(PlanContract.View view) {
        Activity activity = (Activity)view;
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(activity);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<Plan> list){
        return new PlanAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<Plan> provideDatas() {
        return new ArrayList<>();
    }

}
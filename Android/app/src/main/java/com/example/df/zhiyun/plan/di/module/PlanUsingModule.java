package com.example.df.zhiyun.plan.di.module;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.model.entity.PlanUsage;
import com.example.df.zhiyun.mvp.ui.adapter.PlanUsageAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.plan.mvp.contract.PlanUsingContract;
import com.example.df.zhiyun.plan.mvp.model.PlanUsingModel;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/30/2019 15:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class PlanUsingModule {

    @Binds
    abstract PlanUsingContract.Model bindPlanUsingModel(PlanUsingModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(PlanUsingContract.View view) {
        Context context = (Context)view;
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, ArmsUtils.dip2px(context,1)
                , ContextCompat.getColor(context, R.color.divider));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(PlanUsingContract.View view) {
        Context context = (Context)view;
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(context);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<PlanUsage> list){
        return new PlanUsageAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<PlanUsage> provideDatas() {
        return new ArrayList<>();
    }
}
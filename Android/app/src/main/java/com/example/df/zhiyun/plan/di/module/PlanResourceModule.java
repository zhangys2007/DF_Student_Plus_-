package com.example.df.zhiyun.plan.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.mvp.ui.adapter.PlanResourceAdapter;
import com.example.df.zhiyun.mvp.ui.widget.GridDividerItemDecoration;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.plan.mvp.contract.PlanResourceContract;
import com.example.df.zhiyun.plan.mvp.model.PlanResourceModel;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/04/2019 11:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class PlanResourceModule {

    @Binds
    abstract PlanResourceContract.Model bindPlanResourceModel(PlanResourceModel model);



    @ActivityScope
    @Provides
    @Named("id")
    static String provideId(PlanResourceContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(),PlanResourceContract.View.KEY_ID);
//        return ((Activity)view).getIntent().getStringExtra(PlanResourceContract.View.KEY_ID);
    }

    @ActivityScope
    @Provides
    @Named("uuid")
    static String provideUUId(PlanResourceContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(),PlanResourceContract.View.KEY_UUID);
//        return ((Activity)view).getIntent().getStringExtra(PlanResourceContract.View.KEY_UUID);
    }

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(PlanResourceContract.View view) {
        Context context = (Context)view;
        return new GridDividerItemDecoration(ArmsUtils.dip2px(context,10),
                ContextCompat.getColor(context,android.R.color.transparent));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(PlanResourceContract.View view) {
        Context context = (Context)view;
        RecyclerView.LayoutManager  manager = new GridLayoutManager(context, 3);
        return manager;
    }


    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<String> list){
        return new PlanResourceAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<String> provideDataList() {
        return new ArrayList<>();
    }
}
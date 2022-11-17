package com.example.df.zhiyun.organize.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.organize.mvp.contract.GradeItemContract;
import com.example.df.zhiyun.organize.mvp.model.GradeItemModel;
import com.example.df.zhiyun.organize.mvp.ui.activity.GradeItemActivity;
import com.example.df.zhiyun.mvp.ui.adapter.GradeItemAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.R;

import com.example.df.zhiyun.mvp.model.entity.GradeItem;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 14:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class GradeItemModule {

    @Binds
    abstract GradeItemContract.Model bindGradeItemModel(GradeItemModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(GradeItemContract.View view) {
        Context context = (Context)view;
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, ArmsUtils.dip2px(context,5)
                , ContextCompat.getColor(context, R.color.divider));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(GradeItemContract.View view) {
        Context context = (Context)view;
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(context);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<GradeItem> list){
        return new GradeItemAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<GradeItem> provideDatas() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    @Named("id")
    static String provideGradeId(GradeItemContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(),GradeItemActivity.KEY);
//        return ((Activity)view).getIntent().getStringExtra(GradeItemActivity.KEY);
    }

    @ActivityScope
    @Provides
    @Named("name")
    static String provideGradeName(GradeItemContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(),GradeItemActivity.Name);
//        return ((Activity)view).getIntent().getStringExtra(GradeItemActivity.Name);
    }
}
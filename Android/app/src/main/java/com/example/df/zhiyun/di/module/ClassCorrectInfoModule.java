package com.example.df.zhiyun.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.contract.ClassCorrectInfoContract;
import com.example.df.zhiyun.mvp.model.ClassCorrectInfoModel;
import com.example.df.zhiyun.mvp.model.entity.ClassCorrectInfo;
import com.example.df.zhiyun.mvp.ui.adapter.ClassCorrectInfoAdapter;
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
public abstract class ClassCorrectInfoModule {

    @Binds
    abstract ClassCorrectInfoContract.Model bindClassItemModel(ClassCorrectInfoModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(ClassCorrectInfoContract.View view) {
        Context context = (Context)view;
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, ArmsUtils.dip2px(context,10)
                , ContextCompat.getColor(context, R.color.bg_grey));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(ClassCorrectInfoContract.View view) {
        Context context = (Context)view;
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(context);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<ClassCorrectInfo> list){
        return new ClassCorrectInfoAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<ClassCorrectInfo> provideDatas() {
        return new ArrayList<>();
    }


    @ActivityScope
    @Provides
    @Named(ClassCorrectInfoContract.View.KEY_FZ)
    static Integer provideFZId(ClassCorrectInfoContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(ClassCorrectInfoContract.View.KEY_FZ, 0);
    }

    @ActivityScope
    @Provides
    @Named(ClassCorrectInfoContract.View.KEY_TYPE)
    static Integer provideTYPE(ClassCorrectInfoContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(ClassCorrectInfoContract.View.KEY_TYPE, 0);
    }

    @ActivityScope
    @Provides
    @Named(ClassCorrectInfoContract.View.KEY_SCHOOLID)
    static Integer provideSchoolId(ClassCorrectInfoContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(ClassCorrectInfoContract.View.KEY_SCHOOLID, 0);
    }

    @ActivityScope
    @Provides
    @Named(ClassCorrectInfoContract.View.KEY_GRADEID)
    static Integer provideGradeId(ClassCorrectInfoContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(ClassCorrectInfoContract.View.KEY_GRADEID, 0);
    }

    @ActivityScope
    @Provides
    @Named(ClassCorrectInfoContract.View.KEY_SUBJID)
    static Integer provideSubjId(ClassCorrectInfoContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(ClassCorrectInfoContract.View.KEY_SUBJID, 0);
    }
}
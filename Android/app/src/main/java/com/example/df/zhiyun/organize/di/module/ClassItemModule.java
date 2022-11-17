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
import com.example.df.zhiyun.organize.mvp.contract.ClassItemContract;
import com.example.df.zhiyun.organize.mvp.model.ClassItemModel;
import com.example.df.zhiyun.mvp.ui.adapter.ClassItemAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.R;

import com.example.df.zhiyun.mvp.model.entity.ClassItem;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;


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
public abstract class ClassItemModule {

    @Binds
    abstract ClassItemContract.Model bindClassItemModel(ClassItemModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(ClassItemContract.View view) {
        Context context = (Context)view;
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, ArmsUtils.dip2px(context,5)
                , ContextCompat.getColor(context, R.color.divider));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(ClassItemContract.View view) {
        Context context = (Context)view;
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(context);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<ClassItem> list){
        return new ClassItemAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<ClassItem> provideDatas() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    @Named("id")
    static String provideId(ClassItemContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), ClassItemContract.View.KEY);
//        return ((Activity)view).getIntent().getStringExtra(ClassItemContract.View.KEY);
    }

    @ActivityScope
    @Provides
    @Named("name")
    static String provideName(ClassItemContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), ClassItemContract.View.Name);
//        return ((Activity)view).getIntent().getStringExtra(ClassItemContract.View.Name);
    }
}
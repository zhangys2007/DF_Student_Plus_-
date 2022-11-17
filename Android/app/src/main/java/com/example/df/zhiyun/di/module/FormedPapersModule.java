package com.example.df.zhiyun.di.module;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.model.entity.FormedPaper;
import com.example.df.zhiyun.mvp.ui.adapter.FormedPaperAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.mvp.contract.FormedPapersContract;
import com.example.df.zhiyun.mvp.model.FormedPapersModel;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 13:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class FormedPapersModule {

    @Binds
    abstract FormedPapersContract.Model bindFormedPapersModel(FormedPapersModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(FormedPapersContract.View view) {
        return new RecycleViewDivider((Activity)view, LinearLayoutManager.VERTICAL,1
                , ContextCompat.getColor((Activity)view, R.color.divider));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(FormedPapersContract.View view) {
        RecyclerView.LayoutManager  manager = new LinearLayoutManager((Activity)view);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<FormedPaper> list){
        return new FormedPaperAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<FormedPaper> provideDatas() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static Integer provideType(FormedPapersContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(FormedPapersContract.View.KEY_TYPE,0);
    }
}
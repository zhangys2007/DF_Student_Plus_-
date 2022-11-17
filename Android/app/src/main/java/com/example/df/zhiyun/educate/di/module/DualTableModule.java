package com.example.df.zhiyun.educate.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.educate.mvp.contract.TextbookSelContract;
import com.example.df.zhiyun.educate.mvp.ui.adapter.DualTableAdapter;
import com.example.df.zhiyun.educate.mvp.ui.adapter.TextbookSelAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.educate.mvp.contract.DualTableContract;
import com.example.df.zhiyun.educate.mvp.model.DualTableModel;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/12/2020 09:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class DualTableModule {

    @Binds
    abstract DualTableContract.Model bindDualTableModel(DualTableModel model);

    @Provides
    @ActivityScope
    static RecyclerView.LayoutManager provideLayoutManager(DualTableContract.View view) {
        RecyclerView.LayoutManager  manager = new LinearLayoutManager((Context)view);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @Provides
    @ActivityScope
    static BaseQuickAdapter provideAdapter(){
        List<String> list = new ArrayList<>();
        for(int i=0;i<20;i++){
            list.add("人教版："+i);
        }
        return new DualTableAdapter(list);
    }
}
package com.example.df.zhiyun.analy.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.analy.mvp.model.entity.KnowledgeHold;
import com.example.df.zhiyun.analy.mvp.model.entity.LevelItem;
import com.example.df.zhiyun.analy.mvp.ui.adapter.LevelAdapter;
import com.example.df.zhiyun.educate.mvp.ui.adapter.KnowledgeHoldAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.analy.mvp.contract.KnowledgeHoldContract;
import com.example.df.zhiyun.analy.mvp.model.KnowledgeHoldModel;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/14/2020 11:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class KnowledgeHoldModule {

    @Binds
    abstract KnowledgeHoldContract.Model bindKnowledgeHoldModel(KnowledgeHoldModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideDecoration(KnowledgeHoldContract.View view) {
        Context context = (Activity)view;
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,10
                , ContextCompat.getColor(context, R.color.white));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideTodoLayoutManager(KnowledgeHoldContract.View view) {
        Context context = (Activity)view;
        LinearLayoutManager  manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideTodoAdapter(List<KnowledgeHold> list){
        return new KnowledgeHoldAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<KnowledgeHold> provideListTodo() {
        List<KnowledgeHold> list = new ArrayList<>();
        for(int i=0;i<2;i++){
            KnowledgeHold item = new KnowledgeHold();
            item.setContent("第 3 题、第 8 题、第11题");
            item.setCount(24);
            item.setName("作业名称作业名称作业名称作业名称作业名称");
            item.setRate(51.7f);
            item.setTime(1589427049);
            list.add(item);
        }
        return list;
    }
}
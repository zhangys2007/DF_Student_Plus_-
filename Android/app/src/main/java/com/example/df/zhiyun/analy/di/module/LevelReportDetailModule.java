package com.example.df.zhiyun.analy.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.analy.mvp.contract.LevelReportDetailContract;
import com.example.df.zhiyun.analy.mvp.model.entity.KnowledgeHold;
import com.example.df.zhiyun.analy.mvp.ui.adapter.HWQuesAdapter;
import com.example.df.zhiyun.analy.mvp.ui.adapter.HWStaticAdapter;
import com.example.df.zhiyun.analy.mvp.ui.adapter.PointHoldAdapter;
import com.example.df.zhiyun.educate.mvp.ui.adapter.KnowledgeHoldAdapter;
import com.example.df.zhiyun.mvp.contract.ErrorExerContract;
import com.example.df.zhiyun.mvp.model.adapterEntity.QuestionMultipleItem;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.ui.adapter.QuestionAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.analy.mvp.contract.LevelReportDetailContract;
import com.example.df.zhiyun.analy.mvp.model.LevelReportDetailModel;
import com.jess.arms.di.scope.FragmentScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/15/2020 13:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class LevelReportDetailModule {

    @Binds
    abstract LevelReportDetailContract.Model bindLevelReportDetailModel(LevelReportDetailModel model);

    @ActivityScope
    @Named(LevelReportDetailContract.View.KEY_STATUS)
    @Provides
    static RecyclerView.ItemDecoration provideDecoration(LevelReportDetailContract.View view) {
        Context context = (Activity)view;
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,1
                , ContextCompat.getColor(context, R.color.divider));
    }

    @ActivityScope
    @Named(LevelReportDetailContract.View.KEY_STATUS)
    @Provides
    static RecyclerView.LayoutManager provideTodoLayoutManager(LevelReportDetailContract.View view) {
        Context context = (Activity)view;
        LinearLayoutManager  manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Named(LevelReportDetailContract.View.KEY_STATUS)
    @Provides
    static BaseQuickAdapter provideTodoAdapter(){
        List<String> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add("1");
        }
        return new HWStaticAdapter(list);
    }



    @ActivityScope
    @Named(LevelReportDetailContract.View.KEY_POINT)
    @Provides
    static RecyclerView.ItemDecoration provideDecorationPoint(LevelReportDetailContract.View view) {
        Context context = (Activity)view;
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,1
                , ContextCompat.getColor(context, R.color.divider));
    }

    @ActivityScope
    @Named(LevelReportDetailContract.View.KEY_POINT)
    @Provides
    static RecyclerView.LayoutManager provideTodoLayoutManagerPoint(LevelReportDetailContract.View view) {
        Context context = (Activity)view;
        LinearLayoutManager  manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Named(LevelReportDetailContract.View.KEY_POINT)
    @Provides
    static BaseQuickAdapter provideTodoAdapterPoint(){
        List<String> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add("1");
        }
        return new PointHoldAdapter(list);
    }


    @ActivityScope
    @Named(LevelReportDetailContract.View.KEY_HW)
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(LevelReportDetailContract.View view) {
        Context context = (Activity)view;
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(context);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Named(LevelReportDetailContract.View.KEY_HW)
    @Provides
    static BaseMultiItemQuickAdapter provideAdapter(List<QuestionMultipleItem> list){
        return new HWQuesAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<QuestionMultipleItem> provideHomeworkList() {
        List<QuestionMultipleItem> list = new ArrayList<>();
        QuestionMultipleItem item;
        for(int i=0;i<12;i++){
            if(i%3 == 0){
              item  = new QuestionMultipleItem(Api.QUESTION_HEAD,null);
            }else{
                item  = new QuestionMultipleItem(Api.QUESTION_SELECT,null);
            }
            list.add(item);
        }
        return list;
    }
}
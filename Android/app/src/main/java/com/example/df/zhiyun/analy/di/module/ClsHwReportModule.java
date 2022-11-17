package com.example.df.zhiyun.analy.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.analy.mvp.contract.ClsHwReportContract;
import com.example.df.zhiyun.analy.mvp.model.ClsHwReportModel;
import com.example.df.zhiyun.analy.mvp.model.entity.ScoreSection;
import com.example.df.zhiyun.analy.mvp.ui.adapter.RepStdAdapter;
import com.example.df.zhiyun.analy.mvp.ui.adapter.ScoreSectionAdapter;
import com.example.df.zhiyun.main.mvp.model.entity.FocusStd;
import com.example.df.zhiyun.mvp.ui.widget.GridDividerItemDecoration;
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
 * Created by MVPArmsTemplate on 04/25/2020 10:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class ClsHwReportModule {

    @Binds
    abstract ClsHwReportContract.Model bindClsHwReportModel(ClsHwReportModel model);

    @ActivityScope
    @Named(ClsHwReportContract.View.KEY_SECTION)
    @Provides
    static RecyclerView.ItemDecoration provideDecoration(ClsHwReportContract.View view) {
        Context context = (Activity)view;
        return new GridDividerItemDecoration(ArmsUtils.dip2px(context,20)
                , ContextCompat.getColor(context, R.color.white));
    }

    @Provides
    @Named(ClsHwReportContract.View.KEY_SECTION)
    @ActivityScope
    static RecyclerView.LayoutManager provideLayoutManager(ClsHwReportContract.View view) {
        RecyclerView.LayoutManager  manager = new GridLayoutManager((Activity)view, 6);
        return manager;
    }


    @Provides
    @Named(ClsHwReportContract.View.KEY_SECTION)
    @ActivityScope
    static BaseQuickAdapter provideAdapter(){
        List<ScoreSection> list = new ArrayList<>();
        for(int i=0;i<20;i++){
            ScoreSection item = new ScoreSection();
            list.add(item);
            item.setState(i%4);
            item.setName(Integer.toString(i));
        }
        return new ScoreSectionAdapter(list);
    }


    @Provides
    @Named(ClsHwReportContract.View.KEY_STUDENT)
    @ActivityScope
    static RecyclerView.LayoutManager provideLayoutManagerStd(ClsHwReportContract.View view) {
        LinearLayoutManager  manager = new LinearLayoutManager((Activity)view);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }


    @Provides
    @Named(ClsHwReportContract.View.KEY_STUDENT)
    @ActivityScope
    static BaseQuickAdapter provideAdapterstd(){
        List<FocusStd> list = new ArrayList<>();
        for(int i=0;i<3;i++){
            FocusStd item = new FocusStd();
            list.add(item);
            item.setClassName(String.format("班级%d",i));
            item.setRink(i+1);
            item.setRise(i);
            item.setRealName(String.format("学生%d",i));
        }
        return new RepStdAdapter(list);
    }
}
package com.example.df.zhiyun.di.module;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.model.entity.HomeworkArrange;
import com.example.df.zhiyun.mvp.ui.adapter.HomeworkPutAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.mvp.contract.ArrangeHWContract;
import com.example.df.zhiyun.mvp.model.ArrangeHWModel;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/29/2019 16:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class ArrangeHWModule {

    @Binds
    abstract ArrangeHWContract.Model bindArrangeHWModel(ArrangeHWModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(ArrangeHWContract.View view) {
        Context context = (Context)view;
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,
                ArmsUtils.dip2px(context,5)
                , ContextCompat.getColor(context, R.color.bg_grey));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(ArrangeHWContract.View view) {
        Context context = (Context)view;
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(context);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<HomeworkArrange> list){
        return new HomeworkPutAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<HomeworkArrange> provideHomeworkList() {
        return new ArrayList<>();
    }
}
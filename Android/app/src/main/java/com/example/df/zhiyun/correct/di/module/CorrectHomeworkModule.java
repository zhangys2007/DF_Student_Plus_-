package com.example.df.zhiyun.correct.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.correct.mvp.contract.CorrectHomeworkContract;
import com.example.df.zhiyun.correct.mvp.model.CorrectHomeworkModel;
import com.example.df.zhiyun.correct.mvp.ui.adapter.CorrectHomeworkAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.R;

import com.example.df.zhiyun.mvp.model.entity.StudentHomework;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 18:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class CorrectHomeworkModule {

    @Binds
    abstract CorrectHomeworkContract.Model bindCorrectHomeworkModel(CorrectHomeworkModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(CorrectHomeworkContract.View view) {
        Context context = ((Activity)view);
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, ArmsUtils.dip2px(context,1)
                , ContextCompat.getColor(context, R.color.divider));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(CorrectHomeworkContract.View view) {
        Context context = ((Activity)view);
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(context);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<StudentHomework> list,Boolean isCorrect){
        return new CorrectHomeworkAdapter(list,isCorrect);
    }

    @ActivityScope
    @Provides
    static List<StudentHomework> provideDatas() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static String provideId(CorrectHomeworkContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), CorrectHomeworkContract.View.KEY);
//        return ((Activity)view).getIntent().getStringExtra(CorrectHomeworkContract.View.KEY);
    }

    @ActivityScope
    @Provides
    static Boolean provideCorrect(CorrectHomeworkContract.View view) {
        return ((Activity)view).getIntent().getBooleanExtra(CorrectHomeworkContract.View.CORRECT,false);
    }
}
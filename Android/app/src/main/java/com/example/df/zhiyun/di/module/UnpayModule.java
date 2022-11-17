package com.example.df.zhiyun.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.mvp.contract.UnpayContract;
import com.example.df.zhiyun.mvp.model.UnpayModel;
import com.example.df.zhiyun.mvp.ui.adapter.UnpayAdapter;

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
 * Created by MVPArmsTemplate on 08/07/2019 17:16
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class UnpayModule {

    @Binds
    abstract UnpayContract.Model bindUnpayModel(UnpayModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(UnpayContract.View view) {
        Context context = ((Activity)view);
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,1
                , ContextCompat.getColor(context, R.color.divider));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(UnpayContract.View view) {
        Context context = ((Activity)view);
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(context);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<StudentHomework> list){
        return new UnpayAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<StudentHomework> provideDatas() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static String provideId(UnpayContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(),UnpayContract.View.KEY);
//        return ((Activity)view).getIntent().getStringExtra(UnpayContract.View.KEY);
    }
}
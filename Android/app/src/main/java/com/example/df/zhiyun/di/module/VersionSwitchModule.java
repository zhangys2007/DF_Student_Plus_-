package com.example.df.zhiyun.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.mvp.contract.VersionSwitchContract;
import com.example.df.zhiyun.mvp.model.VersionSwitchModel;
import com.example.df.zhiyun.mvp.ui.adapter.VersionAdapter;
import com.example.df.zhiyun.mvp.ui.widget.VersionRecycleViewGridDivider;

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
 * Created by MVPArmsTemplate on 08/21/2019 13:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class VersionSwitchModule {

    @Binds
    abstract VersionSwitchContract.Model bindVersionSwitchModel(VersionSwitchModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(VersionSwitchContract.View view) {
        Context context = (Context)view;
        return new VersionRecycleViewGridDivider(ArmsUtils.dip2px(context,10)
                , ContextCompat.getColor(context, android.R.color.transparent));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(VersionSwitchContract.View view) {
//        Activity activity = (Activity)view;
        RecyclerView.LayoutManager  manager = new GridLayoutManager((Activity)view, 3);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseMultiItemQuickAdapter provideAdapter(List<MultiItemEntity> list){
        return new VersionAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<MultiItemEntity> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    @Named("subjId")
    static String provideId(VersionSwitchContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(),VersionSwitchContract.View.KEY_SUBJ);
//        return ((Activity)view).getIntent().getStringExtra(VersionSwitchContract.View.KEY_SUBJ);
    }
}
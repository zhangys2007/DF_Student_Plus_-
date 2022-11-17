package com.example.df.zhiyun.preview.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.common.mvp.model.entity.CommonExpandableItem;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.example.df.zhiyun.preview.mvp.contract.ResolveStdContract;
import com.example.df.zhiyun.preview.mvp.ui.adapter.ResolveStdAdapter;
import com.example.df.zhiyun.preview.mvp.ui.adapter.ResolveTchIndexAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.preview.mvp.contract.ResolveStdContract;
import com.example.df.zhiyun.preview.mvp.model.ResolveStdModel;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/30/2020 11:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class ResolveStdModule {

    @Binds
    abstract ResolveStdContract.Model bindResolveStdModel(ResolveStdModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideDecoration(ResolveStdContract.View view) {
        Context context = (Activity)view;
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL
                , ArmsUtils.dip2px(context,5)
                , ContextCompat.getColor(context, R.color.seperate));
    }

    @Provides
    @ActivityScope
    static RecyclerView.LayoutManager provideLayoutManagerStd(ResolveStdContract.View view) {
        LinearLayoutManager manager = new LinearLayoutManager((Activity)view);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @Provides
    @ActivityScope
    static BaseMultiItemQuickAdapter provideAdapter(){
        List<MultiItemEntity> list = new ArrayList<>();
        for(int i=0;i<20;i++){
            list.add(new CommonExpandableItem(null,i%2));
        }
        return new ResolveStdAdapter(list);
    }
}
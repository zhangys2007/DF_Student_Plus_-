package com.example.df.zhiyun.preview.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.example.df.zhiyun.preview.mvp.contract.ResolveTchContract;
import com.example.df.zhiyun.preview.mvp.model.ResolveTchModel;
import com.example.df.zhiyun.preview.mvp.ui.adapter.ResolveTchIndexAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.ArmsUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/27/2020 18:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class ResolveTchModule {

    @Binds
    abstract ResolveTchContract.Model bindResolveTchModel(ResolveTchModel model);

    @ActivityScope
    @Provides
    static KProgressHUD provideKP(ResolveTchContract.View view) {
        return KProgressHUD.create((Context) view)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }


    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideDecoration(ResolveTchContract.View view) {
        Context context = (Activity)view;
        return new RecycleViewDivider(context,LinearLayoutManager.HORIZONTAL
                ,ArmsUtils.dip2px(context,15)
                , ContextCompat.getColor(context, R.color.white));
    }

    @Provides
    @ActivityScope
    static RecyclerView.LayoutManager provideLayoutManagerStd(ResolveTchContract.View view) {
        LinearLayoutManager manager = new LinearLayoutManager((Activity)view);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return manager;
    }

    @Provides
    @ActivityScope
    static BaseQuickAdapter provideAdapter(){
        List<Integer> list = new ArrayList<>();
        for(int i=0;i<20;i++){
            Integer item = new Integer(i+1);
            list.add(item);
        }
        return new ResolveTchIndexAdapter(list);
    }
}
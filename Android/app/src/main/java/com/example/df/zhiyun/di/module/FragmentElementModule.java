package com.example.df.zhiyun.di.module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.IView;
import com.kaopiz.kprogresshud.KProgressHUD;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class FragmentElementModule {

    @FragmentScope
    @Provides
    static Context provideContext(IView view) {
        return ((Fragment)view).getContext();

    }

    @FragmentScope
    @Provides
    static KProgressHUD provideKP(Context context) {
        return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @FragmentScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(Context context) {
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,1
                , ContextCompat.getColor(context, R.color.divider));
    }

    @FragmentScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(Context context) {
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(context);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }
}

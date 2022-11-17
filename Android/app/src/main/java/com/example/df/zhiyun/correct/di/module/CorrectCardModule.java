package com.example.df.zhiyun.correct.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.example.df.zhiyun.correct.mvp.contract.CorrectCardContract;
import com.example.df.zhiyun.correct.mvp.model.CorrectCardModel;
import com.example.df.zhiyun.mvp.model.adapterEntity.CorrectCardMultipleItem;
import com.example.df.zhiyun.correct.mvp.ui.adapter.CorrectCardAdapter;
import com.jess.arms.di.scope.ActivityScope;
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
 * Created by MVPArmsTemplate on 08/09/2019 12:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class CorrectCardModule {

    @Binds
    abstract CorrectCardContract.Model bindCardModel(CorrectCardModel model);

    @ActivityScope
    @Provides
    static KProgressHUD provideKP(CorrectCardContract.View view) {
        return KProgressHUD.create((Context) view)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }


    @Provides
    @ActivityScope
    static RecyclerView.LayoutManager provideLayoutManager(CorrectCardContract.View view) {
        RecyclerView.LayoutManager  manager = new GridLayoutManager((Activity)view, 10);
        return manager;
    }


    @Provides
    @ActivityScope
    static BaseMultiItemQuickAdapter provideAdapter(){
        List<CorrectCardMultipleItem> list = new ArrayList<>();
        return new CorrectCardAdapter(list);
    }

    @ActivityScope
    @Provides
    static Boolean provideCorrect(CorrectCardContract.View view) {
        return ((Activity)view).getIntent().getBooleanExtra(CorrectCardContract.View.KEY_CORRECT,false);
    }
}
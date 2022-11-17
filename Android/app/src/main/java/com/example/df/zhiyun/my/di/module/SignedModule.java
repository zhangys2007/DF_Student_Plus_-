package com.example.df.zhiyun.my.di.module;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.my.mvp.model.SignedModel;
import com.example.df.zhiyun.mvp.model.adapterEntity.SignedInfoMultipleItem;
import com.example.df.zhiyun.mvp.ui.adapter.SignedAdapter;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.my.mvp.contract.SignedContract;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/31/2019 10:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class SignedModule {

    @Binds
    abstract SignedContract.Model bindSignedModel(SignedModel model);


    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(SignedContract.View view) {
        RecyclerView.LayoutManager  manager = new GridLayoutManager(view.getPageContext(), 7);
        return manager;
    }

    @ActivityScope
    @Provides
    static KProgressHUD provideKP(SignedContract.View view) {
        return KProgressHUD.create((Context) view)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @ActivityScope
    @Provides
    static BaseMultiItemQuickAdapter provideAdapter(List<SignedInfoMultipleItem> list){
        return new SignedAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<SignedInfoMultipleItem> provideDataList() {
        return new ArrayList<>();
    }

}
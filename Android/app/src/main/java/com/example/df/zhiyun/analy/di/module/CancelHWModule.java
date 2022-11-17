package com.example.df.zhiyun.analy.di.module;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.mvp.contract.CancelHWContract;
import com.example.df.zhiyun.mvp.model.CancelHWModel;
import com.example.df.zhiyun.educate.mvp.model.entity.BelongClass;
import com.example.df.zhiyun.mvp.ui.adapter.CancelHWClassAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.IView;

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
 * Created by MVPArmsTemplate on 08/30/2019 15:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class CancelHWModule {

    @Binds
    abstract CancelHWContract.Model bindPutHWModel(CancelHWModel model);

//    @ActivityScope
//    @Provides
//    static RecyclerView.LayoutManager provideLayoutManager(PutClsHWContract.View view) {
//        RecyclerView.LayoutManager  manager = new LinearLayoutManager((Activity)view);
//        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
//        return manager;
//    }

    @ActivityScope
    @Provides
    static IView provideBaseIView(CancelHWContract.View view){
        return (IView)view;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<BelongClass> list){
        return new CancelHWClassAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<BelongClass> provideDatas() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    @Named(CancelHWContract.View.KEY_PAPER_NAME)
    static String providePaperName(CancelHWContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), CancelHWContract.View.KEY_PAPER_NAME);
//        return ((Activity)view).getIntent().getStringExtra(CancelHWContract.View.KEY_PAPER_NAME);
    }

    @ActivityScope
    @Provides
    @Named(CancelHWContract.View.KEY_PAPER_ID)
    static String providePaperId(CancelHWContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), CancelHWContract.View.KEY_PAPER_ID);
//        return ((Activity)view).getIntent().getStringExtra(CancelHWContract.View.KEY_PAPER_ID);
    }

    @ActivityScope
    @Provides
    @Named(CancelHWContract.View.KEY_SYSTEM_ID)
    static String provideSystemId(CancelHWContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), CancelHWContract.View.KEY_SYSTEM_ID);
//        return ((Activity)view).getIntent().getStringExtra(CancelHWContract.View.KEY_SYSTEM_ID);
    }

    @ActivityScope
    @Provides
    @Named(CancelHWContract.View.KEY_LINK_ID)
    static String provideLinkId(CancelHWContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), CancelHWContract.View.KEY_LINK_ID);
//        return ((Activity)view).getIntent().getStringExtra(CancelHWContract.View.KEY_LINK_ID);
    }

    @ActivityScope
    @Provides
    @Named(CancelHWContract.View.KEY_TYPE)
    static Integer provideType(CancelHWContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(CancelHWContract.View.KEY_TYPE,0);
    }
}
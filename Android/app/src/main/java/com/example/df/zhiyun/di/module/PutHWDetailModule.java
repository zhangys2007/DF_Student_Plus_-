package com.example.df.zhiyun.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.mvp.contract.PutHWDetailContract;
import com.example.df.zhiyun.mvp.model.PutHWDetailModel;
import com.example.df.zhiyun.mvp.ui.adapter.PutHWDetailClassAdapter;
import com.example.df.zhiyun.mvp.ui.widget.GridDividerItemDecoration;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

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
public abstract class PutHWDetailModule {

    @Binds
    abstract PutHWDetailContract.Model bindPutHWModel(PutHWDetailModel model);

    @ActivityScope
    @Provides
    static KProgressHUD provideKP(PutHWDetailContract.View view) {
        Context context = (Context)view;
        return KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @ActivityScope
    @Provides
    static GridDividerItemDecoration provideItemDecoration(PutHWDetailContract.View view) {
        Context context = (Context)view;
        int divpix = ArmsUtils.dip2px(context,2);
        int c = ContextCompat.getColor(context, R.color.white);
        return new GridDividerItemDecoration(divpix,c);
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(PutHWDetailContract.View view) {
        Context context = (Context)view;
        RecyclerView.LayoutManager  manager = new GridLayoutManager(context, 4);
        return manager;
    }

    @ActivityScope
    @Provides
    static IView provideBaseIView(PutHWDetailContract.View view){
        return (IView)view;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<String> list){
        return new PutHWDetailClassAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<String> provideDatas() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    @Named(PutHWDetailContract.View.KEY_CLASS_NAME)
    static String providePaperName(PutHWDetailContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(),PutHWDetailContract.View.KEY_CLASS_NAME);
//        return ((Activity)view).getIntent().getStringExtra(PutHWDetailContract.View.KEY_CLASS_NAME);
    }

    @ActivityScope
    @Provides
    @Named(PutHWDetailContract.View.KEY_PAPER_ID)
    static String providePaperId(PutHWDetailContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(),PutHWDetailContract.View.KEY_PAPER_ID);
//        return ((Activity)view).getIntent().getStringExtra(PutHWDetailContract.View.KEY_PAPER_ID);
    }

    @ActivityScope
    @Provides
    @Named(PutHWDetailContract.View.KEY_SYSTEM_ID)
    static String provideSystemId(PutHWDetailContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(),PutHWDetailContract.View.KEY_SYSTEM_ID);
//        return ((Activity)view).getIntent().getStringExtra(PutHWDetailContract.View.KEY_SYSTEM_ID);
    }

    @ActivityScope
    @Provides
    @Named(PutHWDetailContract.View.KEY_LINK_ID)
    static String provideLinkId(PutHWDetailContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(),PutHWDetailContract.View.KEY_LINK_ID);
//        return ((Activity)view).getIntent().getStringExtra(PutHWDetailContract.View.KEY_LINK_ID);
    }

    @ActivityScope
    @Provides
    @Named(PutHWDetailContract.View.KEY_TYPE)
    static Integer provideType(PutHWDetailContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(PutHWDetailContract.View.KEY_TYPE,0);
    }

    @ActivityScope
    @Provides
    @Named(PutHWDetailContract.View.KEY_CLASS_ID)
    static String provideClassId(PutHWDetailContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(),PutHWDetailContract.View.KEY_CLASS_ID);
//        return ((Activity)view).getIntent().getStringExtra(PutHWDetailContract.View.KEY_CLASS_ID);
    }
}
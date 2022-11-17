package com.example.df.zhiyun.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.mvp.contract.HWClassSelContract;
import com.example.df.zhiyun.mvp.model.HWClassSelModel;
import com.example.df.zhiyun.educate.mvp.model.entity.BelongClass;
import com.example.df.zhiyun.mvp.ui.adapter.ClassSelAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.jess.arms.di.scope.ActivityScope;

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
 * Created by MVPArmsTemplate on 08/06/2019 16:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HWClassSelModule {

    @Binds
    abstract HWClassSelContract.Model bindClassSelModel(HWClassSelModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(HWClassSelContract.View view) {
        Context context = ((Activity)view);
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,1
                , ContextCompat.getColor(context, R.color.divider));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(HWClassSelContract.View view) {
        Context context = ((Activity)view);
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(context);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<BelongClass> list){
        return new ClassSelAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<BelongClass> provideDatas() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    @Named(HWClassSelContract.View.KEY_PAPER_ID)
    static String providePaperId(HWClassSelContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(),HWClassSelContract.View.KEY_PAPER_ID);
//        return ((Activity)view).getIntent().getStringExtra(HWClassSelContract.View.KEY_PAPER_ID);
    }

    @ActivityScope
    @Provides
    @Named(HWClassSelContract.View.KEY_SYSTEM_ID)
    static String provideSystemId(HWClassSelContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(),HWClassSelContract.View.KEY_SYSTEM_ID);
//        return ((Activity)view).getIntent().getStringExtra(HWClassSelContract.View.KEY_SYSTEM_ID);
    }

    @ActivityScope
    @Provides
    @Named(HWClassSelContract.View.KEY_LINK_ID)
    static String provideLinkId(HWClassSelContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(),HWClassSelContract.View.KEY_LINK_ID);
//        return ((Activity)view).getIntent().getStringExtra(HWClassSelContract.View.KEY_LINK_ID);
    }

    @ActivityScope
    @Provides
    @Named(HWClassSelContract.View.KEY_TYPE)
    static Integer provideType(HWClassSelContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(HWClassSelContract.View.KEY_TYPE,0);
    }
}
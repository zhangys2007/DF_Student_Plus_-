package com.example.df.zhiyun.plan.di.module;

import android.app.Activity;

import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.plan.mvp.contract.DesignContract;
import com.example.df.zhiyun.plan.mvp.model.DesignModel;

import javax.inject.Named;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/03/2019 22:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class DesignModule {

    @Binds
    abstract DesignContract.Model bindDesignModel(DesignModel model);

    @ActivityScope
    @Provides
    @Named("isClass")
    static Integer provideIsClass(DesignContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(DesignContract.View.KEY_CLASS,0);
    }

    @ActivityScope
    @Provides
    @Named("isCloud")
    static Integer provideIsCloud(DesignContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(DesignContract.View.KEY_CLOUD,0);
    }

    @ActivityScope
    @Provides
    @Named("parentId")
    static String provideParentId(DesignContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), DesignContract.View.KEY_PARENT_ID);
//        return ((Activity)view).getIntent().getStringExtra(DesignContract.View.KEY_PARENT_ID);
    }

    @ActivityScope
    @Provides
    @Named("title")
    static String provideTitle(DesignContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), DesignContract.View.KEY_TITLE);
//        return ((Activity)view).getIntent().getStringExtra(DesignContract.View.KEY_TITLE);
    }
}
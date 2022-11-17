package com.example.df.zhiyun.analy.di.module;

import android.app.Activity;

import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.analy.mvp.contract.AnalyDetailContract;
import com.example.df.zhiyun.analy.mvp.model.AnalyDetailModel;

import javax.inject.Named;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 14:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class AnalyDetailModule {

    @Binds
    abstract AnalyDetailContract.Model bindAnalyDetailModel(AnalyDetailModel model);

    @ActivityScope
    @Provides
    @Named(AnalyDetailContract.View.KEY_NAME)
    static String provideTitle(AnalyDetailContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(),AnalyDetailContract.View.KEY_NAME);
//        return ((Activity)view).getIntent().getStringExtra(AnalyDetailContract.View.KEY_NAME);
    }

    @ActivityScope
    @Provides
    @Named(AnalyDetailContract.View.KEY_URL)
    static String provideUrl(AnalyDetailContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(),AnalyDetailContract.View.KEY_URL);
//        return ((Activity)view).getIntent().getStringExtra(AnalyDetailContract.View.KEY_URL);
    }
}
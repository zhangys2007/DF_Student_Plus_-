package com.example.df.zhiyun.comment.di.module;

import android.app.Activity;

import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.comment.mvp.contract.CommentAnalyDetailContract;
import com.example.df.zhiyun.comment.mvp.model.CommentAnalyDetailModel;

import javax.inject.Named;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 09/06/2019 15:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class CommentAnalyDetailModule {

    @Binds
    abstract CommentAnalyDetailContract.Model bindCommentAnalyDetailModel(CommentAnalyDetailModel model);

    @ActivityScope
    @Provides
    @Named(CommentAnalyDetailContract.View.KEY_NAME)
    static String provideTitle(CommentAnalyDetailContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), CommentAnalyDetailContract.View.KEY_NAME);
//        return ((Activity)view).getIntent().getStringExtra(CommentAnalyDetailContract.View.KEY_NAME);
    }

    @ActivityScope
    @Provides
    @Named(CommentAnalyDetailContract.View.KEY_URL)
    static String provideUrl(CommentAnalyDetailContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), CommentAnalyDetailContract.View.KEY_URL);
//        return ((Activity)view).getIntent().getStringExtra(CommentAnalyDetailContract.View.KEY_URL);
    }

    @ActivityScope
    @Provides
    @Named(CommentAnalyDetailContract.View.KEY_ID)
    static String provideId(CommentAnalyDetailContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), CommentAnalyDetailContract.View.KEY_ID);
//        return ((Activity)view).getIntent().getStringExtra(CommentAnalyDetailContract.View.KEY_ID);
    }

    @ActivityScope
    @Provides
    @Named(CommentAnalyDetailContract.View.KEY_HOMEWORK_ID)
    static String provideHomeworkId(CommentAnalyDetailContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), CommentAnalyDetailContract.View.KEY_HOMEWORK_ID);
//        return ((Activity)view).getIntent().getStringExtra(CommentAnalyDetailContract.View.KEY_HOMEWORK_ID);
    }

    @ActivityScope
    @Provides
    @Named(CommentAnalyDetailContract.View.KEY_CLASS_ID)
    static String provideClassId(CommentAnalyDetailContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), CommentAnalyDetailContract.View.KEY_CLASS_ID);
//        return ((Activity)view).getIntent().getStringExtra(CommentAnalyDetailContract.View.KEY_CLASS_ID);
    }

    @ActivityScope
    @Provides
    @Named(CommentAnalyDetailContract.View.KEY_CLASS_NAME)
    static String provideClassN(CommentAnalyDetailContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), CommentAnalyDetailContract.View.KEY_CLASS_NAME);
//        return ((Activity)view).getIntent().getStringExtra(CommentAnalyDetailContract.View.KEY_CLASS_NAME);
    }

    @ActivityScope
    @Provides
    @Named(CommentAnalyDetailContract.View.KEY_SUB_NAME)
    static String provideSubName(CommentAnalyDetailContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), CommentAnalyDetailContract.View.KEY_SUB_NAME);
//        return ((Activity)view).getIntent().getStringExtra(CommentAnalyDetailContract.View.KEY_SUB_NAME);
    }
}
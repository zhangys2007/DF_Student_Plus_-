package com.example.df.zhiyun.di.module;

import android.support.v4.app.Fragment;

import com.example.df.zhiyun.mvp.contract.WholeCompearContract;
import com.example.df.zhiyun.mvp.model.WholeCompearModel;
import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Named;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 13:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class WholeCompearModule {

    @Binds
    abstract WholeCompearContract.Model bindMyModel(WholeCompearModel model);

    @FragmentScope
    @Provides
    @Named(WholeCompearContract.View.KEY_FZ)
    static Integer provFzId(WholeCompearContract.View view) {
        return ((Fragment)view).getArguments().getInt(WholeCompearContract.View.KEY_FZ,0);
    }

    @FragmentScope
    @Provides
    @Named(WholeCompearContract.View.KEY_TYPE)
    static Integer provType(WholeCompearContract.View view) {
        return ((Fragment)view).getArguments().getInt(WholeCompearContract.View.KEY_TYPE,0);
    }

    @FragmentScope
    @Provides
    @Named(WholeCompearContract.View.KEY_GRADEID)
    static Integer provGrade(WholeCompearContract.View view) {
        return ((Fragment)view).getArguments().getInt(WholeCompearContract.View.KEY_GRADEID,0);
    }

    @FragmentScope
    @Provides
    @Named(WholeCompearContract.View.KEY_SCHOOLID)
    static Integer provSchoolId(WholeCompearContract.View view) {
        return ((Fragment)view).getArguments().getInt(WholeCompearContract.View.KEY_SCHOOLID,0);
    }

    @FragmentScope
    @Provides
    @Named(WholeCompearContract.View.KEY_HOMEWORK_ID)
    static String provHomeworkId(WholeCompearContract.View view) {
        return ((Fragment)view).getArguments().getString(WholeCompearContract.View.KEY_HOMEWORK_ID,"0");
    }

    @FragmentScope
    @Provides
    @Named(WholeCompearContract.View.KEY_SUBJID)
    static Integer provSubjId(WholeCompearContract.View view) {
        return ((Fragment)view).getArguments().getInt(WholeCompearContract.View.KEY_SUBJID,0);
    }
}
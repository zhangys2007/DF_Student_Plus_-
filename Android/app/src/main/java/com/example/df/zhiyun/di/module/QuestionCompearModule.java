package com.example.df.zhiyun.di.module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.contract.QuestionCompearContract;
import com.example.df.zhiyun.mvp.model.QuestionCompearModel;
import com.example.df.zhiyun.mvp.model.adapterEntity.QuestionCompearMultipleItem;
import com.example.df.zhiyun.mvp.ui.adapter.QuestionCompearAdapter;
import com.example.df.zhiyun.mvp.ui.widget.GridDividerItemDecoration;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.ArmsUtils;

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
 * Created by MVPArmsTemplate on 07/18/2019 13:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class QuestionCompearModule {

    @Binds
    abstract QuestionCompearContract.Model bindMyModel(QuestionCompearModel model);

    @FragmentScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(QuestionCompearContract.View view) {
        Context context = ((Fragment)view).getContext();
        return new GridDividerItemDecoration(ArmsUtils.dip2px(context,1)
                , ContextCompat.getColor(context, R.color.divider));
    }


    @Provides
    @FragmentScope
    static BaseMultiItemQuickAdapter provideAdapter(){
        List<QuestionCompearMultipleItem> list = new ArrayList<>();
        return new QuestionCompearAdapter(list);
    }

    @FragmentScope
    @Provides
    @Named(QuestionCompearContract.View.KEY_FZ)
    static Integer provFzId(QuestionCompearContract.View view) {
        return ((Fragment)view).getArguments().getInt(QuestionCompearContract.View.KEY_FZ,0);
    }

    @FragmentScope
    @Provides
    @Named(QuestionCompearContract.View.KEY_TYPE)
    static Integer provType(QuestionCompearContract.View view) {
        return ((Fragment)view).getArguments().getInt(QuestionCompearContract.View.KEY_TYPE,0);
    }

    @FragmentScope
    @Provides
    @Named(QuestionCompearContract.View.KEY_GRADEID)
    static Integer provGrade(QuestionCompearContract.View view) {
        return ((Fragment)view).getArguments().getInt(QuestionCompearContract.View.KEY_GRADEID,0);
    }

    @FragmentScope
    @Provides
    @Named(QuestionCompearContract.View.KEY_SCHOOLID)
    static Integer provSchoolId(QuestionCompearContract.View view) {
        return ((Fragment)view).getArguments().getInt(QuestionCompearContract.View.KEY_SCHOOLID,0);
    }

    @FragmentScope
    @Provides
    @Named(QuestionCompearContract.View.KEY_HOMEWORK_ID)
    static String provHomeworkId(QuestionCompearContract.View view) {
        return ((Fragment)view).getArguments().getString(QuestionCompearContract.View.KEY_HOMEWORK_ID,"0");
    }

    @FragmentScope
    @Provides
    @Named(QuestionCompearContract.View.KEY_SUBJID)
    static Integer provSubjId(QuestionCompearContract.View view) {
        return ((Fragment)view).getArguments().getInt(QuestionCompearContract.View.KEY_SUBJID,0);
    }
}
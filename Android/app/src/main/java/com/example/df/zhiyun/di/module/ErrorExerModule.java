package com.example.df.zhiyun.di.module;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.jess.arms.di.scope.FragmentScope;
import com.example.df.zhiyun.mvp.contract.ErrorExerContract;
import com.example.df.zhiyun.mvp.model.ErrorExerModel;
import com.example.df.zhiyun.mvp.model.adapterEntity.QuestionMultipleItem;
import com.example.df.zhiyun.mvp.ui.adapter.QuestionAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.R;

import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.jess.arms.utils.ArmsUtils;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 13:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class ErrorExerModule {

    @Binds
    abstract ErrorExerContract.Model bindErrorExerModel(ErrorExerModel model);

    @FragmentScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(ErrorExerContract.View view) {
        return new RecycleViewDivider(view.getPageContext(), LinearLayoutManager.VERTICAL,
                ArmsUtils.dip2px(view.getPageContext(),1)
                , ContextCompat.getColor(view.getPageContext(), R.color.divider));
    }

    @FragmentScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(ErrorExerContract.View view) {
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(view.getPageContext());
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @FragmentScope
    @Provides
    static BaseMultiItemQuickAdapter provideAdapter(List<QuestionMultipleItem> list){
        return new QuestionAdapter(list);
    }

    @FragmentScope
    @Provides
    static List<QuestionMultipleItem> provideHomeworkList() {
        return new ArrayList<>();
    }
}
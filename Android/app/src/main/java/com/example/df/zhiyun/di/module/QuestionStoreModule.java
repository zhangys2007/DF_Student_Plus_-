package com.example.df.zhiyun.di.module;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.mvp.contract.QuestionStoreContract;
import com.example.df.zhiyun.mvp.model.QuestionStoreModel;
import com.example.df.zhiyun.mvp.model.adapterEntity.QuestionMultipleItem;
import com.example.df.zhiyun.mvp.ui.adapter.QuestionAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.R;

import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/26/2019 15:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class QuestionStoreModule {

    @Binds
    abstract QuestionStoreContract.Model bindQuestionStoreModel(QuestionStoreModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(QuestionStoreContract.View view) {
        return new RecycleViewDivider(view.getPageContext(), LinearLayoutManager.VERTICAL,1
                , ContextCompat.getColor(view.getPageContext(), R.color.divider));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(QuestionStoreContract.View view) {
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(view.getPageContext());
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseMultiItemQuickAdapter provideAdapter(List<QuestionMultipleItem> list){
        return new QuestionAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<QuestionMultipleItem> provideHomeworkList() {
        return new ArrayList<>();
    }
}
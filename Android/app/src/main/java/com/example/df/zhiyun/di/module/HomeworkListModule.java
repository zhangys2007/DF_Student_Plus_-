package com.example.df.zhiyun.di.module;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.mvp.contract.HomeworkListContract;
import com.example.df.zhiyun.mvp.model.HomeworkListModel;
import com.example.df.zhiyun.mvp.model.entity.Subject;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.mvp.ui.adapter.SubjectItemAdapter;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/20/2019 19:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HomeworkListModule {

    @Binds
    abstract HomeworkListContract.Model bindHomeworkListModel(HomeworkListModel model);

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(HomeworkListContract.View view) {
        RecyclerView.LayoutManager  manager = new GridLayoutManager(view.getPageContext(), 4);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<Subject> list){
        return new SubjectItemAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<Subject> provideData() {
        return new ArrayList<>();
    }
}
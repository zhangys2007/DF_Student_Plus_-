package com.example.df.zhiyun.di.module;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.jess.arms.di.scope.ActivityScope;
import com.example.df.zhiyun.mvp.contract.SearchContract;
import com.example.df.zhiyun.mvp.model.SearchModel;
import com.example.df.zhiyun.mvp.model.adapterEntity.SearchMultipleItem;
import com.example.df.zhiyun.mvp.ui.adapter.SearchAdapter;

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
 * Created by MVPArmsTemplate on 08/20/2019 16:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class SearchModule {

    @Binds
    abstract SearchContract.Model bindSearchModel(SearchModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(SearchContract.View view) {
        Activity activity = (Activity)view;
        return new RecycleViewDivider(activity, LinearLayoutManager.VERTICAL,1
                , ContextCompat.getColor(activity, R.color.divider));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(SearchContract.View view) {
        Activity activity = (Activity)view;
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(activity);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseMultiItemQuickAdapter provideAdapter(List<SearchMultipleItem> list){
        return new SearchAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<SearchMultipleItem> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static Integer providType(SearchContract.View view) {
        return ((Activity)view).getIntent().getIntExtra(SearchContract.View.KEY_TYPE,0);
    }

    @ActivityScope
    @Provides
    static String providSubjectId(SearchContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), SearchContract.View.KEY_SUBJECTID);
//        return ((Activity)view).getIntent().getS(SearchContract.View.KEY_SUBJECTID,0);
    }
}
package com.example.df.zhiyun.di.module;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.mvp.contract.CategoryContract;
import com.example.df.zhiyun.mvp.contract.CategoryTchContract;
import com.example.df.zhiyun.mvp.model.CategoryTchModel;
import com.example.df.zhiyun.mvp.ui.adapter.CategoryTchAdapter;
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
 * Created by MVPArmsTemplate on 08/18/2019 00:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class CategoryTchModule {

    @Binds
    abstract CategoryTchContract.Model bindCategoryModel(CategoryTchModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(CategoryTchContract.View view) {
        Activity activity = (Activity)view;
        return new RecycleViewDivider(activity, LinearLayoutManager.VERTICAL,1
                , ContextCompat.getColor(activity, R.color.divider));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(CategoryTchContract.View view) {
        Activity activity = (Activity)view;
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(activity);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseMultiItemQuickAdapter provideAdapter(List<MultiItemEntity> list){
        return new CategoryTchAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<MultiItemEntity> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    @Named("subjId")
    static String provideId(CategoryTchContract.View view) {
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(), CategoryContract.View.KEY_ID);
//        return ((Activity)view).getIntent().getStringExtra(CategoryContract.View.KEY_ID);
    }
}
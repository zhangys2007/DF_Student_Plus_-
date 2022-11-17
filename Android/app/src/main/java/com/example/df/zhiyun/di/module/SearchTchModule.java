package com.example.df.zhiyun.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.mvp.contract.SearchTchContract;
import com.example.df.zhiyun.mvp.model.SearchTchModel;
import com.example.df.zhiyun.mvp.model.entity.HomeworkArrange;
import com.example.df.zhiyun.mvp.ui.adapter.HomeworkPutAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


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
public abstract class SearchTchModule {

    @Binds
    abstract SearchTchContract.Model bindSearchModel(SearchTchModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(SearchTchContract.View view) {
        Context context = (Context)view;
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,
                ArmsUtils.dip2px(context,5)
                , ContextCompat.getColor(context, R.color.bg_grey));
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(SearchTchContract.View view) {
        Context context = (Context)view;
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(context);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<HomeworkArrange> list){
        return new HomeworkPutAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<HomeworkArrange> provideHomeworkList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static String provideClassId(SearchTchContract.View view){
        return StringCocatUtil.getStringExtraDefault(((Activity)view).getIntent(),SearchTchContract.View.KEY_CLASSID);
//        return ((Activity)view).getIntent().getStringExtra(SearchTchContract.View.KEY_CLASSID);
    }
}
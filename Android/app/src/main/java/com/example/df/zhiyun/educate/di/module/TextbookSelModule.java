package com.example.df.zhiyun.educate.di.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.educate.mvp.contract.TextbookSelContract;
import com.example.df.zhiyun.educate.mvp.model.TextbookSelModel;
import com.example.df.zhiyun.educate.mvp.ui.adapter.TextbookSelAdapter;
import com.example.df.zhiyun.mvp.ui.widget.GridDividerItemDecoration;
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
 * Created by MVPArmsTemplate on 05/11/2020 18:08
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class TextbookSelModule {

    @Binds
    abstract TextbookSelContract.Model bindTextbookSelModel(TextbookSelModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideDecoration(TextbookSelContract.View view) {
        Context context = (Activity)view;
        return new GridDividerItemDecoration(ArmsUtils.dip2px(context,15)
                , ContextCompat.getColor(context, R.color.white));
    }

    @Provides
    @ActivityScope
    static RecyclerView.LayoutManager provideLayoutManager(TextbookSelContract.View view) {
        RecyclerView.LayoutManager  manager = new GridLayoutManager((Activity)view, 3);
        return manager;
    }


    @Provides
    @ActivityScope
    static BaseQuickAdapter provideAdapter(){
        List<String> list = new ArrayList<>();
        for(int i=0;i<20;i++){
            list.add("人教版："+i);
        }
        return new TextbookSelAdapter(list);
    }
}
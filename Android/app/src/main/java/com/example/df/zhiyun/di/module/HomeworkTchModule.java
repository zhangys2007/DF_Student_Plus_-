package com.example.df.zhiyun.di.module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.mvp.contract.HomeworkTchContract;
import com.example.df.zhiyun.mvp.model.HomeworkTchModel;
import com.example.df.zhiyun.mvp.ui.adapter.HomeworkPutAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.R;

import com.example.df.zhiyun.mvp.model.entity.HomeworkArrange;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/06/2019 13:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HomeworkTchModule {

    @Binds
    abstract HomeworkTchContract.Model bindHomeworkTchModel(HomeworkTchModel model);

    @FragmentScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(HomeworkTchContract.View view) {
        Context context = ((Fragment)view).getContext();
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,
                ArmsUtils.dip2px(context,5)
                , ContextCompat.getColor(context, R.color.bg_grey));
    }

//    @FragmentScope
//    @Provides
//    static RecyclerView.LayoutManager provideLayoutManager(HomeworkTchContract.View view) {
//        Context context = ((Fragment)view).getContext();
//        RecyclerView.LayoutManager  manager = new LinearLayoutManager(context);
//        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
//        return manager;
//    }

    @FragmentScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<HomeworkArrange> list){
        return new HomeworkPutAdapter(list);
    }

    @FragmentScope
    @Provides
    static List<HomeworkArrange> provideHomeworkList() {
        return new ArrayList<>();
    }
}
package com.example.df.zhiyun.educate.di.module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.educate.mvp.ui.adapter.HWAdapter;
import com.example.df.zhiyun.educate.mvp.ui.adapter.PaperAdapter;
import com.example.df.zhiyun.main.mvp.model.entity.TodoItem;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.educate.mvp.contract.EducateContract;
import com.example.df.zhiyun.educate.mvp.model.EducateModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/06/2020 09:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class EducateModule {

    @Binds
    abstract EducateContract.Model bindEducateModel(EducateModel model);

    @FragmentScope
    @Named(EducateContract.View.KEY_HW)
    @Provides
    static RecyclerView.ItemDecoration provideDecoration(EducateContract.View view) {
        Context context = ((Fragment)view).getContext();
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,10
                , ContextCompat.getColor(context, R.color.white));
    }

    @FragmentScope
    @Named(EducateContract.View.KEY_HW)
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(EducateContract.View view) {
        Context context = ((Fragment)view).getContext();
        LinearLayoutManager  manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @FragmentScope
    @Named(EducateContract.View.KEY_HW)
    @Provides
    static BaseQuickAdapter provideAdapter(){
        List<TodoItem> list = new ArrayList<>();
        for(int i=0;i<3;i++){
            TodoItem item = new TodoItem();
            list.add(item);
            item.setApprovedNumber(3);
            item.setClassName("1班");
            item.setHomeworkName("寒假作业");
            item.setPaidNumber(2);
            item.setStudentCount(5);
            item.setUnApprovedNumber(1);
            item.setUnpaidNumber(2);
            item.setCreateTime(1588828382);
        }
        return new HWAdapter(list);
    }


    @FragmentScope
    @Named(EducateContract.View.KEY_PAPER)
    @Provides
    static RecyclerView.ItemDecoration provideDecorationPaper(EducateContract.View view) {
        Context context = ((Fragment)view).getContext();
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,10
                , ContextCompat.getColor(context, R.color.white));
    }

    @FragmentScope
    @Named(EducateContract.View.KEY_PAPER)
    @Provides
    static RecyclerView.LayoutManager provideLayoutManagerPaper(EducateContract.View view) {
        Context context = ((Fragment)view).getContext();
        LinearLayoutManager  manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @FragmentScope
    @Named(EducateContract.View.KEY_PAPER)
    @Provides
    static BaseQuickAdapter provideAdapterPaper(){
        List<TodoItem> list = new ArrayList<>();
        for(int i=0;i<3;i++){
            TodoItem item = new TodoItem();
            list.add(item);
            item.setApprovedNumber(3);
            item.setClassName("1班");
            item.setHomeworkName("寒假作业");
            item.setPaidNumber(2);
            item.setStudentCount(5);
            item.setUnApprovedNumber(1);
            item.setUnpaidNumber(2);
            item.setCreateTime(1588828382);
        }
        return new PaperAdapter(list);
    }
}
package com.example.df.zhiyun.main.di.module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.main.mvp.model.entity.FocusStd;
import com.example.df.zhiyun.main.mvp.model.entity.PushData;
import com.example.df.zhiyun.main.mvp.model.entity.TodoItem;
import com.example.df.zhiyun.main.mvp.ui.adapter.FocusStdAdapter;
import com.example.df.zhiyun.main.mvp.ui.adapter.PushAdapter;
import com.example.df.zhiyun.main.mvp.ui.adapter.TodoItemAdapter;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.jess.arms.di.scope.FragmentScope;
import com.example.df.zhiyun.main.mvp.contract.MainTchFragmentContract;
import com.example.df.zhiyun.main.mvp.model.MainTchFragmentModel;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.R;
import com.kaopiz.kprogresshud.KProgressHUD;

import javax.inject.Named;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/06/2019 10:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class MainTchFragmentModule {

    @Binds
    abstract MainTchFragmentContract.Model bindMainTchFragmentModel(MainTchFragmentModel model);


    @FragmentScope
    @Provides
    static KProgressHUD provideKP(MainTchFragmentContract.View view) {
        Context activity = ((Fragment)view).getContext();
        return KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }


    @FragmentScope
    @Named(MainTchFragmentContract.View.KEY_PUSH)
    @Provides
    static RecyclerView.LayoutManager provideLayoutManagerPush(MainTchFragmentContract.View view) {
        LinearLayoutManager  manager = new LinearLayoutManager(view.getPageContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }


    @FragmentScope
    @Named(MainTchFragmentContract.View.KEY_PUSH)
    @Provides
    static BaseQuickAdapter providePushAdapter(List<PushData> list){
        return new PushAdapter(list);
    }

    @FragmentScope
    @Provides
    static List<PushData> providePushList() {
        List<PushData> list = new ArrayList<>();
        return list;
    }

    @FragmentScope
    @Named(MainTchFragmentContract.View.KEY_FOCUS)
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(MainTchFragmentContract.View view) {
        LinearLayoutManager  manager = new LinearLayoutManager(view.getPageContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }


    @FragmentScope
    @Named(MainTchFragmentContract.View.KEY_FOCUS)
    @Provides
    static BaseQuickAdapter provideAdapter(List<FocusStd> list){
        return new FocusStdAdapter(list);
    }

    @FragmentScope
    @Provides
    static List<FocusStd> provideList() {
        List<FocusStd> list = new ArrayList<>();
        return list;
    }

    @FragmentScope
    @Provides
    static RecyclerView.ItemDecoration provideDecoration(MainTchFragmentContract.View view) {
        Context context = view.getPageContext();
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,10
                , ContextCompat.getColor(context, R.color.white));
    }

    @FragmentScope
    @Named(MainTchFragmentContract.View.KEY_TOD)
    @Provides
    static RecyclerView.LayoutManager provideTodoLayoutManager(MainTchFragmentContract.View view) {
        LinearLayoutManager  manager = new LinearLayoutManager(view.getPageContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @FragmentScope
    @Named(MainTchFragmentContract.View.KEY_TOD)
    @Provides
    static BaseQuickAdapter provideTodoAdapter(List<TodoItem> list){
        return new TodoItemAdapter(list);
    }

    @FragmentScope
    @Provides
    static List<TodoItem> provideListTodo() {
        List<TodoItem> list = new ArrayList<>();
        return list;
    }
}
package com.example.df.zhiyun.plan.di.module;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.ui.widget.RecycleViewDivider;
import com.example.df.zhiyun.plan.mvp.model.entity.Template;
import com.example.df.zhiyun.plan.mvp.ui.adapter.TemplateAdapter;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.plan.mvp.contract.PlanCloudContract;
import com.example.df.zhiyun.plan.mvp.model.PlanCloudModel;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/07/2020 14:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class PlanCloudModule {

    @Binds
    abstract PlanCloudContract.Model bindPlanCloudModel(PlanCloudModel model);

    @ActivityScope
    @Provides
    static RecyclerView.ItemDecoration provideItemDecoration(PlanCloudContract.View view) {
        Context context = (Context)view;
        return new RecycleViewDivider(context, LinearLayoutManager.VERTICAL,10
                , ContextCompat.getColor(context, android.R.color.transparent));
    }


    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(PlanCloudContract.View view) {
        Context context = (Context)view;
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(context);
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    @ActivityScope
    @Provides
    static BaseQuickAdapter provideAdapter(List<Template> list){
        return new TemplateAdapter(list);
    }

    @ActivityScope
    @Provides
    static List<Template> provideDatas() {
        List<Template> list = new ArrayList<>();
        for(int i=0;i<6;i++){
            Template item = new Template();
            list.add(item);

            item.setCover("");
            item.setName("教案1");
            item.setPress("上海教育出版社");
            item.setTime("2020-05-07");
        }
        return list;
    }

    @ActivityScope
    @Provides
    static KProgressHUD provideKP(PlanCloudContract.View view) {
        return KProgressHUD.create((Context) view)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }
}
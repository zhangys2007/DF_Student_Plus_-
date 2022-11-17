package com.example.df.zhiyun.di.module;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.mvp.contract.HomeworkContract;
import com.example.df.zhiyun.mvp.model.HomeworkModel;
import com.example.df.zhiyun.mvp.model.adapterEntity.MainHomeworkMultipleItem;
import com.example.df.zhiyun.mvp.ui.adapter.MainHomeworkAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.example.df.zhiyun.R;

import com.example.df.zhiyun.mvp.ui.widget.MainHWRecycleViewGridDivider;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 10:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class HomeworkModule {

    @Binds
    abstract HomeworkContract.Model bindHomeworkModel(HomeworkModel model);

    @FragmentScope
    @Provides
    static MainHWRecycleViewGridDivider provideDecoration(HomeworkContract.View view) {
        Context context = view.getPageContext();
        int divpix = ArmsUtils.dip2px(context,5);
        int c = ContextCompat.getColor(context, R.color.white);
        return new MainHWRecycleViewGridDivider(divpix,c);
    }

//    @FragmentScope
//    @Provides
//    static RecyclerView.LayoutManager provideLayoutManager(HomeworkContract.View view) {
//        RecyclerView.LayoutManager  manager = new GridLayoutManager(view.getPageContext(), 2);
//        return manager;
//    }

    @FragmentScope
    @Provides
    static BaseQuickAdapter.SpanSizeLookup provideSpanSizeLookup() {
        return new BaseQuickAdapter.SpanSizeLookup(){

            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
                if(position == 0){
                    return 2;
                }else{
                    return 1;
                }
            }
        };
    }

    @FragmentScope
    @Provides
    static BaseMultiItemQuickAdapter provideAdapter(List<MainHomeworkMultipleItem> list){
        return new MainHomeworkAdapter(list);
    }

    @FragmentScope
    @Provides
    static List<MainHomeworkMultipleItem> provideHomeworkList() {
        return new ArrayList<>();
    }
}
package com.example.df.zhiyun.preview.di.module;

import com.example.df.zhiyun.preview.mvp.contract.PrevHWiptContract;
import com.example.df.zhiyun.preview.mvp.model.PrevHWiptModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 17:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class PrevHWiptModule {

    @Binds
    abstract PrevHWiptContract.Model bindHWiptModel(PrevHWiptModel model);

//    @FragmentScope
//    @Provides
//    static RecyclerView.ItemDecoration provideItemDecoration(HWiptContract.View view) {
//        return new RecycleViewDivider(((Fragment)view).getContext(), LinearLayoutManager.VERTICAL, ArmsUtils.dip2px(((Fragment)view).getContext(),10)
//                , ContextCompat.getColor(((Fragment)view).getContext(), android.R.color.transparent));
//    }
//
//    @FragmentScope
//    @Provides
//    static RecyclerView.LayoutManager provideLayoutManager(HWiptContract.View view) {
//        RecyclerView.LayoutManager  manager = new LinearLayoutManager(((Fragment)view).getContext());
//        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
//        return manager;
//    }
//
//    @FragmentScope
//    @Provides
//    static BaseQuickAdapter provideAdapter(List<QuestionOption> list){
//        return new HWinputAdapter(list);
//    }
//
//    @FragmentScope
//    @Provides
//    static List<QuestionOption> provideList() {
//        return new ArrayList<>();
//    }
}
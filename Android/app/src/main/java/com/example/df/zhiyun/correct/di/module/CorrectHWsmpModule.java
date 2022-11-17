package com.example.df.zhiyun.correct.di.module;

import com.example.df.zhiyun.correct.mvp.contract.CorrectHWsmpContract;
import com.example.df.zhiyun.correct.mvp.model.CorrectHWsmpModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/13/2019 11:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class CorrectHWsmpModule {

    @Binds
    abstract CorrectHWsmpContract.Model bindHWsmpModel(CorrectHWsmpModel model);

//    @FragmentScope
//    @Provides
//    static RecyclerView.ItemDecoration provideItemDecoration(HWsmpContract.View view) {
//        return new RecycleViewDivider(((Fragment)view).getContext(), LinearLayoutManager.VERTICAL, ArmsUtils.dip2px(((Fragment)view).getContext(),10)
//                , ContextCompat.getColor(((Fragment)view).getContext(), android.R.color.transparent));
//    }
//
//    @FragmentScope
//    @Provides
//    static RecyclerView.LayoutManager provideLayoutManager(HWsmpContract.View view) {
//        RecyclerView.LayoutManager  manager = new LinearLayoutManager(((Fragment)view).getContext());
//        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
//        return manager;
//    }
//
//    @FragmentScope
//    @Provides
//    static BaseQuickAdapter provideAdapter(List<QuestionOption> list){
//        return new HWsmpAdapter(list);
//    }
//
//    @FragmentScope
//    @Provides
//    static List<QuestionOption> provideList() {
//        return new ArrayList<>();
//    }
}
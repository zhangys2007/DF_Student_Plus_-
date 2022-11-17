package com.example.df.zhiyun.preview.di.module;

import com.example.df.zhiyun.preview.mvp.contract.PrevHWcomContract;
import com.example.df.zhiyun.preview.mvp.model.PrevHWcomModel;

import dagger.Binds;
import dagger.Module;


/**
 * ================================================
 * Description: 作文
 * <p>
 * Created by MVPArmsTemplate on 08/16/2019 11:10
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
public abstract class PrevHWcomModule {

    @Binds
    abstract PrevHWcomContract.Model bindHWcomModel(PrevHWcomModel model);

//    @FragmentScope
//    @Provides
//    static RecyclerView.ItemDecoration provideItemDecoration(HWcomContract.View view) {
//        return new RecycleViewDivider(((Fragment)view).getContext(), LinearLayoutManager.VERTICAL, ArmsUtils.dip2px(((Fragment)view).getContext(),10)
//                , ContextCompat.getColor(((Fragment)view).getContext(), android.R.color.transparent));
//    }
//
//    @FragmentScope
//    @Provides
//    static RecyclerView.LayoutManager provideLayoutManager(HWcomContract.View view) {
//        RecyclerView.LayoutManager  manager = new GridLayoutManager(((Fragment)view).getContext(), 3);
//        return manager;
//    }
//
//    @FragmentScope
//    @Provides
//    static BaseQuickAdapter provideAdapter(List<String> list){
//        return new SquareImageAdapter(list);
//    }
//
//    @FragmentScope
//    @Provides
//    static List<String> provideList() {
//        return new ArrayList<>();
//    }
}
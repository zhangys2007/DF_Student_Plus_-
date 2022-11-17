package com.example.df.zhiyun.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.mvp.contract.CategoryContract;
import com.example.df.zhiyun.mvp.contract.CategoryTchContract;
import com.example.df.zhiyun.mvp.model.adapterEntity.CategoryMainMultipleItem;
import com.example.df.zhiyun.mvp.model.adapterEntity.CategorySubMultipleItem;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.CategoryMain;
import com.example.df.zhiyun.mvp.model.entity.CategoryNode;
import com.example.df.zhiyun.mvp.model.entity.SyncPractice;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/18/2019 00:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class CategoryTchPresenter extends BasePresenter<CategoryTchContract.Model, CategoryTchContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

//    @Inject
//    @Named("subjId")
//    String subjName;
    @Inject
    BaseMultiItemQuickAdapter mAdapter;

    private String mBookVersionId = "";
    private String mObligatoryId = "";

    public void setBookVersionId(String id){
        mBookVersionId = id;
    }

    public void setObligatoryId(String id){
        mObligatoryId = id;
    }

    @Inject
    public CategoryTchPresenter(CategoryTchContract.Model model, CategoryTchContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
//        requestData(true);
    }

    public void requestData(boolean pullToRefresh) {
        mModel.getCategory(mBookVersionId,mObligatoryId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    if (pullToRefresh)
                        mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (pullToRefresh)
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<SyncPractice>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<SyncPractice> response) {
                        if(response.isSuccess()){
                            proccessData(response.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    private void proccessData(SyncPractice response){
        if(response == null || response.getList()== null || response.getList().size() == 0){
            return;
        }

        mRootView.bindVersionInfo(response);
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        List<CategoryMain> list = response.getList();
        for (int i=0;i<list.size();i++) {
            CategoryMainMultipleItem lv0 = new CategoryMainMultipleItem(list.get(i));

            List<CategoryNode> listNode = list.get(i).getList();
            if(listNode != null && listNode.size() >0){
                for (int j = 0; j < listNode.size(); j++) {
                    CategorySubMultipleItem lv1 = new CategorySubMultipleItem(listNode.get(j));
                    if(j == listNode.size()-1){
                        lv1.setLast(true);
                    }
                    lv0.addSubItem(lv1);
                }
            }

            res.add(lv0);
        }

        mAdapter.setNewData(res);
    }

}

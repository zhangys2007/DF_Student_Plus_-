package com.example.df.zhiyun.organize.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.mvp.ui.widget.PaginatorHelper;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;
import com.example.df.zhiyun.organize.mvp.contract.GradeItemContract;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import com.example.df.zhiyun.mvp.model.entity.GradeItem;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;
import javax.inject.Named;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 14:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class GradeItemPresenter extends BasePresenter<GradeItemContract.Model, GradeItemContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    BaseQuickAdapter mAdapter;
//    private PaginatorHelper paginatorHelper = new PaginatorHelper();
    @Inject
    @Named("id")
    String schoolId;


    @Inject
    public GradeItemPresenter(GradeItemContract.Model model, GradeItemContract.View rootView) {
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
        mModel.gradeList(schoolId)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<GradeItem>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<GradeItem>> response) {
                        if(response.isSuccess()){
                            List<GradeItem> tempDatas = response.getData();
                            PaginatorHelper.onAllDataArrive(mAdapter,tempDatas);
//                            paginatorHelper.onDataArrive(mAdapter,pullToRefresh,tempDatas,null);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mAdapter.loadMoreFail();
                    }
                });
    }
}

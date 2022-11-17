package com.example.df.zhiyun.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.SelPaperItem;
import com.example.df.zhiyun.preview.mvp.ui.activity.PreviewHomeworkActivity;
import com.example.df.zhiyun.mvp.ui.widget.PaginatorHelper;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.example.df.zhiyun.mvp.contract.SelPaperListContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 10:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SelPaperListPresenter extends BasePresenter<SelPaperListContract.Model, SelPaperListContract.View> {
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

    private PaginatorHelper paginatorHelper = new PaginatorHelper();
    private int subjectId = Api.SUBJECT_CHINESE;


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
//        requestData(true);
    }

    public void changeSubject(int index){
        switch (index){
            case 0:
                this.subjectId = Api.SUBJECT_CHINESE;
                break;
            case 1:
                this.subjectId = Api.SUBJECT_MATH;
                break;
            case 2:
                this.subjectId = Api.SUBJECT_ENGLISH;
                break;
        }
    }

    public void requestData(boolean pullToRefresh) {
        mModel.selectedPaperList(paginatorHelper.getPageIndex(pullToRefresh),subjectId)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<SelPaperItem>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<SelPaperItem>> response) {
                        if(response.isSuccess()){
                            List<SelPaperItem> tempDatas = response.getData();
                            paginatorHelper.onDataArrive(mAdapter,pullToRefresh,tempDatas,null);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mAdapter.loadMoreFail();
                    }
                });
    }





    @Inject
    public SelPaperListPresenter(SelPaperListContract.Model model, SelPaperListContract.View rootView) {
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

    //点击了预览
    public void clickPreview(SelPaperItem item) {
        PreviewHomeworkActivity.launchActivity((Context)mRootView,item.getUuid(),
                Api.STUDUNTEN_ANSWER_TYPE_SEL,subjectId);
    }
}

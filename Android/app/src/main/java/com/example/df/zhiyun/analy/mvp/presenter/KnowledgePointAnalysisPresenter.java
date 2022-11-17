package com.example.df.zhiyun.analy.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.analy.mvp.contract.KnowledgePointAnalysisContract;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.PointAnalysis;
import com.example.df.zhiyun.mvp.ui.widget.PaginatorHelper;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

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
 * Created by MVPArmsTemplate on 08/07/2019 15:28
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class KnowledgePointAnalysisPresenter extends BasePresenter<KnowledgePointAnalysisContract.Model, KnowledgePointAnalysisContract.View> {
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
    @Named(KnowledgePointAnalysisContract.View.KEY_CREATETIME)
    String mCreateTIme;

    @Inject
    @Named(KnowledgePointAnalysisContract.View.KEY_KNOWLEDGEID)
    String mKnowledgeId;

    @Inject
    @Named(KnowledgePointAnalysisContract.View.KEY_CLASSID)
    String mClassId;

    @Inject
    @Named(KnowledgePointAnalysisContract.View.KEY_SUBJECTID)
    Integer mSubjectId;

    @Inject
    public KnowledgePointAnalysisPresenter(KnowledgePointAnalysisContract.Model model, KnowledgePointAnalysisContract.View rootView) {
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
        mModel.pointAnalysis(mKnowledgeId,mCreateTIme,mClassId,mSubjectId)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<PointAnalysis>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<PointAnalysis>> response) {
                        if(response.isSuccess()){
                            List<PointAnalysis> tempDatas = response.getData();
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

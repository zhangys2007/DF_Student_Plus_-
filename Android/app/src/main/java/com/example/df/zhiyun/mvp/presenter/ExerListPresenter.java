package com.example.df.zhiyun.mvp.presenter;

import android.app.Application;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.mvp.ui.widget.PaginatorHelper;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;
import com.example.df.zhiyun.mvp.contract.ExerListContract;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.Exer;
import com.example.df.zhiyun.mvp.model.entity.ExerSet;
import com.example.df.zhiyun.mvp.model.entity.Subject;

import org.simple.eventbus.EventBus;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.example.df.zhiyun.app.EventBusTags;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/25/2019 17:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class ExerListPresenter extends BasePresenter<ExerListContract.Model, ExerListContract.View> {
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

    //    @Inject
//    Integer subjectId;
    private int subjectId;
    private String beginTime = "";
    private String endTime = "";
    private PaginatorHelper paginatorHelper = new PaginatorHelper();

    @Inject
    public ExerListPresenter(ExerListContract.Model model, ExerListContract.View rootView) {
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

    public void changeSubject(int id){
        if(subjectId != id){
            subjectId = id;
//            requestHomework(true);
        }
    }

    public void changeTime(String begin, String end) {
        if(begin != beginTime && end != endTime){
            beginTime = begin;
            endTime = end;
//            requestHomework(true);
        }

    }



    public void requestData(boolean pullToRefresh) {
        mModel.getExer(paginatorHelper.getPageIndex(pullToRefresh),subjectId,beginTime,endTime)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ExerSet>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ExerSet> response) {
                        if(response.isSuccess()){
                            if(response.getData() == null){
                                mAdapter.loadMoreEnd();
                                return;
                            }
                            EventBus.getDefault().post(response.getData().getCount()
                                    ,EventBusTags.UPDATE_EXER_NUMB);
                            List<Exer> tempDatas = response.getData().getList();

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

    public void getSubjectList(){
        mModel.getSubject()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();//转菊花
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<Subject>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<Subject>> response) {
                        if(response.isSuccess()){
                            mRootView.recvSubjDatas(response.getData());
                        }else{
                            mRootView.showMessage(response.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });
    }
}

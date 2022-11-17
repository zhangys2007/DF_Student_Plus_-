package com.example.df.zhiyun.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.example.df.zhiyun.mvp.contract.PutHWDetailContract;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.educate.mvp.model.entity.BelongClass;
import com.example.df.zhiyun.mvp.model.entity.PutHWDetail;
import com.example.df.zhiyun.mvp.ui.activity.HWClassSelActivity;
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
import timber.log.Timber;


/**
 * ================================================
 * Description: 教师布置作业页面
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 15:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class PutHWDetailPresenter extends BasePresenter<PutHWDetailContract.Model, PutHWDetailContract.View> {
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

    @Inject
    @Named(PutHWDetailContract.View.KEY_PAPER_ID)
    String mPaperId;
    @Inject
    @Named(PutHWDetailContract.View.KEY_LINK_ID)
    String mLinkId;
    @Inject
    @Named(PutHWDetailContract.View.KEY_SYSTEM_ID)
    String mSystemId;
    @Inject
    @Named(PutHWDetailContract.View.KEY_TYPE)
    Integer mType;
    @Inject
    @Named(PutHWDetailContract.View.KEY_CLASS_NAME)
    String mClassName;
    @Inject
    @Named(PutHWDetailContract.View.KEY_CLASS_ID)
    String mClassId;

    public void changeClass(String id,String name){
        mClassId = id;
        mClassName = name;
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
//        if(TextUtils.isEmpty(mClassId)){
//            Timber.tag(TAG).d("========no class id=====");
//            classList();
//        }else{
//            Timber.tag(TAG).d("========has class id=====" + mClassId);
//            mRootView.updateClassName(mClassName);
//            getData();
//        }
    }

    public void startProcess(){
        if(TextUtils.isEmpty(mClassId)){
            Timber.tag(TAG).d("========no class id=====");
            classList();
        }else{
            Timber.tag(TAG).d("========has class id=====" + mClassId);
            mRootView.updateClassName(mClassName);
            getData();
        }
    }


    //老师的班级列表
    public void getData() {
        mModel.putHomeworkDetail(mPaperId,mClassId,mType)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//转菊花
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<PutHWDetail>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<PutHWDetail> response) {
                        if(response.isSuccess()){
                            mRootView.bindDetailInfo(response.getData());
                        }else{
                            mRootView.showMessage(response.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                        super.onComplete();
                    }
                });
    }

    //老师的班级列表
    private void classList() {
        mModel.homeworkClass(mPaperId,mType,mSystemId,mLinkId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//转菊花
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<BelongClass>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<BelongClass>> response) {
                        if(response.isSuccess()){
                            if(response.getData() != null && response.getData().size()>0){
                                BelongClass belongClass = response.getData().get(0);
                                changeClass(belongClass.getClassId(),belongClass.getClassName());
                                mRootView.updateClassName(belongClass.getClassName());
                                getData();
                            }
                        }else{
                            mRootView.showMessage(response.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                        super.onComplete();
                    }
                });
    }

    @Inject
    public PutHWDetailPresenter(PutHWDetailContract.Model model, PutHWDetailContract.View rootView) {
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

    public void clickClass(){
        HWClassSelActivity.startHWClassSelPage((Activity) mRootView, PutHWDetailContract.View.REQ_SEL,mPaperId,mType,mSystemId,mLinkId);
    }
}

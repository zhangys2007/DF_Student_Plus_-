package com.example.df.zhiyun.main.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;
import com.example.df.zhiyun.main.mvp.contract.MainContract;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;

import org.simple.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.EventBusTags;
import com.example.df.zhiyun.mvp.model.entity.PersonCenter;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import timber.log.Timber;


/**
 * ================================================
 * Description: app主页面
 * <p>
 * Created by MVPArmsTemplate on 07/17/2019 10:34
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    private Boolean isExit = false;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
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

    /***
     * 点击的后退按钮
     */
    public void clickBack(){
        if (isExit == false) {
            isExit = true; // 准备退出
            mRootView.showMessage(mApplication.getResources().getString(R.string.exit_notice));
            Flowable.timer(1, TimeUnit.SECONDS)
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            isExit = false;
                        }
                    });
        } else {
            mAppManager.killAll();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
//        requestData();
    }

    /***
     * 点击里中间的刷新, 参数代表是哪个子页面
     */
    public void clickRefresh(int pageIndex){
        Timber.tag(TAG).w("refresh");
        EventBus.getDefault().post(new Integer(pageIndex), EventBusTags.UPDATE_FRAGMENT);
        requestData();
    }


    public void requestData() {
        mModel.getPersonCenterInfo()
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<PersonCenter>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<PersonCenter> response) {
                        if(response.isSuccess()){
                            processPersonCenterData(response.getData());
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


    private void processPersonCenterData(PersonCenter data){
        mRootView.setMsgCount(data.getMessageCount());
        EventBus.getDefault().post(data, EventBusTags.UPDATE_PERSON_CENTER);
    }
}

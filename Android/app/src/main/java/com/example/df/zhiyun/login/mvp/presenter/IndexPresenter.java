package com.example.df.zhiyun.login.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;
import com.example.df.zhiyun.login.mvp.contract.IndexContract;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.UserInfo;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import com.example.df.zhiyun.login.mvp.ui.activity.GuideActivity;
import com.example.df.zhiyun.login.mvp.ui.activity.LoginActivity;
import com.example.df.zhiyun.main.mvp.ui.activity.MainActivity;
import com.example.df.zhiyun.main.mvp.ui.activity.TchMainActivity;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/17/2019 09:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class IndexPresenter extends BasePresenter<IndexContract.Model, IndexContract.View> {
    private static final String KEY_OPENE = "OPEN";
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public IndexPresenter(IndexContract.Model model, IndexContract.View rootView) {
        super(model, rootView);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
//        Flowable.timer(500, TimeUnit.MILLISECONDS)
//                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        if(DataHelper.getIntergerSF(mApplication,KEY_OPENE) != 1){
//                            DataHelper.setIntergerSF(mApplication,KEY_OPENE,1);
//                            ArmsUtils.startActivity(GuideActivity.class);
//                        }else{
//                            if(!mModel.hasLogin()) {
//                                toLoginPage();
//                            }else {
//                                requestUserInfo();
//                            }
//                        }
//                    }
//                });
    }

    public void startCountTime(){
        Flowable.timer(500, TimeUnit.MILLISECONDS)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        if(DataHelper.getIntergerSF(mApplication,KEY_OPENE) != 1){
                            DataHelper.setIntergerSF(mApplication,KEY_OPENE,1);
                            ArmsUtils.startActivity(GuideActivity.class);
                            mRootView.killMyself();
                        }else{
                            if(!mModel.hasLogin()) {
                                toLoginPage();
                            }else {
                                requestUserInfo();
                            }
                        }
                    }
                });
    }

    private void requestUserInfo(){
        mModel.requestUserInfo()
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//转菊花
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UserInfo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UserInfo> response) {
                        if(response.isSuccess()){
                            mRootView.killMyself();
                            if(response.getData().getRoleId() == Api.TYPE_STUDENT){
                                mAppManager.startActivity(MainActivity.class);
                            }else{
                                mAppManager.startActivity(TchMainActivity.class);
                            }

                        }else{
                            mRootView.showMessage(response.getMessage());
                            toLoginPage();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage(t.getMessage());
                        toLoginPage();
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });
    }

    private void toLoginPage(){
        mAppManager.startActivity(LoginActivity.class);
        mRootView.killMyself();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }
}

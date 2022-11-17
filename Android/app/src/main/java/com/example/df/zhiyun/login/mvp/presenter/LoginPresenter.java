package com.example.df.zhiyun.login.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v4.app.Fragment;
import android.support.v4.app.SupportActivity;
import android.text.TextUtils;

import com.example.df.zhiyun.mvp.model.entity.UserInfo;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;
import com.example.df.zhiyun.login.mvp.contract.LoginContract;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.LoginInfo;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import com.example.df.zhiyun.R;

import com.example.df.zhiyun.main.mvp.ui.activity.MainActivity;
import com.example.df.zhiyun.main.mvp.ui.activity.TchMainActivity;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/19/2019 12:23
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
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
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView) {
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

    /**
     * 使用 2017 Google IO 发布的 Architecture Components 中的 Lifecycles 的新特性 (此特性已被加入 Support library)
     * 使 {@code Presenter} 可以与 {@link SupportActivity} 和 {@link Fragment} 的部分生命周期绑定
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {

    }

    public void login(String account,String password) {
        if(check(account,password)){
            request(account,password);
        }
    }

    private boolean check(String account,String password){
        if(TextUtils.isEmpty(account)){
            mRootView.showMessage("请输入账号");
            return false;
        }

        if(TextUtils.isEmpty(password)){
            mRootView.showMessage("请输入账号");
            return false;
        }

        return true;
    }

    public void request(String account,String password) {
        mModel.login(account, password)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
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
                    public void onNext(BaseResponse<UserInfo> loginInfoBaseResponse) {
                        if(loginInfoBaseResponse.isSuccess()){
                            if(loginInfoBaseResponse.getData().getRoleId() == Api.TYPE_STUDENT){
                                mAppManager.startActivity(MainActivity.class);
                            }else{
                                mAppManager.startActivity(TchMainActivity.class);
                            }

                            mRootView.killMyself();
                        }else{
                            mRootView.showMessage(loginInfoBaseResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });
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
}

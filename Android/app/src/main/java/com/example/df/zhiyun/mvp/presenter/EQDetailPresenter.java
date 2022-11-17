package com.example.df.zhiyun.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v4.app.Fragment;

import com.example.df.zhiyun.app.EventBusTags;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.ErrorDetail;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import com.example.df.zhiyun.mvp.ui.fragment.EQD_selFragment;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;
import javax.inject.Named;

import com.example.df.zhiyun.mvp.contract.EQDetailContract;

import org.simple.eventbus.EventBus;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description: 错题详情
 * <p>
 * Created by MVPArmsTemplate on 08/02/2019 09:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class EQDetailPresenter extends BasePresenter<EQDetailContract.Model, EQDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    @Named("questionId")
    String questionId;

    @Inject
    @Named("subjectId")
    String subjId;

    @Inject
    Boolean hideMyAnswer;

    @Inject
    public EQDetailPresenter(EQDetailContract.Model model, EQDetailContract.View rootView) {
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
//        getDetail();
    }

    public void getDetail(){
        mModel.getErrorDetail(questionId,subjId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//转菊花
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<ErrorDetail>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<ErrorDetail>> response) {
                        if(response.isSuccess() && response.getData() != null && response.getData().size()>0){
                            processData(response.getData().get(0));
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

    /**
     * 根据不同的题目类型选择不同的fragment渲染
     * @param response
     */
    private void processData(ErrorDetail response){
        if(response != null ){
            Fragment fragment;
            ErrorDetail errorDetail = response;
            mRootView.setStore(errorDetail.getIsCollection()== Api.STORE_YES);
//            if(TextUtils.equals(errorDetail.getQuestionType() , "选择题")){
                fragment = EQD_selFragment.newInstance(errorDetail,hideMyAnswer);
                mRootView.setFragment(fragment);
//            }
        }
    }

    /**
     * 更新收藏
     */
    public void updateCollection(boolean collecte){
        mModel.updateCollection(questionId,collecte)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//转菊花
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        if(response.isSuccess()){
                            mRootView.setStore(collecte);
                            EventBus.getDefault().post(new Integer(1), EventBusTags.REQUEST_PERSON_CENTER);
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

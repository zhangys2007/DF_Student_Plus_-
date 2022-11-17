package com.example.df.zhiyun.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.EventBusTags;
import com.example.df.zhiyun.mvp.contract.CancelHWContract;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.educate.mvp.model.entity.BelongClass;
import com.example.df.zhiyun.mvp.ui.adapter.CancelHWClassAdapter;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import org.simple.eventbus.EventBus;

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
public class CancelHWPresenter extends BasePresenter<CancelHWContract.Model, CancelHWContract.View> {
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
    @Named(CancelHWContract.View.KEY_PAPER_ID)
    String mPaperId;
    @Inject
    @Named(CancelHWContract.View.KEY_LINK_ID)
    String mLinkId;
    @Inject
    @Named(CancelHWContract.View.KEY_SYSTEM_ID)
    String mSystemId;
    @Inject
    @Named(CancelHWContract.View.KEY_TYPE)
    Integer mType;
    @Inject
    @Named(CancelHWContract.View.KEY_PAPER_NAME)
    String mPaperName;



    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
//        classList();
    }


    //点击了布置
    public void clickCancel(){
        if(TextUtils.isEmpty(((CancelHWClassAdapter)mAdapter).getSelectedClasses())){
            mRootView.showMessage("请选择至少一个班级");
            return;
        }
        cancelHomework();
    }


    //老师的班级列表
    public void classList() {
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
                                mAdapter.setNewData(response.getData());
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

    private void cancelHomework(){
        mModel.cancelHomework(mPaperId,((CancelHWClassAdapter)mAdapter).getSelectedClasses(),mType)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<String>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<String> response) {
                        if(response.isSuccess()){
                            if(TextUtils.isEmpty(response.getData())){
                                Toast.makeText((Context)mRootView,R.string.cancel_homework_ok,Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText((Context)mRootView,"班级："+response.getData()+"取消不成功",Toast.LENGTH_SHORT).show();
                            }

                            EventBus.getDefault().post(new Integer(1), EventBusTags.UPDATE_PUT_REMOVE_HW);
                            mRootView.killMyself();
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
    public CancelHWPresenter(CancelHWContract.Model model, CancelHWContract.View rootView) {
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
}

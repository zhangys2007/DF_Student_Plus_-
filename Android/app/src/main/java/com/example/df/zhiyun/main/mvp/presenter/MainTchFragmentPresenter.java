package com.example.df.zhiyun.main.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.main.mvp.model.entity.FocusStd;
import com.example.df.zhiyun.main.mvp.model.entity.HomePageData;
import com.example.df.zhiyun.main.mvp.model.entity.MsgItem;
import com.example.df.zhiyun.main.mvp.model.entity.PushData;
import com.example.df.zhiyun.main.mvp.model.entity.TodoItem;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.PersonCenter;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;
import com.example.df.zhiyun.main.mvp.contract.MainTchFragmentContract;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;
import timber.log.Timber;

import javax.inject.Inject;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/06/2019 10:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class MainTchFragmentPresenter extends BasePresenter<MainTchFragmentContract.Model, MainTchFragmentContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private List<MsgItem> mMsgList; //走马灯的内容
    private Disposable mMarqueDisp; // 走马灯控制

    @Inject
    public MainTchFragmentPresenter(MainTchFragmentContract.Model model, MainTchFragmentContract.View rootView) {
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

    public void requestData() {
        mModel.getHomepageData()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//转菊花
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<HomePageData>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<HomePageData> response) {
                        if(response.isSuccess()){
                            processData(response);
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

    private void processData(BaseResponse<HomePageData> response){
        HomePageData homePageData = response.getData();
        if(homePageData == null){
            return;
        }

        processMessage(homePageData.getMessageData());

        BaseResponse<List<PushData>> pushData = homePageData.getPushData();
        if(pushData != null && pushData.getData() != null){
            mRootView.processPush(pushData.getData());
        }else{
            mRootView.processPush(new ArrayList<>());
        }

        BaseResponse<List<TodoItem>> todoData = homePageData.getHomeWorkData();
        if(todoData != null && todoData.getData() != null){
            mRootView.processTodo(todoData.getData());
        }else{
            mRootView.processTodo(new ArrayList<>());
        }

        BaseResponse<List<FocusStd>> followStudentData = homePageData.getTeacherFollowStudentData();
        if(followStudentData != null && followStudentData.getData() != null){
            mRootView.processFollow(followStudentData.getData());
        }else{
            mRootView.processFollow(new ArrayList<>());
        }
    }

    /**
     * 处理消息
     * @param messageData
     */
    private void processMessage(BaseResponse<List<MsgItem>> messageData){
        if(messageData != null && messageData.getData() != null
            && messageData.getData().size() > 0){
            mMsgList = messageData.getData();
            mRootView.showMarque(true);
            startMarquee();
        }else{
            if(mMarqueDisp != null && !mMarqueDisp.isDisposed()){
                mMarqueDisp.dispose();
            }
            mRootView.showMarque(false);
        }
    }

    /***
     * 开启跑马灯
     */
    public void startMarquee(){
        if(mMarqueDisp != null && !mMarqueDisp.isDisposed()){
            mMarqueDisp.dispose();
        }
        mMarqueDisp = Flowable.interval(3,TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        if(mMsgList != null && mMsgList.size() > 0){
                            int index = (int) (aLong % mMsgList.size());
                            mRootView.marqueeNext(mMsgList.get(index));
                        }
                    }
                });
    }
}

package com.example.df.zhiyun.main.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;
import com.example.df.zhiyun.main.mvp.contract.MainFragmentContract;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.Homework;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import com.example.df.zhiyun.mvp.model.adapterEntity.MainHomeworkMultipleItem;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/17/2019 13:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class MainFragmentPresenter extends BasePresenter<MainFragmentContract.Model, MainFragmentContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
    }
    private static final String[] marqueeTitles = {"用心学习，会有无限可能"
            ,"不放手，直到梦想抓到手"};

    /***
     * 开启跑马灯
     */
    public void startMarquee(){
//        Flowable.interval(0,3, TimeUnit.SECONDS)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(@NonNull Long aLong) throws Exception {
//                        mRootView.marqueeNext("dd");
//                    }
//                });
                Flowable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        mRootView.marqueeNext(marqueeTitles[0]);
                    }
                });
    }

    @Inject
    public MainFragmentPresenter(MainFragmentContract.Model model, MainFragmentContract.View rootView) {
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
     * 请求网络数据
     */
    public void requestData(){
        mModel.getHomeworkNewest()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//转菊花
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//转菊花
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<Homework>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<Homework>> response) {
                        if(response.isSuccess()){
                            processHomeworkData(response.getData());
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

    /**
     * 如果有零个，就显示一个空项,
     * @param datas
     */
    private void processHomeworkData(List<Homework> datas){
        List<MainHomeworkMultipleItem> items = new ArrayList<>();
        MainHomeworkMultipleItem temp;
//        if(datas == null || datas.size()==0){
//            temp = new MainHomeworkMultipleItem(MainHomeworkMultipleItem.TYPE_EMPTY,null);
//            items.add(temp);
//        }
        if(datas == null){
            datas = new ArrayList<>();
        }

        for(Homework homework: datas){
            if(items.size() >= 3){
                break;
            }
            if(items.size() == 0){
                temp = new MainHomeworkMultipleItem(MainHomeworkMultipleItem.TYPE_TOP, homework);
                items.add(temp);
            }else{
                temp = new MainHomeworkMultipleItem(MainHomeworkMultipleItem.TYPE_SUB, homework);
                items.add(temp);
            }
        }
//        mRootView.bindHomeworkData(items);
    }
}

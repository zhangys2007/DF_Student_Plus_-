package com.example.df.zhiyun.setting.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

//import com.example.df.zhiyun.app.CorrectionStorageManager;
import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.example.df.zhiyun.app.utils.DiscSizeUtil;
import com.example.df.zhiyun.app.utils.FileUtils;
import com.example.df.zhiyun.mvp.model.entity.Edition;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;
import com.example.df.zhiyun.setting.mvp.contract.SettingContract;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import com.example.df.zhiyun.login.mvp.ui.activity.LoginActivity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/21/2019 13:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SettingPresenter extends BasePresenter<SettingContract.Model, SettingContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public SettingPresenter(SettingContract.Model model, SettingContract.View rootView) {
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
//        calculateCacheSize();
        getEdition();
    }

    //先删除本人的作业答案缓存
    public void logout() {
        Observable.just(1)
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        QuestionAnswerHolder.getInstance().removeAllAnswers(mApplication);
//                        CorrectionStorageManager.removeAllCorrections(mApplication);
                        return 1;
                    }
                })
                .flatMap(new Function<Integer, ObservableSource<BaseResponse>>() {
                    @Override
                    public ObservableSource<BaseResponse> apply(Integer integer) throws Exception {
                        return mModel.logout();
                    }
                })
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//转菊花
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                    mAppManager.killAll();
                    mAppManager.startActivity(LoginActivity.class);
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage(t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });
    }

    public void calculateCacheSize(){
                Observable.zip(calculateCache(), calculateStorage(), new BiFunction<Long, String, Map<String,Object>>() {
                    @Override
                    public Map<String,Object> apply(Long aLong, String s) throws Exception {
                        Map<String, Object> map = new HashMap<>();
                        map.put("cache",aLong);
                        map.put("storage",s);
                        return map;
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//转菊花
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Map<String,Object>>(mErrorHandler) {
                    @Override
                    public void onNext(Map<String,Object> map) {
                        Long cacheSize = (Long) map.get("cache");
                        mRootView.setCacheSize(cacheSize);
                        String storageSize = (String) map.get("storage");
                        mRootView.setStorageSize(storageSize);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage(t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });
    }

    public void cleanCachSize(){
            cleanCache()
                    .flatMap(new Function<Long, ObservableSource<Map<String,Object>>>() {
                        @Override
                        public ObservableSource<Map<String,Object>> apply(Long aLong) throws Exception {
                            return Observable.zip(calculateCache(), calculateStorage(), new BiFunction<Long, String, Map<String,Object>>() {
                                @Override
                                public Map<String,Object> apply(Long aLong, String s) throws Exception {
                                    Map<String, Object> map = new HashMap<>();
                                    map.put("cache",aLong);
                                    map.put("storage",s);
                                    return map;
                                }
                            });
                        }
                    })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//转菊花
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<Map<String,Object>>(mErrorHandler) {
                    @Override
                    public void onNext(Map<String,Object> map) {
                        Long cacheSize = (Long) map.get("cache");
                        mRootView.setCacheSize(cacheSize);
                        String storageSize = (String) map.get("storage");
                        mRootView.setStorageSize(storageSize);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage(t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });
    }

    private Observable<Long> cleanCache(){
        return Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                File cacheFile = mApplication.getCacheDir();
                File cacheExternalFile = mApplication.getExternalCacheDir();

                File[] files = cacheFile.listFiles();
                if(files != null && files.length >0){
                    for (File file : files) {
                        if (file.isFile()) {
                            file.delete();
                        } else if (file.isDirectory()) {
                            FileUtils.deleteDir(file);
                        }
                    }
                }

                if(cacheExternalFile != null && cacheExternalFile.exists()){
                    files = cacheExternalFile.listFiles();
                    if(files != null && files.length >0){
                        for (File file : files) {
                            if (file.isFile()) {
                                file.delete();
                            } else if (file.isDirectory()) {
                                FileUtils.deleteDir(file);
                            }
                        }
                    }
                }
                emitter.onNext(1l);
                emitter.onComplete();
            }
        });
    }


    private Observable<Long> calculateCache(){
        return Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                long cacheSize = DataHelper.getDirSize(mApplication.getCacheDir());
                cacheSize += DataHelper.getDirSize(mApplication.getExternalCacheDir());
                emitter.onNext(cacheSize);
                emitter.onComplete();
            }
        });
    }

    private Observable<String> calculateStorage(){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                long sdAvail = DiscSizeUtil.readSDCardAvailable();
                long sdTotal = DiscSizeUtil.readSDCardTotal();
                long sysAvail = DiscSizeUtil.readSystemAvailable();
                long sysTotal = DiscSizeUtil.readSystemTotal();

                float avail =  (sdAvail+sysAvail)/(1024f*1024f*1024f);
                float total =  (sdTotal+sysTotal)/(1024f*1024f*1024f);
                emitter.onNext(String.format("总共%.2fG/可用%.2fG",total,avail));
                emitter.onComplete();
            }
        });
    }

    public void getEdition(){
        mModel.getEdition()
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<Edition>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<Edition> response) {
                        if(response.isSuccess()){
                            mRootView.bindEditionData(response.getData());
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

package com.example.df.zhiyun.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;
import com.example.df.zhiyun.mvp.model.adapterEntity.VersionItemMultipleItem;
import com.example.df.zhiyun.mvp.model.adapterEntity.VersionPublisherMultipleItem;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.VersionItem;
import com.example.df.zhiyun.mvp.model.entity.VersionPublisher;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;
import javax.inject.Named;

import com.example.df.zhiyun.mvp.contract.VersionSwitchContract;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/21/2019 13:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class VersionSwitchPresenter extends BasePresenter<VersionSwitchContract.Model, VersionSwitchContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    BaseMultiItemQuickAdapter mAdapter;

    @Inject
    @Named("subjId")
    String subjName;

    @Inject
    public VersionSwitchPresenter(VersionSwitchContract.Model model, VersionSwitchContract.View rootView) {
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
        requestData();
    }

    public void requestData(){
        mModel.getBookVersion(subjName)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                        mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<VersionPublisher>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<VersionPublisher>> response) {
                        if(response.isSuccess()){
                            proccessData(response.getData());
                        }else{
                            mRootView.showMessage(response.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }

    private void proccessData(List<VersionPublisher> listPublisher){
        if(listPublisher == null || listPublisher.size() == 0){
            return;
        }

        ArrayList<MultiItemEntity> res = new ArrayList<>();

        for (int i=0;i<listPublisher.size();i++) {
            VersionPublisherMultipleItem lv0 = new VersionPublisherMultipleItem(listPublisher.get(i));

            List<VersionItem> listItem = listPublisher.get(i).getList();

            if(listItem != null && listItem.size() > 0 ){
                for (int j = 0; j < listItem.size(); j++) {
                    VersionItemMultipleItem lv1 = new VersionItemMultipleItem(listItem.get(j),listPublisher.get(i),j);
                    lv0.addSubItem(lv1);
                }
            }

            res.add(lv0);
        }

        mAdapter.setNewData(res);
    }
}

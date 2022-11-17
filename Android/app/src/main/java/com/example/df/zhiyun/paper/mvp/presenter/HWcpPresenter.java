package com.example.df.zhiyun.paper.mvp.presenter;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;

import com.example.df.zhiyun.app.utils.MediaPickerHelper;
import com.example.df.zhiyun.mvp.ui.widget.CanvasDialog;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import timber.log.Timber;

import javax.inject.Inject;

import com.example.df.zhiyun.paper.mvp.contract.HWcpContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/10/2019 13:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HWcpPresenter extends BasePresenter<HWcpContract.Model, HWcpContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public HWcpPresenter(HWcpContract.Model model, HWcpContract.View rootView) {
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

    public void onCameraBack(File file){
        if(file != null){
            List<File> list = new ArrayList<>();
            list.add(file);
            MediaPickerHelper.getCompressBitmapFromFiles(((Fragment)mRootView).getContext(),list)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe(disposable -> {
                        mRootView.showLoading();//转菊花
                    }).subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> {
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                    })
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(new Observer<List<Bitmap>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<Bitmap> bitmaps) {
                            if(bitmaps != null && bitmaps.size()>0){
                                ((CanvasDialog.ICanvasEnvent)mRootView).onBitmapCreate(bitmaps.get(0));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.tag(TAG).d(e.toString());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        }
    }

    public void onAlbumBack(List<Uri> uriList){
        if(uriList == null || uriList.size()==0){
            return;
        }

        MediaPickerHelper.getCompressBitmapFromUris(((Fragment)mRootView).getContext(),uriList)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();//转菊花
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Observer<List<Bitmap>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Bitmap> bitmaps) {
                        if(bitmaps != null && bitmaps.size()>0){
                            ((CanvasDialog.ICanvasEnvent)mRootView).onBitmapCreate(bitmaps.get(0));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.tag(TAG).d(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}

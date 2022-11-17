package com.example.df.zhiyun.my.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;

import com.example.df.zhiyun.app.utils.FileUriUtil;
import com.example.df.zhiyun.app.utils.RegexUtils;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;
import com.example.df.zhiyun.my.mvp.contract.ProfileContract;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.UserInfo;
//import com.yalantis.ucrop.UCrop;

import org.simple.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import com.example.df.zhiyun.app.EventBusTags;
import com.example.df.zhiyun.app.utils.MediaPickerHelper;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description:个人资料
 * <p>
 * Created by MVPArmsTemplate on 07/21/2019 15:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ProfilePresenter extends BasePresenter<ProfileContract.Model, ProfileContract.View> implements MediaPickerHelper.IMediaPicker {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private Uri mSrcUri;  //待剪切图片
    private File mCropFile; //剪切的图片

    @Inject
    public ProfilePresenter(ProfileContract.Model model, ProfileContract.View rootView) {
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

    public void updateAvarta(String value){
        updateUserProfile("headImage",value);
    }

    public void updateUserName(String value){
        updateUserProfile("userName",value);
    }

    //1 男  2女
    public void updateSex(int value){
        updateUserProfile("sex",value);
    }

    public void updateBirthday(String value){
        updateUserProfile("birthday",value);
    }

    public void updateEmail(String value){
        if(TextUtils.isEmpty(value)){
            return;
        }

        if(RegexUtils.checkEmail(value)){
            updateUserProfile("email",value);
        }else{
            mRootView.showMessage("邮箱格式不正确");
        }

    }

    public void updateMobile(String value){
        if(TextUtils.isEmpty(value)){
            return;
        }
        if(RegexUtils.checkMobile(value)){
            updateUserProfile("phone",value);
        }else{
            mRootView.showMessage("手机号码不正确");
        }
    }

    public void updatePassword(String psw1,String psw2){
        if(TextUtils.isEmpty(psw1) || TextUtils.isEmpty(psw2) ){
            mRootView.showMessage("请输入密码");
            return;
        }

        if(psw1.trim() != psw2.trim()){
            mRootView.showMessage("密码不一致");
            return;
        }
        updateUserProfile("password",psw1);
    }

    public void updateSchool(String value){
        updateUserProfile("school",value);
    }

    public void updateUserProfile(String key,Object value) {
        Map<String,Object> params = new HashMap<>();
        params.put(key,value);
        mModel.updateUserProfile(params)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UserInfo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UserInfo> response) {
                        if(response.isSuccess()){
                            mModel.updateLocalInfo(response.getData());
                            mRootView.bindData();
                            EventBus.getDefault().post(EventBusTags.UPDATE_USERINFO);
                        }else{

                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage(t.getMessage());
                    }
                });
    }

    /**
     * 压缩并上传
     */
    public void uploadPicture() {
        if(mCropFile == null || !mCropFile.exists()){
            return;
        }
        List<File> list = new ArrayList<>();
        list.add(mCropFile);

        MediaPickerHelper.getCompressBitmapFromFiles(mRootView.getMyActivity(),list)
                .flatMap(new Function<List<Bitmap>, ObservableSource<BaseResponse<UserInfo>>>() {
                    @Override
                    public ObservableSource<BaseResponse<UserInfo>> apply(List<Bitmap> bitmaps) throws Exception {
                        return mModel.uploadPic(bitmaps.get(0));
                    }
                })
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<UserInfo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<UserInfo> response) {
                        if(response.isSuccess()){
                            mModel.updateLocalInfo(response.getData());
                            mRootView.bindData();
                            EventBus.getDefault().post(new Integer(1),EventBusTags.UPDATE_USERINFO);
                        }else{

                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage(t.getMessage());
                    }
                });
    }


    public void selectCamera(){
        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.launchCamera(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
                mSrcUri = FileUriUtil.getUriFromFile(mRootView.getMyActivity(),
                        FileUriUtil.getTempPicFile(mRootView.getMyActivity()));
                if(mSrcUri != null){
                    mRootView.lunchCamera(mSrcUri);
                }
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.showMessage("请求权限失败");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.showMessage("请到设置中开启必要权限");
            }
        }, mRootView.getRxPermissions(), mErrorHandler);
    }

    public void selectAlbum(){
        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
                mRootView.lunchAlbum();
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                mRootView.showMessage("请求权限失败");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {
                mRootView.showMessage("请到设置中开启必要权限");
            }
        }, mRootView.getRxPermissions(), mErrorHandler);
    }


    /**
     * 剪裁中成功返回，立即开始上传。
     * @param intent
     */
    public void cropBack(Intent intent){
        MediaPickerHelper.updateAlbum(mRootView.getMyActivity(),mCropFile.getAbsolutePath());
        uploadPicture();
    }

    /**
     * 从拍照中成功返回,立即开始剪裁
     * @param intent
     */
    public void cameraBack(Intent intent){
//        MediaPickerHelper.updateAlbum(mRootView.getMyActivity(),mSrcUri);
        mRootView.cropPic(mSrcUri,setCropUri());
    }

    /**
     * 从相册中成功返回,立即开始剪裁
     * @param imgUri
     */
    public void albumBack(Uri imgUri){
        mRootView.cropPic(imgUri, setCropUri());
    }

    //指定剪切的图片放在哪里
    private Uri setCropUri(){
        mCropFile = FileUriUtil.getTempPicFile(mRootView.getMyActivity());
        return   FileUriUtil.getUriFromFile(mRootView.getMyActivity(), mCropFile);
    }
}

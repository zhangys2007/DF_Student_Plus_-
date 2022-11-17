package com.example.df.zhiyun.analy.mvp.presenter;

import android.app.Application;

import com.example.df.zhiyun.analy.mvp.contract.MeasureSumaryContract;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.MeasureSumary;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 13:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class MeasureSumaryPresenter extends BasePresenter<MeasureSumaryContract.Model, MeasureSumaryContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    @Named(MeasureSumaryContract.View.KEY_FZ)
    Integer mFz;
    @Inject
    @Named(MeasureSumaryContract.View.KEY_TYPE)
    Integer mType;
    @Inject
    @Named(MeasureSumaryContract.View.KEY_SCHOOLID)
    Integer mSchoolId;
    @Inject
    @Named(MeasureSumaryContract.View.KEY_GRADEID)
    Integer mGradeId;
    @Inject
    @Named(MeasureSumaryContract.View.KEY_HOMEWORK_ID)
    String mHomeworkId;

    @Inject
    public MeasureSumaryPresenter(MeasureSumaryContract.Model model, MeasureSumaryContract.View rootView) {
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

    //老师的班级列表
    public void getMeasureSumary() {
        mModel.findTestStatus(mHomeworkId,mFz,mType,mGradeId,mSchoolId)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<MeasureSumary>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<MeasureSumary> response) {
                        if(response.isSuccess() && response.getData() != null){
                            MeasureSumary measureSumary = response.getData();
                            if(measureSumary.getScoreOverviewList() != null && measureSumary.getScoreOverviewList().size() > 0){
                                mRootView.bindSumaryData(measureSumary.getScoreOverviewList().get(0));
                            }

                            if(measureSumary.getGradeDistributionList() != null && measureSumary.getGradeDistributionList().size() > 0){
                                mRootView.bindLevelData(measureSumary.getGradeDistributionList().get(0));
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

}

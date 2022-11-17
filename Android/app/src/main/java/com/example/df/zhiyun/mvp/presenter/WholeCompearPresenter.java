package com.example.df.zhiyun.mvp.presenter;

import android.app.Application;

import com.example.df.zhiyun.mvp.contract.WholeCompearContract;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.ScoreCompear;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import org.json.JSONObject;

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
public class WholeCompearPresenter extends BasePresenter<WholeCompearContract.Model, WholeCompearContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    @Named(WholeCompearContract.View.KEY_FZ)
    Integer mFz;
    @Inject
    @Named(WholeCompearContract.View.KEY_TYPE)
    Integer mType;
    @Inject
    @Named(WholeCompearContract.View.KEY_SCHOOLID)
    Integer mSchoolId;
    @Inject
    @Named(WholeCompearContract.View.KEY_GRADEID)
    Integer mGradeId;
//    @Inject
//    @Named(WholeCompearContract.View.KEY_HOMEWORK_ID)
//    String mHomeworkId;

    @Inject
    public WholeCompearPresenter(WholeCompearContract.Model model, WholeCompearContract.View rootView) {
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

    public void getData(){
        mModel.getScoreCompear(mFz,mType,mGradeId,mSchoolId)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ScoreCompear>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ScoreCompear> response) {
                        if(response.isSuccess()){
                            if(response.getData() == null){
                                return;
                            }

                            if(response.getData().getScoreOverviewList() != null){
                                mRootView.bindSumary(response.getData().getScoreOverviewList());
                            }

                            if(response.getData().getArrangementContrastList() != null){
                                mRootView.bindLevel(response.getData().getDistributionList());
                            }

                            if(response.getData().getScoreOverviewList() != null){
                                mRootView.bindLayout(response.getData().getArrangementContrastList());
                            }
                        }else{
                            mRootView.showMessage(response.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage(t.getMessage());
                    }
                });
    }
}

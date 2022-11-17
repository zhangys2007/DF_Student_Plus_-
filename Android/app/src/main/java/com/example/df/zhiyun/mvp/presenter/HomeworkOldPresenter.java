package com.example.df.zhiyun.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.correct.mvp.ui.activity.CorrectHWActivity;
import com.example.df.zhiyun.mvp.ui.widget.PaginatorHelper;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;
import com.example.df.zhiyun.mvp.contract.HomeworkOldContract;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.Homework;
import com.example.df.zhiyun.mvp.model.entity.Subject;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/22/2019 10:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HomeworkOldPresenter extends BasePresenter<HomeworkOldContract.Model, HomeworkOldContract.View> {
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

    private int subjectId;
    private PaginatorHelper paginatorHelper = new PaginatorHelper();
    private String beginTime = "";
    private String endTime = "";

    @Inject
    public HomeworkOldPresenter(HomeworkOldContract.Model model, HomeworkOldContract.View rootView) {
        super(model, rootView);
    }


    public void changeSubject(int id){
        if(subjectId != id){
            subjectId = id;
//            requestHomework(true);
        }
    }

    public void changeTime(String begin, String end) {
        if(begin != beginTime && end != endTime){
            beginTime = begin;
            endTime = end;
//            requestHomework(true);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }


    public void requestHomework(boolean pullToRefresh) {
        mModel.getHomework(paginatorHelper.getPageIndex(pullToRefresh),subjectId,beginTime,endTime)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
                    if (pullToRefresh)
                        mRootView.showLoading();//显示下拉刷新的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
                    if (pullToRefresh)
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<Homework>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<Homework>> response) {
                        if(response.isSuccess()){
                            List<Homework> tempDatas = response.getData();
                            paginatorHelper.onDataArrive(mAdapter,pullToRefresh,tempDatas,null);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mAdapter.loadMoreFail();
                    }
                });
    }

    public void getSubjectList(){
        mModel.getSubject()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();//转菊花
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<Subject>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<Subject>> response) {
                        if(response.isSuccess()){
                            mRootView.recvSubjDatas(response.getData());
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

    //去历史试卷
    public void viewHistoryPaper(Homework homework) {
//        PreviewHomeworkActivity.launchActivity(((Fragment)mRootView).getContext(),homework.getStudentHomeworkId(),
//                Api.STUDUNTEN_ANSWER_TYPE_HISTORY,subjectId);
        if(homework != null && !TextUtils.isEmpty(homework.getStudentHomeworkId())){
            CorrectHWActivity.launchActivity(mRootView.getPageContext(),homework.getType(),homework.getStudentHomeworkId()
                    ,0,homework.getCorrectionStatus() == Api.STUDENT_ANSWER_STATUS_READED);
        }
    }
}

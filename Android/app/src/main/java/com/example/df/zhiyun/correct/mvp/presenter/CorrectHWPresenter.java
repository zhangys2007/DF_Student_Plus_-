package com.example.df.zhiyun.correct.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;

//import com.example.df.zhiyun.app.CorrectionStorageManager;
import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.example.df.zhiyun.correct.mvp.contract.CorrectHWContract;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.HomeworkSet;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.correct.mvp.ui.activity.CorrectCardActivity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 14:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class CorrectHWPresenter extends BasePresenter<CorrectHWContract.Model, CorrectHWContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    @Named(CorrectHWContract.View.KEY_TYPE)
    Integer mType;

    @Inject
    @Named(CorrectHWContract.View.KEY_STUDENT_COUNT)
    Integer mStudentCount;

    @Inject
    @Named(CorrectHWContract.View.KEY_ID)
    String mStudentHomeworkId;

    @Inject
    Boolean mCorrect;

    @Inject
    public CorrectHWPresenter(CorrectHWContract.Model model, CorrectHWContract.View rootView) {
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

    public String getListenerBaseUrl(){
        return QuestionAnswerHolder.getInstance().getHomework().getHearingUrl();
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {}

    public void requestData() {
        mModel.homeworkCorrecting(mType,mStudentHomeworkId,mStudentCount)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<HomeworkSet>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<HomeworkSet> response) {
                        if(response.isSuccess()){
                            processData(response.getData());
                        }else{
                            mRootView.showMessage(response.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });
    }

    private void processData(HomeworkSet data){
        List<Question> questionList = data.getList();
        if(questionList == null || questionList.size() == 0){
            return;
        }

        mStudentHomeworkId = data.getStudentHomeWorkId();
        QuestionAnswerHolder.getInstance().setHomework(data);
        mRootView.initViewPager(questionList);
    }

    //上传某道题的批改，如果已批改的不用提交
    private Observable<BaseResponse> submitQuestionCorrection(int index){
        if(QuestionAnswerHolder.getInstance().getHomework().getList() == null &&
                QuestionAnswerHolder.getInstance().getHomework().getList().size() <= index){
            BaseResponse response = new BaseResponse();
            response.setMessage("");
            response.setCode(Api.success);
            return Observable.just(response);
        }

        Question q = QuestionAnswerHolder.getInstance().getHomework().getList().get(index);
        if(q.getCorrectParam() == null){
            BaseResponse response = new BaseResponse();
            response.setMessage("");
            response.setCode(Api.success);
            return Observable.just(response);
        }

        return mModel.questionCorrect(QuestionAnswerHolder.getInstance().getHomework().getList().get(index),
                QuestionAnswerHolder.getInstance().getHomework().getSubjectId(),
                QuestionAnswerHolder.getInstance().getHomework().getStudentHomeWorkId())
                .map(new Function<BaseResponse, BaseResponse>() {
                    @Override
                    public BaseResponse apply(BaseResponse baseResponse) throws Exception {
                        if(baseResponse.isSuccess()){
                            HomeworkSet homeworkSet = QuestionAnswerHolder.getInstance().getHomework();
//                            CorrectionStorageManager.saveQuestionCorrection(mApplication,homeworkSet.getStudentHomeWorkId(),
//                                    homeworkSet.getList().get(index));
                        }
                        return baseResponse;
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
                ;
    }

    /**
     * 保存当前页批改内容，跳转到下一页
     * @param curIndex
     * @param nextIndex
     */
    public void changePage(int curIndex,int nextIndex){
        submitQuestionCorrection(curIndex)
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        if(response.isSuccess()){
                            mRootView.changeViewpagerSelect(nextIndex);
                        }else{
                            mRootView.showMessage(response.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });
    }

    /**
     * 保存当前页批改内容，跳转到批改卡
     */
    public void submitAllCorrections(int curIndex){
        submitQuestionCorrection(curIndex)
            .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                @Override
                public void onNext(BaseResponse response) {
                    if(response.isSuccess()){
                        CorrectCardActivity.launchActivity((Context)mRootView,mCorrect);
                    }else{
                        mRootView.showMessage(response.getMessage());
                    }
                }

                @Override
                public void onError(Throwable t) {
                    super.onError(t);
                }

                @Override
                public void onComplete() {
                    super.onComplete();
                }
            });
    }


}

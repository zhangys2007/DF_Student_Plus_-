package com.example.df.zhiyun.paper.mvp.presenter;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.example.df.zhiyun.mvp.model.entity.Answer;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.HomeworkSet;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.paper.mvp.contract.SetHomeworkContract;
import com.example.df.zhiyun.paper.mvp.ui.activity.CardActivity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.PermissionUtil;
import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
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
public class SetHomeworkPresenter extends BasePresenter<SetHomeworkContract.Model, SetHomeworkContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;


    @Inject
    @Named(SetHomeworkContract.View.KEY_SUBJECTID)
    Integer mSubjectId;

    @Inject
    @Named(SetHomeworkContract.View.KEY_JOB_TYPE)
    Integer mJobType;


    @Inject
    @Named(SetHomeworkContract.View.KEY_STD_HW_ID)
    String mStdHwId;
    @Inject
    @Named(SetHomeworkContract.View.KEY_UUID)
    String mUUID;
    @Inject
    @Named(SetHomeworkContract.View.KEY_SCHOOLID)
    String mSchoolId;

    long timeLast = 0;  //答题时间；
    Disposable mDisposableCount;

    public String getListenerBaseUrl(){
        return QuestionAnswerHolder.getInstance().getHomework().getHearingUrl();
    }

    @Inject
    public SetHomeworkPresenter(SetHomeworkContract.Model model, SetHomeworkContract.View rootView) {
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

    //请求跳转到下一题后保存之前那页的答案，并且上传服务器当前题的答案
    public void saveAndSubmitQuestion(int position){
        savePageAnswer(position);
        submitQuestion(position)
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        if(response.isSuccess()){
//                            mRootView.changeViewpagerSelect(position);
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

    //点击了答题卡
    public void clickCard(int position) {
        savePageAnswer(position);

        submitQuestion(position)
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        if(response.isSuccess()){
                            String hwId = QuestionAnswerHolder.getInstance().getHomework().getHomeWorkId();
                            CardActivity.launchActivity((Context)mRootView,mStdHwId,hwId,
                                    mUUID,mSubjectId,mSchoolId,mJobType,timeLast);
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


    public void requestInitWithAnswer(int currentIndex){
        mRootView.initQuestionWithAnswer(currentIndex,
                QuestionAnswerHolder.getInstance().getAnswerByIndex(currentIndex));
    }


    public void requestData() {
        mModel.studentDoHomework(mStdHwId, mUUID,mSubjectId,mJobType,mSchoolId)
                .map(new Function<BaseResponse<HomeworkSet>, BaseResponse<HomeworkSet>>() {
                    @Override
                    public BaseResponse<HomeworkSet> apply(BaseResponse<HomeworkSet> homeworkSetBaseResponse) throws Exception {
                        if(homeworkSetBaseResponse.isSuccess() && homeworkSetBaseResponse.getData() != null){
                            HomeworkSet homeworkSet = homeworkSetBaseResponse.getData();
                            homeworkSet.setStudentHomeWorkId(homeworkSet.getStudentHomeWorkId());
                            homeworkSet.setSubjectId(Integer.toString(mSubjectId));
                            QuestionAnswerHolder.getInstance().setHomework(homeworkSetBaseResponse.getData());
//                            QuestionAnswerHolder.getInstance().restoreAnswersFromFile(mApplication);
//
                        }
                        return homeworkSetBaseResponse;
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

                    HomeworkSet homeworkSet = QuestionAnswerHolder.getInstance().getHomework();
                    if(homeworkSet == null || homeworkSet.getList() == null || homeworkSet.getList().size() == 0){
                        mRootView.disableOption();
                        return;
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<HomeworkSet>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<HomeworkSet> response) {
                        if(response.isSuccess()){
                            processData();
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

    private void processData(){
        HomeworkSet homeworkSet = QuestionAnswerHolder.getInstance().getHomework();

        if(homeworkSet == null || homeworkSet.getList() == null || homeworkSet.getList().size() == 0){
            return;
        }
        mRootView.updateTitle(homeworkSet.getColumnName());
        List<Question> questionList = homeworkSet.getList();

        startCountTime();
        mRootView.initViewPager(questionList);

        requestPermission();
    }

    //开始计时,到时前自动保存当前答案
    private void startCountTime(){
        mDisposableCount =Flowable.interval(0,1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        mRootView.updateCountTime(aLong);
                        timeLast++;
                    }
                });
    }



    //保存当前页的答案到本地,时间没到都可以保存
    private void savePageAnswer(int position){
        Answer answer = mRootView.getAnswerFromQuestion(position);
        QuestionAnswerHolder.getInstance().saveAnswerByIndex(position,answer);
    }

    //上传某道题的答案
    private Observable<BaseResponse> submitQuestion(int index){
        Answer answer = QuestionAnswerHolder.getInstance().getAnswerByIndex(index);
        return mModel.submitQuestion(mStdHwId,answer.getQuestionId()
                ,mUUID,mJobType,answer,mSchoolId)
                .map(new Function<BaseResponse, BaseResponse>() {
                    @Override
                    public BaseResponse apply(BaseResponse baseResponse) throws Exception {
                        if(baseResponse.isSuccess()){
                            QuestionAnswerHolder.getInstance().saveQuestionAnswertoFile(mApplication,index);
                        }
                        return baseResponse;
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
                ;
    }


    private void requestPermission(){
        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.launchCamera(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
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
     * 点击退出答题，把当前页面答题暂存进文件
     */
    public void clickQuit() {
        savePageAnswer(mRootView.getCurrentQuestionIndex());
        Observable.just(mRootView.getCurrentQuestionIndex())
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer) throws Exception {
                        QuestionAnswerHolder.getInstance().saveQuestionAnswertoFile(mApplication,integer);
                        return Integer.valueOf(1);
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
                .subscribe(new ErrorHandleSubscriber<Integer>(mErrorHandler) {
                    @Override
                    public void onNext(Integer response) {
                        mRootView.killMyself();
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

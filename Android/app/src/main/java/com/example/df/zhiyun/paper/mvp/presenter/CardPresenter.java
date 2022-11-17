package com.example.df.zhiyun.paper.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.NonNull;

import com.example.df.zhiyun.app.EventBusTags;
import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.example.df.zhiyun.mvp.model.adapterEntity.CardMultipleItem;
import com.example.df.zhiyun.mvp.model.entity.Answer;
import com.example.df.zhiyun.mvp.model.entity.AnswerSet;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.PaperAnswerSet;
import com.example.df.zhiyun.paper.mvp.contract.CardContract;
import com.example.df.zhiyun.paper.mvp.ui.activity.SetHomeworkActivity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Flowable;
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
 * Created by MVPArmsTemplate on 08/09/2019 12:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class CardPresenter extends BasePresenter<CardContract.Model, CardContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    @Named(CardContract.View.KEY_TIME_REMAIN)
    Long timeRemain;  //剩余的考试时间

    @Inject
    @Named(CardContract.View.KEY_SUBJECTID)
    Integer mSubjectId;

    @Inject
    @Named(CardContract.View.KEY_JOB_TYPE)
    Integer mJobType;


    @Inject
    @Named(CardContract.View.KEY_STD_HW_ID)
    String mStdHwId;
    @Inject
    @Named(CardContract.View.KEY_HW_ID)
    String mHwId;
    @Inject
    @Named(CardContract.View.KEY_UUID)
    String mUUID;
    @Inject
    @Named(CardContract.View.KEY_SCHOOLID)
    String mSchoolId;


    Disposable mDisposableCount;   //

    @Inject
    public CardPresenter(CardContract.Model model, CardContract.View rootView) {
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
//        requestData();
//        startCountTime();
    }

    //开始计时
    public void startCountTime(){
        mDisposableCount = Flowable.interval(0,1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        timeRemain--;
                        if(timeRemain < 0){
                            mDisposableCount.dispose();
                            mRootView.showTimeoutDialog();
                        }
                    }
                });
    }

    public void requestData() {
        mModel.answerSet(mStdHwId,mSubjectId,mSchoolId,mJobType)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<PaperAnswerSet>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<PaperAnswerSet> response) {
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

    private void processData(PaperAnswerSet data){
        List<CardMultipleItem> cardMultipleItemList = new ArrayList<>();
        List<AnswerSet> answerSets = data.getList();
        if(answerSets != null){
            CardMultipleItem tempCardMultipleItem;
            int answerIndex = 0;
            for(AnswerSet answerSet:answerSets){
                tempCardMultipleItem = new CardMultipleItem(CardMultipleItem.TYPE_TITLE,null,"0",answerSet.getName());
                cardMultipleItemList.add(tempCardMultipleItem);
                List<Answer> listanswer = answerSet.getList();
                if(listanswer != null){
                    for(int i=0;i<listanswer.size();i++){
                        tempCardMultipleItem = new CardMultipleItem(CardMultipleItem.TYPE_INDEX,
                                QuestionAnswerHolder.getInstance().getAnswerByIndex(answerIndex),""+answerIndex,""+listanswer.get(i).getQuestionNum());
                        cardMultipleItemList.add(tempCardMultipleItem);
                        answerIndex++;
                    }
                }
            }
        }
        mRootView.bindData(data.getTitle(),cardMultipleItemList);
    }


    //往服务器上传答卷
    public void submitAnswer() {
        mModel.submitHomeWork(mStdHwId,mHwId,mUUID,mJobType,mSubjectId,mSchoolId)
                .map(new Function<BaseResponse, BaseResponse>() {
                    @Override
                    public BaseResponse apply(BaseResponse baseResponse) throws Exception {
                        QuestionAnswerHolder.getInstance().removeAnswers(mApplication);
                        return baseResponse;
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
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        if(response.isSuccess()){
                            mRootView.showMessage("提交成功！");
                            EventBus.getDefault().post(new Integer(1), EventBusTags.UPDATE_HW_LIST);
                            mRootView.killMyself();
                            mAppManager.killActivity(SetHomeworkActivity.class);
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

    public void clickSubmit() {
        mRootView.showSubmitDialog();
    }
}

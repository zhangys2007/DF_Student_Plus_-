package com.example.df.zhiyun.correct.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

//import com.example.df.zhiyun.app.CorrectionStorageManager;
import com.example.df.zhiyun.app.EventBusTags;
import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.example.df.zhiyun.correct.mvp.contract.CorrectCardContract;
import com.example.df.zhiyun.mvp.model.adapterEntity.CardMultipleItem;
import com.example.df.zhiyun.mvp.model.adapterEntity.CorrectCardMultipleItem;
import com.example.df.zhiyun.mvp.model.entity.Answer;
import com.example.df.zhiyun.mvp.model.entity.AnswerSet;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.HomeworkSet;
import com.example.df.zhiyun.mvp.model.entity.PaperAnswerSet;
import com.example.df.zhiyun.correct.mvp.ui.activity.CorrectHWActivity;
import com.example.df.zhiyun.correct.mvp.ui.activity.CorrectSomeOneActivity;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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
 * Created by MVPArmsTemplate on 08/09/2019 12:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class CorrectCardPresenter extends BasePresenter<CorrectCardContract.Model, CorrectCardContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public CorrectCardPresenter(CorrectCardContract.Model model, CorrectCardContract.View rootView) {
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

    public void requestData() {
        HomeworkSet homeworkSet = QuestionAnswerHolder.getInstance().getHomework();
        if(homeworkSet == null){
            return;
        }
        mModel.homeworkSet(homeworkSet.getStudentHomeWorkId(),homeworkSet.getSubjectId())
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
        List<CorrectCardMultipleItem> cardMultipleItemList = new ArrayList<>();
        List<AnswerSet> answerSets = data.getList();
        if(answerSets != null){
            CorrectCardMultipleItem tempCardMultipleItem;
            int answerIndex = 0;
            for(AnswerSet answerSet:answerSets){
                tempCardMultipleItem = new CorrectCardMultipleItem(CardMultipleItem.TYPE_TITLE,null,"0",answerSet.getName());
                cardMultipleItemList.add(tempCardMultipleItem);
                List<Answer> listanswer = answerSet.getList();
                if(listanswer != null){
                    for(int i=0;i<listanswer.size();i++){
                        if(QuestionAnswerHolder.getInstance().getHomework() != null &&
                                QuestionAnswerHolder.getInstance().getHomework().getList() != null &&
                                QuestionAnswerHolder.getInstance().getHomework().getList().size() > answerIndex){
                            tempCardMultipleItem = new CorrectCardMultipleItem(CardMultipleItem.TYPE_INDEX,
                                    QuestionAnswerHolder.getInstance().getHomework().getList().get(answerIndex),""+answerIndex,""+listanswer.get(i).getQuestionNum());
                            cardMultipleItemList.add(tempCardMultipleItem);
                            answerIndex++;
                        }
                    }
                }
            }
        }
        mRootView.bindData(data.getTitle(),cardMultipleItemList);
    }


    //提交批改
    public void submitAnswer() {
        HomeworkSet homeworkSet = QuestionAnswerHolder.getInstance().getHomework();
        if(homeworkSet == null){
            return;
        }

        mModel.submitCorrections(homeworkSet.getStudentHomeWorkId(),homeworkSet.getSubjectId())
                .map(new Function<BaseResponse, BaseResponse>() {
                    @Override
                    public BaseResponse apply(BaseResponse baseResponse) throws Exception {
//                        CorrectionStorageManager.removeCorrection(mApplication,
//                                QuestionAnswerHolder.getInstance().getHomework().getStudentHomeWorkId());
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        if(response.isSuccess()){
                            mRootView.showMessage("提交成功！");
                            EventBus.getDefault().post(new Integer(1), EventBusTags.UPDATE_HW_LIST);
                            mRootView.killMyself();
                            mAppManager.killActivity(CorrectHWActivity.class);
                            mAppManager.killActivity(CorrectSomeOneActivity.class);
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

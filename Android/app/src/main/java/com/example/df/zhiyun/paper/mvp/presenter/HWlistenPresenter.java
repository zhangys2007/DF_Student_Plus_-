package com.example.df.zhiyun.paper.mvp.presenter;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.example.df.zhiyun.paper.mvp.contract.SetHomeworkContract;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;
import com.example.df.zhiyun.paper.mvp.contract.HWlistenContract;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import com.example.df.zhiyun.mvp.model.entity.Answer;

import io.reactivex.schedulers.Schedulers;
import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.ProgressManager;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/19/2019 11:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HWlistenPresenter extends BasePresenter<HWlistenContract.Model, HWlistenContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;
    Disposable mDisposableCount;
    File mAudioFile;

    public File getAudioFile(){
        return mAudioFile;
    }

    public boolean isAudioFileReady(){
        return mAudioFile != null && mAudioFile.exists();
    }

    @Inject
    public HWlistenPresenter(HWlistenContract.Model model, HWlistenContract.View rootView) {
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

    Map<Integer, Answer> mAnswers = new HashMap<>();  //存储所有的作答

    public void onSubPageSwitch(int oldIndex,int newIndex){
        Answer answer = mRootView.getAnswerFromQuestion(oldIndex);
        mAnswers.put(oldIndex,answer);
        mRootView.initQuestionWithAnswer(newIndex,mAnswers.get(newIndex));
    }

    //保存当前页的答案,时间没到都可以保存
    public void saveCurrentPageAnswer(){
        int position = mRootView.getCurrentQuestionIndex();
        Answer answer = mRootView.getAnswerFromQuestion(position);
        mAnswers.put(position,answer);
    }

    public void initeWhiteNewAnswer(List<Answer> answers){
        if(answers != null){
            mAnswers.clear();
            for(int i=0;i<answers.size();i++){
                mAnswers.put(i,answers.get(i));
            }
        }
    }

    //hasEmpty  是否放在空的占位
    public ArrayList<Answer> getAnswerList(boolean hasEmpty, Question question){
        return QuestionAnswerHolder.makeAnswerListFromAnswerMap(hasEmpty,mAnswers,question.getSubQuestion());
    }

    public void startCountTime(){
        mDisposableCount = Flowable.interval(0,1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, FragmentEvent.DESTROY))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        mRootView.updateTime();
                    }
                });
    }

    public void stopCountTime(){
        if(mDisposableCount != null && !mDisposableCount.isDisposed()){
            mDisposableCount.dispose();
        }
    }

    /**
     * 下载音频文件
     * @param
     */
    public void downloadAudioFile(String uuid,ProgressListener listener){
        if(TextUtils.isEmpty(getBaseUrl()) || TextUtils.isEmpty(uuid)){
            return;
        }

        if(mAudioFile == null){
            mAudioFile = makeTargetFile(uuid);
        }

        if(mAudioFile.exists()){
            mRootView.playAudioFile(mAudioFile);
        }else{
            ProgressManager.getInstance().addResponseListener(makeDownLoadUrl(uuid), listener);
            mModel.downloadFile(makeDownLoadUrl(uuid),mAudioFile)
                    .subscribeOn(Schedulers.io())
                    .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                    .doOnSubscribe(disposable -> {
                        mRootView.enablePlayButton(false);  //下载中不让多次点击下载
                        mRootView.showLoading();//转菊花
                    }).subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doFinally(() -> {
                        mRootView.enablePlayButton(true);
                        mRootView.hideLoading();//隐藏下拉刷新的进度条
                    })
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                    .subscribe(new ErrorHandleSubscriber<File>(mErrorHandler) {
                        @Override
                        public void onNext(File file) {
                            mRootView.playAudioFile(mAudioFile);
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


    private String getBaseUrl(){
        Fragment fragment = (Fragment)mRootView;
        SetHomeworkContract.View contractView = (SetHomeworkContract.View)fragment.getActivity();
        return contractView.getListenerBaseUrl();
    }

    private String makeDownLoadUrl(String uuid){
        return getBaseUrl()+ "/" +uuid;
    }

    private File makeTargetFile(String uuid){
        return new File(DataHelper.getCacheFile(mApplication), uuid + ".mp3");
    }
}

package com.example.df.zhiyun.paper.mvp.presenter;

import android.app.Application;
import android.net.Uri;
import android.text.TextUtils;

import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.example.df.zhiyun.mvp.model.entity.Question;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.df.zhiyun.mvp.model.entity.Answer;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import com.example.df.zhiyun.paper.mvp.contract.HWsepContract;
import com.jess.arms.utils.DataHelper;
import com.jess.arms.utils.RxLifecycleUtils;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/14/2019 16:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HWsepPresenter extends BasePresenter<HWsepContract.Model, HWsepContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

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


    @Inject
    public HWsepPresenter(HWsepContract.Model model, HWsepContract.View rootView) {
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

    private File makeTargetFile(String uuid){
        return new File(DataHelper.getCacheFile(mApplication), uuid);
    }

    public void getPdfFile(final String path){
        if(TextUtils.isEmpty(path)){
            return;
        }
        Observable.just(path.replace("previewFile","downLoadFile"))
                .flatMap(new Function<String, ObservableSource<File>>() {
                    @Override
                    public ObservableSource<File> apply(String s) throws Exception {
                        Uri uri = Uri.parse(s);
                        String uuid = uri.getQueryParameter("uuid");
                        if(!TextUtils.isEmpty(uuid)){
                            File file = makeTargetFile(uuid);
                            if(file.exists()){
                                return Observable.just(file);
                            }else{
                                return mModel.downloadFile(s,file);
                            }
                        }else{
                            return mModel.downloadFile(s,makeTargetFile(UUID.randomUUID().toString()));
                        }
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
                .subscribe(new ErrorHandleSubscriber<File>(mErrorHandler) {
                    @Override
                    public void onNext(File file) {
                        mRootView.showPdf(file);
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

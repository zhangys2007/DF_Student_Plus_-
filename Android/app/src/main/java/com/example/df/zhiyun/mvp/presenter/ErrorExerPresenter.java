package com.example.df.zhiyun.mvp.presenter;

import android.app.Application;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.example.df.zhiyun.mvp.ui.widget.PaginatorHelper;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;
import com.example.df.zhiyun.mvp.contract.ErrorExerContract;
import com.example.df.zhiyun.mvp.model.adapterEntity.QuestionMultipleItem;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.Question;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.example.df.zhiyun.app.utils.TimeUtils;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description:主页面上的错题集子页面
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 13:35
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class ErrorExerPresenter extends BasePresenter<ErrorExerContract.Model, ErrorExerContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    BaseMultiItemQuickAdapter mAdapter;

    private PaginatorHelper paginatorHelper = new PaginatorHelper();
    private int subjectId;
    private String beginTime = "";
    private String endTime = "";

    @Inject
    public ErrorExerPresenter(ErrorExerContract.Model model, ErrorExerContract.View rootView) {
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

    public void changeSubject(int id){
        if(subjectId != id){
            subjectId = id;
        }
    }

    public int getSubjectId(){
        return subjectId;
    }

    public void changeTime(String begin, String end) {
        if(begin != beginTime || end != endTime){
            beginTime = begin;
            endTime = end;
        }

    }

    public void requesData(boolean pullToRefresh) {
        mModel.getQuestion(paginatorHelper.getPageIndex(pullToRefresh),subjectId,beginTime,endTime)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<Question>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<Question>> response) {
                        if(response.isSuccess()){
                            List<Question> tempDatas = response.getData();
                            paginatorHelper.onDataArrive(mAdapter,pullToRefresh,transformData(tempDatas),null);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mAdapter.loadMoreFail();
                    }
                });
    }

    //当头，或者两个数据不是同一天的时候加个头
    private List<QuestionMultipleItem> transformData(List<Question> data){
        List<QuestionMultipleItem> items = new ArrayList<>();
        if(data == null){
            return items;
        }

        QuestionMultipleItem tempItem;
        for(int i=0;i<data.size();i++){

            if(i == 0){
                List<QuestionMultipleItem> oldQuestions = mAdapter.getData();
                if(oldQuestions == null || oldQuestions.size() == 0){
                    tempItem = new QuestionMultipleItem(Api.QUESTION_HEAD,data.get(0));
                    items.add(tempItem);
                }else{
                    String oldTime = oldQuestions.get(oldQuestions.size()-1).getData().getTime();
                    if(!TimeUtils.isSameDate(oldTime,data.get(0).getTime())){
                        tempItem = new QuestionMultipleItem(Api.QUESTION_HEAD,data.get(0));
                        items.add(tempItem);
                    }
                }
            }else if(!TimeUtils.isSameDate(data.get(i).getTime(),data.get(i-1).getTime()) ){
                tempItem = new QuestionMultipleItem(Api.QUESTION_HEAD,data.get(i));
                items.add(tempItem);
            }

            tempItem = new QuestionMultipleItem(Api.QUESTION_SELECT, data.get(i));
            items.add(tempItem);
        }
        return items;
    }

}

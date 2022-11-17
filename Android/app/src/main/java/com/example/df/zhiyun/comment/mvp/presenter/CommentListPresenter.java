package com.example.df.zhiyun.comment.mvp.presenter;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.comment.mvp.ui.activity.CommentAnalyDetailActivity;
import com.example.df.zhiyun.mvp.ui.widget.PaginatorHelper;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;
import com.example.df.zhiyun.comment.mvp.contract.CommentListContract;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

import com.example.df.zhiyun.educate.mvp.model.entity.BelongClass;
import com.example.df.zhiyun.mvp.model.entity.CommentItem;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/07/2019 10:20
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class CommentListPresenter extends BasePresenter<CommentListContract.Model, CommentListContract.View> {
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

    private String mBaseReportUrl = "";
    private String mBaseSummaryUrl = "";
    private String classId = "";
    private String className = "";
    private int subjectId;
    private String subjectName;
    private PaginatorHelper paginatorHelper = new PaginatorHelper();
    private String beginTime = "";
    private String endTime = "";

    public boolean isOtherInfoReady(){
        return !TextUtils.isEmpty(classId) && !TextUtils.isEmpty(mBaseReportUrl) && !TextUtils.isEmpty(mBaseSummaryUrl);
    }

    @Inject
    public CommentListPresenter(CommentListContract.Model model, CommentListContract.View rootView) {
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

    public void changeClass(String name,String id){
        classId = id;
        className = name;
    }

    public void changeSubject(int id,String name){
        if(subjectId != id){
            subjectId = id;
            subjectName = name;
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

    public void requestComment(boolean pullToRefresh) {
        mModel.getCommentList(paginatorHelper.getPageIndex(pullToRefresh),classId,subjectId,beginTime,endTime)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<CommentItem>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<CommentItem>> response) {
                        if(response.isSuccess()){
                            List<CommentItem> tempDatas = response.getData();
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

    /**
     * 获取班级和讲评详情信息
     */
    public void getOtherData(){
        Observable.zip(mModel.belongClass(), mModel.commentingBaseUrl("tikuReport"), mModel.commentingBaseUrl("tikuSummary"),
        new Function3<BaseResponse<List<BelongClass>>, BaseResponse<String>, BaseResponse<String>, BaseResponse<Map<String,Object>>>() {
            @Override
            public BaseResponse<Map<String, Object>> apply(BaseResponse<List<BelongClass>> listBaseResponse, BaseResponse<String> stringBaseResponse, BaseResponse<String> stringBaseResponse2) throws Exception {
                Map<String,Object> result = new HashMap<>();
                result.put("belongClass",listBaseResponse.getData());
                result.put("tikuReport",stringBaseResponse.getData());
                result.put("tikuSummary",stringBaseResponse2.getData());
                BaseResponse<Map<String, Object>> baseResponse = new BaseResponse<>();
                baseResponse.setData(result);

                if(!listBaseResponse.isSuccess()){
                    baseResponse.setCode(listBaseResponse.getCode());
                    baseResponse.setMessage(listBaseResponse.getMessage());
                }else if(!stringBaseResponse.isSuccess()){
                    baseResponse.setCode(stringBaseResponse.getCode());
                    baseResponse.setMessage(stringBaseResponse.getMessage());
                }else if(!stringBaseResponse2.isSuccess()){
                    baseResponse.setCode(stringBaseResponse2.getCode());
                    baseResponse.setMessage(stringBaseResponse2.getMessage());
                }else{
                    baseResponse.setCode(Api.success);
                    baseResponse.setMessage("");
                }

                return baseResponse;
            }
        }
    )
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
        .subscribe(new ErrorHandleSubscriber<BaseResponse<Map<String, Object>>>(mErrorHandler) {
            @Override
            public void onNext(BaseResponse<Map<String, Object>> response) {
                if(response.isSuccess()){
                    List<BelongClass> classList = (List<BelongClass>)(response.getData().get("belongClass"));
                    if(classList != null && classList.size() > 0){
                        mRootView.bindClassInfo(classList.get(0));
                        classId = classList.get(0).getClassId();
                        className = classList.get(0).getClassName();
                        requestComment(true);
                    }
                    mBaseReportUrl = (String)response.getData().get("tikuReport");
                    mBaseSummaryUrl = (String)response.getData().get("tikuSummary");
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

    /**
     * 讲评报告
     * @param commentItem
     */
    public void previewDetail(CommentItem commentItem) {
        if(!TextUtils.isEmpty(commentItem.getId()) && !TextUtils.isEmpty(mBaseReportUrl) && !TextUtils.isEmpty(classId)
                && !TextUtils.isEmpty(className) && !TextUtils.isEmpty(commentItem.getHomeworkId())){
            Context context = ((Fragment)mRootView).getContext();
            CommentAnalyDetailActivity.launcheDetailActivity(context,commentItem.getStuHomeworkName()
                    ,mBaseReportUrl,commentItem.getSubject(),commentItem.getId(),commentItem.getHomeworkId(),classId,className);
        }
    }

    /**
     * 讲评概述
     * @param commentItem
     */
    public void previewSummary(CommentItem commentItem) {
        if(!TextUtils.isEmpty(commentItem.getId()) && !TextUtils.isEmpty(mBaseSummaryUrl) && !TextUtils.isEmpty(classId)
                && !TextUtils.isEmpty(className) && !TextUtils.isEmpty(commentItem.getHomeworkId())){
            Context context = ((Fragment)mRootView).getContext();
            CommentAnalyDetailActivity.launcheDetailActivity(context,commentItem.getStuHomeworkName()
                    ,mBaseSummaryUrl,commentItem.getSubject(),commentItem.getId(),commentItem.getHomeworkId(),classId,className);
        }
    }
}

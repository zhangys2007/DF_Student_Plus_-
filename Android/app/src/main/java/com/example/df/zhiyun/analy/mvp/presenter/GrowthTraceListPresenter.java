package com.example.df.zhiyun.analy.mvp.presenter;

import android.app.Application;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.utils.FilterGradeClassSubjectHelper;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.FilterClass;
import com.example.df.zhiyun.mvp.model.entity.FilterGrade;
import com.example.df.zhiyun.mvp.model.entity.FilterSubject;
import com.example.df.zhiyun.mvp.model.entity.GrowthTraceItem;
import com.example.df.zhiyun.mvp.ui.widget.PaginatorHelper;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

import com.example.df.zhiyun.analy.mvp.contract.GrowthTraceListContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/23/2019 10:07
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class GrowthTraceListPresenter extends BasePresenter<GrowthTraceListContract.Model, GrowthTraceListContract.View> {
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

    private List<FilterGrade> mFilterData;
    private String mClassId= "";
    private String mSubjectId = "23";

    private PaginatorHelper paginatorHelper = new PaginatorHelper(0);

    public String getSubjectID(){
        return mSubjectId;
    }

    @Inject
    public GrowthTraceListPresenter(GrowthTraceListContract.Model model, GrowthTraceListContract.View rootView) {
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

    public boolean isFilterDataReady(){
        return mFilterData != null;
    }

    public void filterItemChange(int op1,int op2,int op3){
        if(mFilterData != null && mFilterData.size() > op1){
            FilterGrade grade = mFilterData.get(op1);
            if(grade.getClassList() != null && grade.getClassList().size() > op2){
                FilterClass classData = grade.getClassList().get(op2);
                mClassId = classData.getClassID();
                if(classData.getSubject() != null && classData.getSubject().size() > op3){
                    FilterSubject subject = classData.getSubject().get(op3);
                    mSubjectId = subject.getSubjectId();
                }else{
                    mSubjectId = "23";
                }
            }else{
                mClassId = "";
                mSubjectId = "23";
            }
        }
    }


    public void findFilterData(){
        mModel.findAnalysisDict()
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<FilterGrade>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<FilterGrade>> response) {
                        if(response.isSuccess()){
                            processFilterData(response.getData());
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

    /**
     *
     * @param data
     */
    private void processFilterData(List<FilterGrade> data){
        if(data == null){
            return;
        }

        List<String> op1 = new ArrayList<>();
        List<List<String>> op2 = new ArrayList<>();
        List<List<List<String>>> op3 = new ArrayList<>();


        mFilterData = data;
        if(mFilterData.size() > 0){
            FilterGrade filterGrade = mFilterData.get(0);
            if(filterGrade.getClassList() != null && filterGrade.getClassList().size() > 0){
                FilterClass filterClass = filterGrade.getClassList().get(0);
                mClassId = filterClass.getClassID();
                if(filterClass.getSubject() != null && filterClass.getSubject().size() > 0){
                    FilterSubject filterSubject = filterClass.getSubject().get(0);
                    mSubjectId = filterSubject.getSubjectId();
                }
            }
        }

        FilterGradeClassSubjectHelper.parseLevel1(data,op1,op2,op3);
        mRootView.initPickViewData(op1,op2,op3);
        filterItemChange(0,0,0);
        requestData(true);
    }


    public void requestData(boolean pullToRefresh) {
        mModel.growthTraceItems(paginatorHelper.getPageIndex(pullToRefresh),mSubjectId,mClassId)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<GrowthTraceItem>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<GrowthTraceItem>> response) {
                        if(response.isSuccess()){
                            List<GrowthTraceItem> tempDatas = response.getData();
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
}

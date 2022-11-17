package com.example.df.zhiyun.plan.mvp.presenter;

import android.app.Application;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.AnalysisDict;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.PlanUsage;
import com.example.df.zhiyun.mvp.model.entity.Tag;
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

import com.example.df.zhiyun.plan.mvp.contract.PlanUsingContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/30/2019 15:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class PlanUsingPresenter extends BasePresenter<PlanUsingContract.Model, PlanUsingContract.View> {
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
    private PaginatorHelper paginatorHelper = new PaginatorHelper();

    private List<Tag> mFilterData;
    private String mGradeClassId= "";
    private String mUserName = "";
    private int mIsClass = 0; //0-年级数组，1-班级数组;
    private int mCountType = Api.USAGE_COUNT_TYPE_DATE;

    public void changeSearch(String key){
        mUserName = key;
    }

    public boolean isClass(){
        return mIsClass == 1;
    }

    @Inject
    public PlanUsingPresenter(PlanUsingContract.Model model, PlanUsingContract.View rootView) {
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

    public void filterItemChange(int op1,int op2){
        if(mFilterData != null && mFilterData.size() > op1){
            mGradeClassId = mFilterData.get(op1).getValue();
            mCountType = op2;
        }
    }

    public void countTypeChange(int op1){
        mCountType = op1;
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<AnalysisDict>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<AnalysisDict> response) {
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
     * @param
     */
    private void processFilterData(AnalysisDict data){
        if(data == null){
            return;
        }

        mIsClass = data.getIsClass();
        mFilterData = data.getClassList();


        mRootView.initPickViewData(mFilterData,mIsClass==1);
        filterItemChange(0,0);
        requestData(true);
    }


    public void requestData(boolean pullToRefresh) {
        Map<String,Object> params = new HashMap<>();
        if(mIsClass == 1){   //传个非空值
            params.put("classId","1");
            params.put("gradeId","");
        }else{
            params.put("classId","");
            params.put("gradeId",mGradeClassId);
        }
        mModel.planUsage(mUserName,params,mCountType,paginatorHelper.getPageIndex(pullToRefresh))
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<PlanUsage>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<PlanUsage>> response) {
                        if(response.isSuccess()){
                            List<PlanUsage> tempDatas = response.getData();
                            paginatorHelper.onDataArrive(mAdapter,pullToRefresh,tempDatas,null);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                });
    }
}

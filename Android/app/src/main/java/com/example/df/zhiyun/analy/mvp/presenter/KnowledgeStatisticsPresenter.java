package com.example.df.zhiyun.analy.mvp.presenter;

import android.app.Application;

import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.ComSubjCla;
import com.example.df.zhiyun.mvp.model.entity.KnowledgePoint;
import com.github.mikephil.charting.data.Entry;
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

import com.example.df.zhiyun.analy.mvp.contract.KnowledgeStatisticsContract;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 10:40
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class KnowledgeStatisticsPresenter extends BasePresenter<KnowledgeStatisticsContract.Model, KnowledgeStatisticsContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private String mClassId = "";
    private String mSubjId = "";

    public void changeClass(String id){
        mClassId = id;
    }

    public void changeSubject(String id){
        mSubjId = id;
    }

    @Inject
    public KnowledgeStatisticsPresenter(KnowledgeStatisticsContract.Model model, KnowledgeStatisticsContract.View rootView) {
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


    public void findFilterData(){
        mModel.findClassSubjectByStudent()
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<ComSubjCla>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<ComSubjCla> response) {
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

    private void processFilterData(ComSubjCla data){
        if(data.getClassList() != null && data.getClassList().size() > 0){
            mClassId = data.getClassList().get(0).getValue();
        }

        if(data.getSubjectList() != null && data.getSubjectList().size() > 0){
            mSubjId = data.getSubjectList().get(0).getValue();
        }

        mRootView.initFilterData(data);
        getKnowledgePoints();
    }


    public void getKnowledgePoints(){
        mModel.getKnowledgePoints(mClassId,mSubjId)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<KnowledgePoint>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<KnowledgePoint>> response) {
                        if(response.isSuccess()){
                            processChartData(response.getData());
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

    private void processChartData(List<KnowledgePoint> list){
        ArrayList<Entry> values = new ArrayList<>();
        if(list == null || list.size() == 0){
            mRootView.setData(values);
            return;
        }

        int i=0;
        for(KnowledgePoint point:list){
            values.add(new Entry(i, point.getScoreRate(), point));
            i++;
        }

        mRootView.setData(values);
    }
}

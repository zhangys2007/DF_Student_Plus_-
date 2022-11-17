package com.example.df.zhiyun.my.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.example.df.zhiyun.app.EventBusTags;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.my.mvp.contract.SignedContract;
import com.example.df.zhiyun.mvp.model.adapterEntity.SignedInfoMultipleItem;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.SignedInfo;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.example.df.zhiyun.R;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/31/2019 10:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SignedPresenter extends BasePresenter<SignedContract.Model, SignedContract.View> {
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

    private int mMoonStartIndex;
    private SignedInfo mSignedInfo = new SignedInfo();

    @Inject
    public SignedPresenter(SignedContract.Model model, SignedContract.View rootView) {
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
//        getSignedList();
    }

    public void getSignedList(){
        mModel.getSignedData()
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<SignedInfo>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<SignedInfo> response) {
                        if(response.isSuccess()){
                            processData(response.getData());
                        }else{
                            mRootView.showMessage(response.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });
    }

    /**
     * 添加星期，填充空白项，填入这个月的日期
     * @param response
     */
    private void processData(SignedInfo response){
        String[] titles = {"日","一","二","三","四","五","六"};
        List<SignedInfoMultipleItem> items = new ArrayList<>();
        SignedInfoMultipleItem temItem;
        for(int index=0;index< titles.length ;index++){
            temItem = new SignedInfoMultipleItem(SignedInfoMultipleItem.TYPE_TITLE, titles[index],0);
            items.add(temItem);
        }


        long currentTime = Calendar.getInstance().getTimeInMillis();
        int daysInMonth = TimeUtils.getDaysInMonth(currentTime);  //这个月总天数
        long firstDay = TimeUtils.getFirstOfMonth(currentTime); //这月第一天
        int firstDayInWeek = TimeUtils.getWeek(firstDay);  //这月第一天是星期几
        for(int index=0;index< firstDayInWeek;index++){
            temItem = new SignedInfoMultipleItem(SignedInfoMultipleItem.TYPE_EMPTY, " ",0);
            items.add(temItem);
        }

        mMoonStartIndex = items.size();  //标记这月第一天在表了的开始位置


        for(int index=1;index<= daysInMonth ;index++){
            temItem = new SignedInfoMultipleItem(SignedInfoMultipleItem.TYPE_DATE, ""+index,0);
            items.add(temItem);
        }

        mSignedInfo = response;
        List<String> signedDays = response.getlist();   //标记已签到的天
        if(signedDays != null){
            for(String sd : signedDays){
                try {
                    long lsd = Long.parseLong(sd);
                    items.get(getIndexInTable(lsd)).setTime(lsd);
                }catch (NumberFormatException e){

                }
            }
        }

        mAdapter.setNewData(items);
        mRootView.updateSignedCount(mSignedInfo.getSignCount(),mSignedInfo.getMonthCount());
    }

    /***
     * 签到
     */
    public void signed(){
        long timenow = Calendar.getInstance().getTimeInMillis();
        List<SignedInfoMultipleItem> datas  = mAdapter.getData();
        long itemTime = datas.get(getIndexInTable(timenow)).getTime();
        if(TimeUtils.isSameDay(timenow,itemTime)){
            mRootView.showMessage(mApplication.getResources().getString(R.string.resigned_error));
            return;
        }

        mModel.signed()
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
                            processSigned();
                        }else{
                            mRootView.showMessage(response.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });
    }

    /**
     * 处理签到，
     */
    private void processSigned(){
        long timenow = Calendar.getInstance().getTimeInMillis();
        List<SignedInfoMultipleItem> datas  = mAdapter.getData();
        datas.get(getIndexInTable(timenow)).setTime(timenow);
        mAdapter.notifyItemChanged(getIndexInTable(timenow));

        mSignedInfo.setMonthCount(mSignedInfo.getMonthCount()+1);
        mSignedInfo.setSignCount(mSignedInfo.getSignCount()+1);
        mRootView.updateSignedCount(mSignedInfo.getSignCount(),mSignedInfo.getMonthCount());

        EventBus.getDefault().post(new Integer(1),EventBusTags.REQUEST_PERSON_CENTER);
    }


    /**
     * 返回这天在整个adapter中是第几项
     * @param time
     * @return
     */
    private int getIndexInTable(long time){
        int moonIndex = Integer.parseInt(TimeUtils.getD(time))-1;
        return mMoonStartIndex+moonIndex;
    }
}

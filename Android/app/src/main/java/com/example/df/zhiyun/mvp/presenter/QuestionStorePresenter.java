package com.example.df.zhiyun.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.example.df.zhiyun.mvp.ui.widget.PaginatorHelper;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;
import com.example.df.zhiyun.mvp.contract.QuestionStoreContract;
import com.example.df.zhiyun.mvp.model.adapterEntity.QuestionMultipleItem;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.mvp.model.entity.Subject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.example.df.zhiyun.R;

import com.example.df.zhiyun.mvp.model.entity.StoreSet;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/26/2019 15:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class QuestionStorePresenter extends BasePresenter<QuestionStoreContract.Model, QuestionStoreContract.View> {
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

    private int subjectId;
    private PaginatorHelper paginatorHelper = new PaginatorHelper();
    private String beginTime = "";
    private String endTime = "";
    private List<Subject> subjectList = new ArrayList<>();

    private PaginatorHelper.IPaginator iPaginator = new PaginatorHelper.IPaginator(){
        @Override
        public void setHasMoreDataToLoad(boolean hasMoreDataToLoad) { }

        @Override
        public void setEmpty() {
            mAdapter.setEmptyView(R.layout.view_empty);
        }
    };

    @Inject
    public QuestionStorePresenter(QuestionStoreContract.Model model, QuestionStoreContract.View rootView) {
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
//        requesData(true);//打开 App 时自动加载列表
//        getSubjectList();
    }

    public void changeSubject(int id){
        if(subjectId != id){
            subjectId = id;
            requesData(true);
        }
    }

    //点击了popup 菜单的第几个按钮
    public void clickPopupMenu(int positon){
        Subject subject = subjectList.get(positon);
        changeSubject(Integer.parseInt(subject.getId()));
    }


    public void requesData(boolean pullToRefresh) {
        mModel.getStore(paginatorHelper.getPageIndex(pullToRefresh),subjectId,beginTime,endTime)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<StoreSet>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<StoreSet> response) {
                        if(response.isSuccess()){
                            StoreSet storeSet = response.getData();
                            if(response.getData() != null){
                                mRootView.setCollectionCount(storeSet.getCount());
                            }

                            List<Question> tempDatas = response.getData().getList();
                            paginatorHelper.onDataArrive(mAdapter,pullToRefresh,transformData(tempDatas),iPaginator);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mAdapter.loadMoreFail();
                    }
                });
    }

    private List<QuestionMultipleItem> transformData(List<Question> data){
        List<QuestionMultipleItem> items = new ArrayList<>();
        QuestionMultipleItem tempItem;
        for(int i=0;i<data.size();i++){
            tempItem = new QuestionMultipleItem(Api.QUESTION_SELECT, data.get(i));
            items.add(tempItem);
        }
        return items;
    }

    public void getSubjectList(){
        mModel.getSubject()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();//转菊花
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<Subject>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<Subject>> response) {
                        if(response.isSuccess()){
//                            mAdapter.setNewData(response.getData());
                            subjectList = response.getData();
                            mRootView.onGetSubjectData(subjectList);
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

    public List<Subject> getSubjectItems(){
        return subjectList;
    }

}

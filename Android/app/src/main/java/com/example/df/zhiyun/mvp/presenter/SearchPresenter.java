package com.example.df.zhiyun.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.example.df.zhiyun.mvp.ui.widget.PaginatorHelper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;
import com.example.df.zhiyun.mvp.contract.SearchContract;
import com.example.df.zhiyun.mvp.model.api.Api;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.mvp.model.adapterEntity.SearchMultipleItem;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.Homework;
import com.example.df.zhiyun.mvp.model.entity.Question;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/20/2019 16:05
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SearchPresenter extends BasePresenter<SearchContract.Model, SearchContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    Gson mGson;
    @Inject
    Integer searchType;
    @Inject
    String mSubjId;
    @Inject
    BaseMultiItemQuickAdapter mAdapter;
    private PaginatorHelper paginatorHelper = new PaginatorHelper();

//    private Type typePaper = new TypeToken<Question>(){}.getType();
//    private Type typeHomework = new TypeToken<Homework>(){}.getType();

    public void changeType(int type){
        searchType = type;
    }

    public void search(boolean pullToRefresh,String searchName) {
        if(TextUtils.isEmpty(searchName)){
            mRootView.showMessage(mApplication.getString(R.string.search_empty_warning));
            mRootView.hideLoading();
            return;
        }

        mModel.search(paginatorHelper.getPageIndex(pullToRefresh),searchName,searchType,mSubjId)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<JsonObject>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<JsonObject>> response) {
                        if(response.isSuccess()){
                            List<SearchMultipleItem> tempDatas = processData(response.getData());

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

    private List<SearchMultipleItem> processData(List<JsonObject> list){
        List<SearchMultipleItem>  items = new ArrayList<>();
        SearchMultipleItem tempItem;
        if(list != null ){
            for(JsonObject str:list){
                if(searchType == Api.SEARCH_HOMEWORK){
                    Homework homework = mGson.fromJson(str,Homework.class);
                    tempItem = new SearchMultipleItem(Api.SEARCH_HOMEWORK,homework);
                    items.add(tempItem);
                }else if(searchType == Api.SEARCH_PAPER){
                    Question question = mGson.fromJson(str,Question.class);
                    tempItem = new SearchMultipleItem(Api.SEARCH_PAPER,question);
                    items.add(tempItem);
                }
            }
        }
        return items;
    }


    @Inject
    public SearchPresenter(SearchContract.Model model, SearchContract.View rootView) {
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
}

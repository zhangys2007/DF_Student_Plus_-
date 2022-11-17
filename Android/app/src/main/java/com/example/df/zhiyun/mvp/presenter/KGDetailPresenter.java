package com.example.df.zhiyun.mvp.presenter;

import android.app.Application;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.mvp.contract.KGDetailContract;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.KnowledgeGrasp;
import com.example.df.zhiyun.mvp.ui.widget.PaginatorHelper;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/22/2019 11:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class KGDetailPresenter extends BasePresenter<KGDetailContract.Model, KGDetailContract.View> {
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

    @Inject
    @Named(KGDetailContract.View.KEY_FZ)
    Integer mFz;
    @Inject
    @Named(KGDetailContract.View.KEY_TYPE)
    Integer mType;
    @Inject
    @Named(KGDetailContract.View.KEY_SCHOOLID)
    Integer mSchoolId;
    @Inject
    @Named(KGDetailContract.View.KEY_GRADEID)
    Integer mGradeId;
    @Inject
    @Named(KGDetailContract.View.KEY_HOMEWORK_ID)
    String mHomeworkId;
    @Inject
    @Named(KGDetailContract.View.KEY_SUBJID)
    Integer mSubjId;

//    private PaginatorHelper paginatorHelper = new PaginatorHelper();

    @Inject
    public KGDetailPresenter(KGDetailContract.Model model, KGDetailContract.View rootView) {
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


    public void requestData(boolean pullToRefresh) {
        mModel.knowledgeGrasp(mHomeworkId,mFz,mType,mGradeId,mSchoolId,mSubjId)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<KnowledgeGrasp>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<KnowledgeGrasp>> response) {
                        if(response.isSuccess()){
                            List<KnowledgeGrasp> tempDatas = response.getData();
                            PaginatorHelper.onAllDataArrive(mAdapter,tempDatas);
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

package com.example.df.zhiyun.analy.mvp.presenter;

import android.app.Application;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.analy.mvp.contract.GradeScorePercentContract;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.GradePerscent;
import com.example.df.zhiyun.mvp.model.entity.GradePerscentItem;
import com.example.df.zhiyun.mvp.ui.widget.PaginatorHelper;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import java.util.ArrayList;
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
public class GradeScorePercentPresenter extends BasePresenter<GradeScorePercentContract.Model, GradeScorePercentContract.View> {
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
    @Named(GradeScorePercentContract.View.KEY_FZ)
    Integer mFz;
    @Inject
    @Named(GradeScorePercentContract.View.KEY_TYPE)
    Integer mType;
    @Inject
    @Named(GradeScorePercentContract.View.KEY_SCHOOLID)
    Integer mSchoolId;
    @Inject
    @Named(GradeScorePercentContract.View.KEY_GRADEID)
    Integer mGradeId;
//    @Inject
//    @Named(GradeScoreSumaryContract.View.KEY_HOMEWORK_ID)
//    String mHomeworkId;
    @Inject
    @Named(GradeScorePercentContract.View.KEY_SUBJID)
    Integer mSubjId;

//    private PaginatorHelper paginatorHelper = new PaginatorHelper();

    @Inject
    public GradeScorePercentPresenter(GradeScorePercentContract.Model model, GradeScorePercentContract.View rootView) {
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
        mModel.knowledgeGrasp(mFz,mType,mGradeId,mSchoolId,mSubjId)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<GradePerscent>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<GradePerscent> response) {
                        if(response.isSuccess()){
                            processData(response.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mAdapter.loadMoreFail();
                    }
                });
    }

    private void processData(GradePerscent data){
        if(data == null){
            return;
        }

        List<GradePerscentItem> list = new ArrayList<>();
        if(data.getTop50List() == null || data.getTop100List() == null){
            return;
        }

        for(int i=0;i< data.getTop50List().size() && i< data.getTop100List().size();i++){
            GradePerscentItem item = new GradePerscentItem();
            list.add(item);

            item.setTop50(data.getTop50List().get(i));
            item.setTop100(data.getTop100List().get(i));
        }

        PaginatorHelper.onAllDataArrive(mAdapter,list);
    }

}

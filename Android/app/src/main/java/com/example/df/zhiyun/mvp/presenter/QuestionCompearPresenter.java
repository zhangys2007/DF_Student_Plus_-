package com.example.df.zhiyun.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.example.df.zhiyun.mvp.contract.QuestionCompearContract;
import com.example.df.zhiyun.mvp.model.adapterEntity.QuestionCompearMultipleItem;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.CompearCla;
import com.example.df.zhiyun.mvp.model.entity.CompearItem;
import com.example.df.zhiyun.mvp.model.entity.CompearQuestion;
import com.example.df.zhiyun.mvp.model.entity.CompearScore;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;

import org.json.JSONObject;

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
 * Created by MVPArmsTemplate on 07/18/2019 13:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class QuestionCompearPresenter extends BasePresenter<QuestionCompearContract.Model, QuestionCompearContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    @Named(QuestionCompearContract.View.KEY_FZ)
    Integer mFz;
    @Inject
    @Named(QuestionCompearContract.View.KEY_TYPE)
    Integer mType;
    @Inject
    @Named(QuestionCompearContract.View.KEY_SCHOOLID)
    Integer mSchoolId;
    @Inject
    @Named(QuestionCompearContract.View.KEY_GRADEID)
    Integer mGradeId;
    @Inject
    @Named(QuestionCompearContract.View.KEY_SUBJID)
    Integer mSubjId;
//    @Inject
//    @Named(QuestionCompearContract.View.KEY_HOMEWORK_ID)
//    String mHomeworkId;

    @Inject
    public QuestionCompearPresenter(QuestionCompearContract.Model model, QuestionCompearContract.View rootView) {
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

    public void getData(){
        mModel.getSubQuestionCompear(mFz,mType,mSubjId,mGradeId,mSchoolId)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<CompearItem>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<CompearItem> response) {
                        if(response.isSuccess()){
                            processData(response.getData());
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

    private void processData(CompearItem data){
        int collumCount = 0;  //
        List<QuestionCompearMultipleItem> list = new ArrayList<>();
        if(data.getGradeQuestionList() == null || data.getClassScoreList() == null || data.getClassList() == null){
            return;
        }

        //添加标题
        list.add(new QuestionCompearMultipleItem(QuestionCompearMultipleItem.TYPE_TITLE_1,"题号"));
        list.add(new QuestionCompearMultipleItem(QuestionCompearMultipleItem.TYPE_TITLE_1,"得分"));
        if(data.getClassList() != null){
            for(CompearCla cla: data.getClassList()){
                list.add(new QuestionCompearMultipleItem(QuestionCompearMultipleItem.TYPE_TITLE_2,cla.getClassName()+"得分率"));
            }
        }
        collumCount = 1+1+ (data.getClassList()==null?0:data.getClassList().size())*2;

        //添加数据项
        int i=0;
        for(CompearQuestion cq:data.getGradeQuestionList()){
            list.add(new QuestionCompearMultipleItem(QuestionCompearMultipleItem.TYPE_VALUE_1,Integer.toString(i+1)));
            list.add(new QuestionCompearMultipleItem(QuestionCompearMultipleItem.TYPE_VALUE_1,
                    String.format("%.1f",cq.getScore())));
            for(CompearCla cla: data.getClassList()){
                QuestionCompearMultipleItem questionCompearMultipleItem = new QuestionCompearMultipleItem(QuestionCompearMultipleItem.TYPE_VALUE_2,
                        "--");
                list.add(questionCompearMultipleItem);
                for(CompearScore cScore: data.getClassScoreList()){
                    if(TextUtils.equals(cScore.getHomeworkId(),cla.getHomeworkId()) &&
                            TextUtils.equals(cScore.getQuestionId(), cq.getQuestionId())){
                        questionCompearMultipleItem.setValue(String.format("%.1f%%",cScore.getScoreRate()));
                    }
                }
            }

            i++;
        }

        mRootView.bindCompearData(list, collumCount);
    }
}

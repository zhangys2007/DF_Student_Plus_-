package com.example.df.zhiyun.educate.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.EventBusTags;
import com.example.df.zhiyun.educate.mvp.contract.PutClsHWContract;
import com.example.df.zhiyun.educate.mvp.model.entity.ClassPutMultipleItem;
import com.example.df.zhiyun.educate.mvp.ui.activity.PutClsHWActivity;
import com.example.df.zhiyun.mvp.model.adapterEntity.StudentsMultipleItem;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.educate.mvp.model.entity.BelongClass;
import com.example.df.zhiyun.mvp.model.entity.PutStudent;
import com.example.df.zhiyun.educate.mvp.ui.adapter.PutHWClassAdapter;
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
import javax.inject.Named;

import com.jess.arms.utils.RxLifecycleUtils;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * ================================================
 * Description: 教师布置作业页面
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 15:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class PutClsHWPresenter extends BasePresenter<PutClsHWContract.Model, PutClsHWContract.View> {
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

    @Inject
    @Named(PutClsHWContract.View.KEY_PAPER_ID)
    String mPaperId;
    @Inject
    @Named(PutClsHWContract.View.KEY_LINK_ID)
    String mLinkId;
    @Inject
    @Named(PutClsHWContract.View.KEY_SYSTEM_ID)
    String mSystemId;
    @Inject
    @Named(PutClsHWContract.View.KEY_TYPE)
    Integer mType;
    @Inject
    @Named(PutClsHWContract.View.KEY_PAPER_NAME)
    String mPaperName;

    String mBeginTime = "";
    String mEndTime = "";
    int isHide = 0;
    int isAnswer = 0;

    public void updateHide(int value){
        isHide = value;
    }

    public void updateAnswer(int value){
        isAnswer = value;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
//        classList();
    }

    public void updateTimeStart(String time){
        this.mBeginTime = time;
    }

    public void updateTimeEnd(String time){
        this.mEndTime = time;
    }

    public void updateTypes(int type){

    }

    //点击了布置
    public void clickPut(){
        if(TextUtils.isEmpty(mBeginTime)){
            mRootView.showMessage("请选择开始时间");
            return;
        }

        if(TextUtils.isEmpty(mEndTime)){
            mRootView.showMessage("请选择结束时间");
            return;
        }

        if(((PutHWClassAdapter)mAdapter).getSelectedParams().size() == 0){
            mRootView.showMessage("请选择至少一项");
            return;
        }
        putHomework();
    }


    public void studentList(String classId, int position){
        mModel.putStudents(mPaperId,mType,classId)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<PutStudent>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<PutStudent>> response) {
                        if(response.isSuccess()){
                            processStudentData(position,response.getData());
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

    private void processStudentData(int position,List<PutStudent> students){
        ClassPutMultipleItem data = (ClassPutMultipleItem)mAdapter.getData().get(position);
        BelongClass belongClass = (BelongClass)data.getData();
        List<StudentsMultipleItem>  listSub = new ArrayList<>();

        if(students == null || students.size()==0){
            data.setSubItems(listSub);
            return;
        }

        StudentsMultipleItem tempItem;
        int index = 0;
        for(PutStudent putStudent: students){
            tempItem = new StudentsMultipleItem(belongClass.getClassId(),putStudent,index);
            listSub.add(tempItem);
            index++;
        }

        data.setSubItems(listSub);
//        ((PutHWClassAdapter)mAdapter).clickPutByPerson(position);
    }

    //老师的班级列表
    public void classList() {
        mModel.homeworkClass(mPaperId,mType,mSystemId,mLinkId)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<BelongClass>>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<BelongClass>> response) {
                        if(response.isSuccess()){
                            processClassData(response.getData());
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

    private void processClassData(List<BelongClass> datas){
        if(datas != null && datas.size()>0){
            ArrayList<MultiItemEntity> res = new ArrayList<>();

            for (int i=0;i<datas.size();i++) {
                ClassPutMultipleItem lv0 = new ClassPutMultipleItem(datas.get(i));
                res.add(lv0);
            }

            mAdapter.setNewData(res);
        }
    }

    public void putHomework(){
        Map<String,Object> params = new HashMap<>();
        params.putAll(((PutHWClassAdapter)mAdapter).getSelectedParams());
        params.put("paperId",mPaperId);
        params.put("type",mType);
        params.put("teachSystemId",mSystemId);
        params.put("homeworkName",mPaperName);
        params.put("linkId",mLinkId);
        params.put("beginTime",mBeginTime);
        params.put("endTime",mEndTime);
        params.put("isHide",isHide);
        params.put("isAnswer",isAnswer);
//        params.put("types",mTypes);

        mModel.putHomework(params)
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
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse response) {
                        if(response.isSuccess()){
                            Toast.makeText((Context)mRootView,R.string.put_homework_ok,Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post(new Integer(1), EventBusTags.UPDATE_PUT_REMOVE_HW);
                            mRootView.killMyself();
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


    @Inject
    public PutClsHWPresenter(PutClsHWContract.Model model, PutClsHWContract.View rootView) {
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

    /**
     * 点击了单独布置
     * @param position
     */
    public void clickPutDiv(int position) {
        ClassPutMultipleItem data = (ClassPutMultipleItem)mAdapter.getData().get(position);
        List<StudentsMultipleItem>  listSub = data.getSubItems();

        if(listSub== null){
            studentList(((BelongClass)data.getData()).getClassId(),position);
        }else{
//            ((PutHWClassAdapter)mAdapter).clickPutByPerson(position);
        }
    }
}

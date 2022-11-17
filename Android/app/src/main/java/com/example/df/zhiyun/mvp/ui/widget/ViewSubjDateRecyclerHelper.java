package com.example.df.zhiyun.mvp.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.RxLifecycleUtils;
import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.api.ParamsUtils;
import com.example.df.zhiyun.mvp.model.api.service.HomeworkService;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.Subject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.example.df.zhiyun.R;

import com.example.df.zhiyun.mvp.ui.adapter.SubjectItemAdapter;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

/***
 * 用于统一管理各个页面都有的  选择科目，日期，显示列表 的管理。
 */
public class ViewSubjDateRecyclerHelper {
    private  static final int PANNEL_RECYCLER = 1;
    private  static final int PANNEL_SUBJECT = 2;
    private  static final int PANNEL_DATE = 3;

    private int[] subjectColors;
    private int[] arrows = {R.mipmap.arrow_down_red,R.mipmap.arrow_down_red,
            R.mipmap.arrow_down_blue,R.mipmap.arrow_down_yellow};

    //科目和日期
    private FrameLayout flSubject;
    private FrameLayout flDate;

    private TextView tvSubject;
    private TextView tvDate;

    //科目栏和里面的选择按钮
    private FrameLayout flPannelSubject;
    private RecyclerView recyclerView;


    //日期和里面的控件
    private FrameLayout flPannelDate;
    private TextView tvStart;
    private TextView tvEnd;
    private Button btnSubTime;
    private Button btnClearTime;

    private SubjDateInterface mSubjDateInterface;
    private Context mContext;
    TimePickerView pickerView;
    private BaseQuickAdapter mAdapter;
    private int selectedIndex = -1;
    private boolean notifyWhenLoaded = false;

    public boolean isDataReady(){
        if(mAdapter.getData() == null || mAdapter.getData().size() == 0){
            return false;
        }
        return true;
    }

    public void create(Activity activity, SubjDateInterface subjDateInterface, int initSubjId){
        this.mSubjDateInterface = subjDateInterface;
        selectedIndex = initSubjId;
        mContext = activity;
        findView(activity);
        bindListener();
        initSubjectTheme(initSubjId);
        preSetSubjName(initSubjId);
        initRecyclerView();
    }

    public void create(Fragment fragment, SubjDateInterface subjDateInterface, int initSubjId){
        this.mSubjDateInterface = subjDateInterface;
        selectedIndex = initSubjId;
        mContext = fragment.getContext();
        findView(fragment);
        bindListener();
        initSubjectTheme(initSubjId);
        preSetSubjName(initSubjId);
        initRecyclerView();
    }

    public void destroy(){
        if(pickerView != null && pickerView.isShowing()){
            pickerView.dismiss();
        }
    }

    public void getSubjectDatas(List<Subject> datas){
        mAdapter.setNewData(datas);
        if(notifyWhenLoaded && mAdapter.getData() != null && mAdapter.getData().size() >0){
            itemChange(0);
        }
    }

    public void needNotifyWhenLoaded(boolean value){
        notifyWhenLoaded = value;
    }

    void findView(Activity activity){
            subjectColors = activity.getResources().getIntArray(R.array.subject_colors);
            flSubject = (FrameLayout) activity.findViewById(R.id.fl_subject);
            flDate = (FrameLayout) activity.findViewById(R.id.fl_date);

            tvSubject = (TextView) activity.findViewById(R.id.tv_subject);
            tvDate = (TextView) activity.findViewById(R.id.tv_date);

            flPannelSubject = (FrameLayout) activity.findViewById(R.id.fl_subject_container);
            recyclerView = (RecyclerView) activity.findViewById(R.id.recyclerView_subject);

            flPannelDate = (FrameLayout) activity.findViewById(R.id.fl_subject_date);
            tvStart = (TextView) activity.findViewById(R.id.tv_start);
            tvEnd = (TextView) activity.findViewById(R.id.tv_end);
            btnSubTime = (Button) activity.findViewById(R.id.btn_submit_time);
            btnClearTime = (Button) activity.findViewById(R.id.btn_clear_time);
        }

    void findView(Fragment fragment){
        View view = fragment.getView();
        subjectColors = fragment.getContext().getResources().getIntArray(R.array.subject_colors);
        flSubject = (FrameLayout) view.findViewById(R.id.fl_subject);
        flDate = (FrameLayout) view.findViewById(R.id.fl_date);

        tvSubject = (TextView) view.findViewById(R.id.tv_subject);
        tvDate = (TextView) view.findViewById(R.id.tv_date);

        flPannelSubject = (FrameLayout) view.findViewById(R.id.fl_subject_container);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_subject);

        flPannelDate = (FrameLayout) view.findViewById(R.id.fl_subject_date);
        tvStart = (TextView) view.findViewById(R.id.tv_start);
        tvEnd = (TextView) view.findViewById(R.id.tv_end);
        btnSubTime = (Button) view.findViewById(R.id.btn_submit_time);
        btnClearTime = (Button) view.findViewById(R.id.btn_clear_time);
    }

    public void getSubjectData(IRepositoryManager repositoryManager, Fragment fragment, RxErrorHandler errorHandler){
        getSubjectData(repositoryManager,fragment.getContext(),(IView)fragment,errorHandler);
    }

    private void getSubjectData(IRepositoryManager repositoryManager, Context context, IView iView, RxErrorHandler errorHandler){
        Map<String,Object> params = new HashMap<>();
        if(AccountManager.getInstance().getUserInfo() != null){
            params.put("userId", AccountManager.getInstance().getUserInfo().getUserId());
        }

        repositoryManager
                .obtainRetrofitService(HomeworkService.class)
                .getSubjectId(ParamsUtils.fromMap(context,params))
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(disposable -> {
//                    mRootView.showLoading();//转菊花
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> {
//                    mRootView.hideLoading();//隐藏下拉刷新的进度条
                })
                .compose(RxLifecycleUtils.bindToLifecycle(iView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseResponse<List<Subject>>>(errorHandler) {
                    @Override
                    public void onNext(BaseResponse<List<Subject>> response) {
                        if(response.isSuccess()){
                            getSubjectDatas(response.getData());
                        }else{
                            iView.showMessage(response.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }
                });
    }


    private void initRecyclerView(){
        RecyclerView.LayoutManager  manager = new GridLayoutManager(mContext, 4);
        GridDividerItemDecoration itemDecoration = new GridDividerItemDecoration(ArmsUtils.dip2px(mContext,4),
                ContextCompat.getColor(mContext,R.color.bg_grey));
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(itemDecoration);
        mAdapter = new SubjectItemAdapter(new ArrayList<>());
        mAdapter.setOnItemClickListener(subjItemListener);
        recyclerView.setAdapter(mAdapter);
    }

    /***
     * 根据科目不同，初始化不同的颜色，标题风格
     */
    public void initSubjectTheme(int subId){
        int index = getPreSetSubjResourceIndex(subId);

        tvSubject.setTextColor(subjectColors[index]);
        tvDate.setTextColor(subjectColors[index]);
        Drawable arrow = ContextCompat.getDrawable(mContext,arrows[index]);
        arrow.setBounds(0,0,arrow.getIntrinsicWidth(),arrow.getIntrinsicHeight());
        tvSubject.setCompoundDrawables(null,null,arrow,null);
        tvDate.setCompoundDrawables(null,null,arrow,null);
    }

    public static int getPreSetSubjResourceIndex(int subId){
        int index = subId % 4;
        if(subId == Api.SUBJECT_CHINESE){
            index = 1;
        }else if(subId == Api.SUBJECT_MATH){
            index = 2;
        }else if(subId == Api.SUBJECT_ENGLISH){
            index = 3;
        }

        return index;
    }

    public void preSetSubjName(int subId){
        String name = mContext.getString(R.string.subject);
        if(subId == Api.SUBJECT_CHINESE){
            name = mContext.getString(R.string.chinese);
        }else if(subId == Api.SUBJECT_MATH){
            name = mContext.getString(R.string.math);
        }else if(subId == Api.SUBJECT_ENGLISH){
            name = mContext.getString(R.string.english);
        }

        tvSubject.setText(name);
    }

    public void preSetSubjName(String name){
        tvSubject.setText(name);
    }

    private void bindListener(){
        flSubject.setOnClickListener(clickListener);
        flDate.setOnClickListener(clickListener);
        btnSubTime.setOnClickListener(clickListener);

        tvStart.setOnClickListener(clickListener);
        tvEnd.setOnClickListener(clickListener);
        btnClearTime.setOnClickListener(clickListener);

        flPannelSubject.setOnClickListener(clickListener);
        flPannelDate.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.fl_subject:
                    switchPannel(PANNEL_SUBJECT);
                    break;
                case R.id.fl_date:
                    switchPannel(PANNEL_DATE);
                    break;
                case R.id.btn_submit_time:
                    switchPannel(PANNEL_RECYCLER);
                    mSubjDateInterface.onTimeChange(tvStart.getText().toString(),tvEnd.getText().toString());
                    break;
                case R.id.btn_clear_time:
                    switchPannel(PANNEL_RECYCLER);
                    tvStart.setText("");
                    tvEnd.setText("");
                    mSubjDateInterface.onTimeChange("","");
                    break;
                case R.id.tv_start:
                    pickerView = PickViewHelper.getDayPicker(mContext,0,startTimeSelListener);
                    pickerView.show();
                    break;
                case R.id.tv_end:
                    pickerView = PickViewHelper.getDayPicker(mContext,0,endTimeSelListener);
                    pickerView.show();
                    break;
                case R.id.fl_subject_container:
                    switchPannel(PANNEL_RECYCLER);
                    break;
                case R.id.fl_subject_date:
                    switchPannel(PANNEL_RECYCLER);
                    break;
                default:
                    break;
            }
        }
    };

    //切换科目选择和日期选择面板和下面的列表
    private void switchPannel(int pannel){
        if(pannel==PANNEL_SUBJECT ){
            flPannelSubject.setVisibility(flPannelSubject.getVisibility() == View.VISIBLE ? View.GONE:View.VISIBLE);
            flPannelDate.setVisibility( View.GONE);
        } else if(pannel==PANNEL_DATE ){
            flPannelDate.setVisibility(flPannelDate.getVisibility() == View.VISIBLE ? View.GONE:View.VISIBLE);
            flPannelSubject.setVisibility( View.GONE);
        }else if(pannel==PANNEL_RECYCLER ){
            flPannelDate.setVisibility( View.GONE);
            flPannelSubject.setVisibility( View.GONE);
        }
    }

    private BaseQuickAdapter.OnItemClickListener subjItemListener = new BaseQuickAdapter.OnItemClickListener(){
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            itemChange(position);
        }
    };

    private void itemChange(int position){
        Subject data = (Subject)(mAdapter.getData().get(position));
        mSubjDateInterface.onSubjectChange(data.getId(),data.getName());
        switchPannel(PANNEL_RECYCLER);
        initSubjectTheme(Integer.parseInt(data.getId()));
        tvSubject.setText(data.getName());
    }

    private OnTimeSelectListener startTimeSelListener = new OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date, View v) {
            tvStart.setText(TimeUtils.getYmd(date.getTime()));
        }
    };

    private OnTimeSelectListener endTimeSelListener = new OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date, View v) {
            tvEnd.setText(TimeUtils.getYmd(date.getTime()));
        }
    };

    public interface SubjDateInterface{
        void onSubjectChange(String subjId, String subName);
        void onTimeChange(String start, String end);
    }

    public interface SubjDateSyncInterface{
        void syncSubject(String subjId, String subName);
        void syncTime(String start, String end);
    }
}

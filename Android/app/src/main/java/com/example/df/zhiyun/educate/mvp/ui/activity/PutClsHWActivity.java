package com.example.df.zhiyun.educate.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.educate.di.component.DaggerPutClsHWComponent;
import com.example.df.zhiyun.educate.mvp.contract.PutClsHWContract;
import com.example.df.zhiyun.educate.mvp.presenter.PutClsHWPresenter;
import com.example.df.zhiyun.educate.mvp.ui.adapter.PutHWClassAdapter;
import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.example.df.zhiyun.mvp.ui.widget.PickViewHelper;
import com.example.df.zhiyun.mvp.ui.widget.PutHWSpanLookup;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:给班级布置作业
 * <p>
 * Created by MVPArmsTemplate on 08/30/2019 15:04
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PutClsHWActivity extends BaseStatusActivity<PutClsHWPresenter> implements PutClsHWContract.View
    ,BaseMultiItemQuickAdapter.OnItemChildClickListener ,BaseMultiItemQuickAdapter.OnItemClickListener
        ,View.OnClickListener, RadioGroup.OnCheckedChangeListener{

    @BindView(R.id.tv_put_hw_name)
    TextView tvName;

    @BindView(R.id.ll_star)
    LinearLayout llStart;
    @BindView(R.id.ll_end)
    LinearLayout llEnd;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;

    @BindView(R.id.ll_notice_time)
    LinearLayout llNoticeTime;
    @BindView(R.id.tv_notice_time)
    TextView tvNoticeTime;

    @BindView(R.id.rg_type)
    RadioGroup radioGroup;
    @BindView(R.id.rb_stop)
    RadioButton rbStop;
    @BindView(R.id.rb_correct)
    RadioButton rbCorrect;

    @BindView(R.id.tv_put)
    TextView tvPut;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Inject
    BaseMultiItemQuickAdapter mAdapter;
    @Inject
    RecyclerView.LayoutManager layoutManager;

    @Inject
    KProgressHUD progressHUD;

    @Inject
    @Named(PutClsHWContract.View.KEY_PAPER_NAME)
    String mPaperName;
    TimePickerView pickerView;

    public static void startPutHWPage(Context context,String name,String paperId,int type,String teachSystemId,String linkId){
        Intent intent = new Intent(context, PutClsHWActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(PutClsHWContract.View.KEY_PAPER_NAME,name);
        bundle.putString(PutClsHWContract.View.KEY_PAPER_ID,paperId);
        bundle.putInt(PutClsHWContract.View.KEY_TYPE,type);
        bundle.putString(PutClsHWContract.View.KEY_SYSTEM_ID,teachSystemId);
        bundle.putString(PutClsHWContract.View.KEY_LINK_ID,linkId);
        intent.putExtras(bundle);

        ArmsUtils.startActivity(intent);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPutClsHWComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_put_hw; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
//        initBaseView();
//        initRecyclerView();
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        initBaseView();
        initRecyclerView();
//        mPresenter.classList();
    }

    private void initBaseView(){
        llStart.setOnClickListener(this);
        llEnd.setOnClickListener(this);
        tvPut.setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(this);
        tvName.setText(mPaperName);
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager(layoutManager);
        mAdapter.setSpanSizeLookup(new PutHWSpanLookup(mAdapter));
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        ((PutHWClassAdapter)adapter).expanClassItem(position);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        if(view.getId() == R.id.iv_std_sel){
            ((PutHWClassAdapter)adapter).selStd(position);
        }else if(view.getId() == R.id.iv_cls_sel){
            ((PutHWClassAdapter)adapter).selCls(position);
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId == R.id.rb_stop){
            mPresenter.updateTypes(0);
        }else if(checkedId == R.id.rb_correct){
            mPresenter.updateTypes(1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_star:
                pickerView = PickViewHelper.getTimePicker(this,0,startTimeSelListener);
                pickerView.show();
                break;
            case R.id.ll_end:
                pickerView = PickViewHelper.getTimePicker(this,0,endTimeSelListener);
                pickerView.show();
                break;
            case R.id.tv_put:
                mPresenter.clickPut();
                break;
        }
    }

    private OnTimeSelectListener startTimeSelListener = new OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date, View v) {
            String time = TimeUtils.getYmdhm(date.getTime());
            tvStartTime.setText(time);
            mPresenter.updateTimeStart(time);
        }
    };

    private OnTimeSelectListener endTimeSelListener = new OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date, View v) {
            String time = TimeUtils.getYmdhm(date.getTime());
            tvEndTime.setText(time);
            mPresenter.updateTimeEnd(time);
        }
    };

    @Override
    public void showLoading() {
        if(progressHUD!=null){
            if(progressHUD.isShowing()){
                progressHUD.dismiss();
            }
            progressHUD.show();
        }
    }

    @Override
    public void hideLoading() {
        if(progressHUD!=null){
            progressHUD.dismiss();
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(progressHUD != null){
            progressHUD.dismiss();
        }
        if(pickerView != null && pickerView.isShowing()){
            pickerView.dismiss();
        }
    }
}

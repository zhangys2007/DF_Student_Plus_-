package com.example.df.zhiyun.my.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.df.zhiyun.analy.mvp.ui.activity.ScoreTraceActivity;
import com.example.df.zhiyun.my.di.component.DaggerMyComponent;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.app.EventBusTags;
import com.example.df.zhiyun.app.utils.Base64BitmapTransformor;
import com.example.df.zhiyun.main.mvp.contract.MainContract;
import com.example.df.zhiyun.my.mvp.contract.MyContract;
import com.example.df.zhiyun.mvp.model.entity.PersonCenter;
import com.example.df.zhiyun.mvp.model.entity.UserInfo;
import com.example.df.zhiyun.my.mvp.presenter.MyPresenter;
import com.example.df.zhiyun.mvp.ui.activity.ClassActivity;
import com.example.df.zhiyun.mvp.ui.activity.ExerRecordActivity;
import com.example.df.zhiyun.my.mvp.ui.activity.MsgActivity;
import com.example.df.zhiyun.my.mvp.ui.activity.PostsActivity;
import com.example.df.zhiyun.my.mvp.ui.activity.ProfileActivity;
import com.example.df.zhiyun.mvp.ui.activity.QuestionStoreActivity;
import com.example.df.zhiyun.setting.mvp.ui.activity.SettingActivity;
import com.example.df.zhiyun.my.mvp.ui.activity.SignedActivity;

import org.simple.eventbus.Subscriber;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import com.example.df.zhiyun.R;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class MyFragment extends BaseFragment<MyPresenter> implements MyContract.View, View.OnClickListener{
    @BindView(R.id.tv_error_exer)
    TextView tvErrorExc;
//    @BindView(R.id.fake_status_bar)
//    View vFakeBar;
    @BindView(R.id.tv_my_homework)
    TextView tvMyHomework;
    @BindView(R.id.tv_my_data_analy)
    TextView tvMyData;
    @BindView(R.id.tv_my_class)
    TextView tvMyClass;
    @BindView(R.id.ll_my_msg)
    LinearLayout llMyMsg;
    @BindView(R.id.tv_msg_count)
    TextView tvMsgCount;
    @BindView(R.id.tv_my_post)
    TextView tvMyPost;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.ib_profile)
    ImageButton ibProfile;
    @BindView(R.id.ll_exer)
    LinearLayout llExer;
    @BindView(R.id.ll_signed)
    LinearLayout llSigned;
    @BindView(R.id.ll_store)
    LinearLayout llStore;

    @BindView(R.id.civ_thumb)
    CircleImageView iv_thumb;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_school)
    TextView tvSchool;

    @BindView(R.id.tv_signed)
    TextView tvSigned;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.tv_fav)
    TextView tvStore;

    @Inject
    ImageLoader mImageLoader;


    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        bindClicker();
        bindUserInfo();
//        vFakeBar.setBackgroundColor(Color.parseColor("#ff2f3a"));
    }

    public void bindUserInfo(){
        if (AccountManager.getInstance().getUserInfo() != null){
            UserInfo info = AccountManager.getInstance().getUserInfo();
            iv_thumb.setImageBitmap(Base64BitmapTransformor.getThumb(info.getHeadImage(),getContext()));

            tvSchool.setText(info.getSchool());
            tvName.setText(info.getUserName());
        }
    }

    @Subscriber(tag = EventBusTags.UPDATE_USERINFO)
    private void updateUserWithTag(Integer value) {
        Timber.tag(TAG).d("====================get eventTag===============");
        bindUserInfo();
    }

    @Subscriber(tag = EventBusTags.UPDATE_PERSON_CENTER)
    private void updatePersonCenterInfo(PersonCenter data){
        tvSigned.setText(""+data.getCheckInCount());
        tvRecord.setText(""+data.getPracticeCount());
        tvStore.setText(""+data.getCollectionCount());

        if(data.getMessageCount() > 0){
            tvMsgCount.setVisibility(View.VISIBLE);
            if(data.getMessageCount() > 100){
                tvMsgCount.setText("100+");
            }else{
                tvMsgCount.setText(""+data.getMessageCount());
            }

        }else{
            tvMsgCount.setVisibility(View.INVISIBLE);
        }
    }

    /***
     * 绑定点击事件
     */
    void bindClicker(){
        tvErrorExc.setOnClickListener(this);
        tvMyHomework.setOnClickListener(this);
        tvMyClass.setOnClickListener(this);
        tvSetting.setOnClickListener(this);
        ibProfile.setOnClickListener(this);
        tvMyPost.setOnClickListener(this);
        llMyMsg.setOnClickListener(this);
        tvMyData.setOnClickListener(this);

        llExer.setOnClickListener(this);
        llSigned.setOnClickListener(this);
        llStore.setOnClickListener(this);
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_error_exer:
                ((MainContract.View)getActivity()).switchTab(2);
                break;
            case R.id.tv_my_homework:
                ((MainContract.View)getActivity()).switchTab(1);
                break;
            case R.id.tv_my_class:
                ArmsUtils.startActivity(ClassActivity.class);
                break;
            case R.id.tv_setting:
                ArmsUtils.startActivity(SettingActivity.class);
                break;
            case R.id.ib_profile:
                ArmsUtils.startActivity(ProfileActivity.class);
                break;
            case R.id.tv_my_post:
                ArmsUtils.startActivity(PostsActivity.class);
                break;
            case R.id.ll_my_msg:
                ArmsUtils.startActivity(MsgActivity.class);
                break;
            case R.id.ll_exer:
                ArmsUtils.startActivity(ExerRecordActivity.class);
                break;
            case R.id.ll_signed:
                ArmsUtils.startActivity(SignedActivity.class);
                break;
            case R.id.ll_store:
                ArmsUtils.startActivity(QuestionStoreActivity.class);
                break;
            case R.id.tv_my_data_analy:
//                ArmsUtils.startActivity(AnalyActivity.class);
                ArmsUtils.startActivity(ScoreTraceActivity.class);
                break;
            default:
                break;
        }
    }


    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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

    }
}

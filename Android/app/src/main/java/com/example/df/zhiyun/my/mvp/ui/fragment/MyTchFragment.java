package com.example.df.zhiyun.my.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.df.zhiyun.analy.mvp.ui.activity.AnalyActivity;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.my.di.component.DaggerMyTchComponent;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.mvp.model.entity.UserInfo;
import com.example.df.zhiyun.organize.mvp.ui.activity.SchoolItemActivity;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.app.EventBusTags;
import com.example.df.zhiyun.app.utils.Base64BitmapTransformor;

import com.example.df.zhiyun.my.mvp.contract.MyTchContract;
import com.example.df.zhiyun.mvp.model.entity.PersonCenter;
import com.example.df.zhiyun.my.mvp.presenter.MyTchPresenter;

import com.example.df.zhiyun.R;

import com.example.df.zhiyun.my.mvp.ui.activity.MsgActivity;
import com.example.df.zhiyun.my.mvp.ui.activity.PostsActivity;
import com.example.df.zhiyun.my.mvp.ui.activity.ProfileActivity;
import com.example.df.zhiyun.setting.mvp.ui.activity.SettingActivity;

import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>x
 * Created by MVPArmsTemplate on 08/07/2019 09:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MyTchFragment extends BaseFragment<MyTchPresenter> implements MyTchContract.View  , View.OnClickListener{
    @BindView(R.id.fake_status_bar)
    View vFakeBar;

    @BindView(R.id.tv_profile)
    TextView tvProfile;
    @BindView(R.id.tv_error_exer)
    TextView tvError;
    @BindView(R.id.tv_msg)
    TextView tvMsg;
    @BindView(R.id.tv_my_class)
    TextView tvMyCls;
    @BindView(R.id.tv_sys_post)
    TextView tvMyPost;
    @BindView(R.id.tv_setting)
    TextView tvSetting;


    @BindView(R.id.civ_thumb)
    ImageView iv_thumb;
    @BindView(R.id.iv_sex)
    ImageView ivSex;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_school)
    TextView tvSchool;


    public static MyTchFragment newInstance() {
        MyTchFragment fragment = new MyTchFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMyTchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_tch, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        bindClicker();
        bindUserInfo();
//        vFakeBar.setBackgroundColor(Color.parseColor("#51aee3"));
    }

    public void bindUserInfo(){
        if (AccountManager.getInstance().getUserInfo() != null){
            UserInfo info = AccountManager.getInstance().getUserInfo();
            Glide.with(getContext())
                    .load(Base64BitmapTransformor.getThumbTch(info.getHeadImage(),getContext()))
                    .apply(RequestOptions.circleCropTransform())
                    .into(iv_thumb);
            ivSex.setImageResource(info.getSex() == Api.SEX_MAN?R.mipmap.ic_man:
                    R.mipmap.ic_women);
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

    }

    /***
     * 绑定点击事件
     */
    void bindClicker(){
        iv_thumb.setOnClickListener(this);

        tvMyPost.setOnClickListener(this);
        tvSetting.setOnClickListener(this);

        tvProfile.setOnClickListener(this);
        tvError.setOnClickListener(this);

        tvMsg.setOnClickListener(this);
        tvMyCls.setOnClickListener(this);
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_my_data_analy:
                ArmsUtils.startActivity(AnalyActivity.class);
                break;
            case R.id.tv_my_class:
                ArmsUtils.startActivity(SchoolItemActivity.class);
                break;
            case R.id.tv_setting:
                ArmsUtils.startActivity(SettingActivity.class);
                break;
            case R.id.civ_thumb:
            case R.id.tv_profile:
                ArmsUtils.startActivity(ProfileActivity.class);
                break;
            case R.id.tv_sys_post:
                ArmsUtils.startActivity(PostsActivity.class);
                break;
            case R.id.tv_msg:
                ArmsUtils.startActivity(MsgActivity.class);
                break;
            case R.id.tv_error_exer:
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

package com.example.df.zhiyun.my.mvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.df.zhiyun.mvp.ui.activity.BaseStatusActivity;
import com.example.df.zhiyun.my.di.component.DaggerProfileComponent;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.app.utils.Base64BitmapTransformor;
import com.example.df.zhiyun.app.utils.MediaPickerHelper;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.my.mvp.contract.ProfileContract;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.UserInfo;
import com.example.df.zhiyun.my.mvp.presenter.ProfilePresenter;
import com.example.df.zhiyun.mvp.ui.widget.CommonDialog.CommonDialogs;
import com.example.df.zhiyun.mvp.ui.widget.PickViewHelper;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.zhihu.matisse.Matisse;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindArray;
import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

import com.example.df.zhiyun.R;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 个人中心
 * <p>
 * Created by MVPArmsTemplate on 07/21/2019 15:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ProfileActivity extends BaseStatusActivity<ProfilePresenter> implements ProfileContract.View
    , View.OnClickListener{
    public static final int RQ_CAPTURE = 77;    //请求拍照
    public static final int RQ_ALBUM = 79;    //请求相册

    public static final int OPTION_USERNAE = 1;
    public static final int OPTION_BIRTHDAY = 2;
    public static final int OPTION_EMAIL = 3;
    public static final int OPTION_MOBILE = 4;
    public static final int OPTION_PASSWORD = 5;
    public static final int OPTION_SCHOOL = 6;
    private int OptionIndex = -1;


    @BindView(R.id.ll_avatar)
    LinearLayout llAvarta;
    @BindView(R.id.civ_thumb)
    ImageView civAvarta;

    @BindView(R.id.ll_user_name)
    LinearLayout llUserName;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    @BindView(R.id.ll_sex)
    LinearLayout llSex;
    @BindView(R.id.tv_sex)
    TextView tvSex;

    @BindView(R.id.ll_birthday)
    LinearLayout llBirthday;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;

    @BindView(R.id.ll_email)
    LinearLayout llEmail;
    @BindView(R.id.tv_email)
    TextView tvEmail;

    @BindView(R.id.ll_phone)
    LinearLayout llMobile;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;

    @BindView(R.id.ll_psw)
    LinearLayout llPsw;

    @BindView(R.id.ll_school)
    LinearLayout llSchool;
    @BindView(R.id.tv_school_name)
    TextView tvSchool;

    @BindArray(R.array.array_sex)
    String[] arraySex;

    @Inject
    ImageLoader mImageLoader;
    @Inject
    RxPermissions mRxPermissions;

    private KProgressHUD progressHUD;
    TimePickerView pickerView;
    Dialog mDialog;

    View inputarea;
    EditText etValue;

    View pswLayout;
    EditText etPsw1;
    EditText etPsw2;

    View sexItemsLayout;
    FrameLayout tvMan;
    FrameLayout tvWomen;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerProfileComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public Activity getMyActivity() {
        return this;
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_profile; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
    }

    @Override
    public void initAll(@Nullable Bundle savedInstanceState) {
        llAvarta.setOnClickListener(this);
        llUserName.setOnClickListener(this);
        llSex.setOnClickListener(this);
        llBirthday.setOnClickListener(this);
        llEmail.setOnClickListener(this);
        llMobile.setOnClickListener(this);
        llPsw.setOnClickListener(this);
        llSchool.setOnClickListener(this);

        bindData();

        sexItemsLayout = getLayoutInflater().inflate(R.layout.view_sex_sel, null);
        tvMan = (FrameLayout) sexItemsLayout.findViewById(R.id.fl_man);
        tvWomen = (FrameLayout) sexItemsLayout.findViewById(R.id.fl_women);
        tvMan.setOnClickListener(this);
        tvWomen.setOnClickListener(this);

        inputarea = getLayoutInflater().inflate(R.layout.comom_dialog_inputarea, null);
        etValue = (EditText)inputarea.findViewById(R.id.editText);

        pswLayout = getLayoutInflater().inflate(R.layout.view_psw_changel, null);
        etPsw1 = (EditText)pswLayout.findViewById(R.id.et_psw_1);
        etPsw2 = (EditText)pswLayout.findViewById(R.id.et_psw_2);

        progressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(pickerView != null && pickerView.isShowing()){
            pickerView.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_avatar:
                showPictureDialog();
                break;
            case R.id.ll_user_name:
                OptionIndex = OPTION_USERNAE;
                showDialog(getString(R.string.user_name),R.mipmap.person,tvUserName.getText().toString());
                break;
            case R.id.ll_sex:
                showSexDialog();
                break;
            case R.id.ll_birthday:
                pickerView = PickViewHelper.showBirthdayPicker(this,0,startTimeSelListener);
                pickerView.show();
                break;
            case R.id.ll_email:
                OptionIndex = OPTION_EMAIL;
                showDialog(getString(R.string.e_mail),R.mipmap.message,tvEmail.getText().toString());
                break;
            case R.id.ll_phone:
                OptionIndex = OPTION_MOBILE;
                showDialog(getString(R.string.mobile),R.mipmap.mobile,tvMobile.getText().toString());
                break;
            case R.id.ll_psw:
                OptionIndex = OPTION_PASSWORD;
                showPswDialog();
                break;
            case R.id.ll_school:
//                OptionIndex = OPTION_SCHOOL;
//                showDialog(getString(R.string.change_school),R.mipmap.my_act);
                break;
            case R.id.fl_man:
                mPresenter.updateSex(Api.SEX_MAN);
                mDialog.dismiss();
                break;
            case R.id.fl_women:
                mPresenter.updateSex(Api.SEX_WOMEN);
                mDialog.dismiss();
                break;
            default:
                break;
        }
    }

    private OnTimeSelectListener startTimeSelListener = new OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date, View v) {
            mPresenter.updateBirthday(TimeUtils.getYmdhms(date.getTime()));
        }
    };

    @Override
    public void bindData(){
        UserInfo info = AccountManager.getInstance().getUserInfo();
        if(info != null){
            Glide.with(this)
                    .load(Base64BitmapTransformor.getThumb(info.getHeadImage(),this))
                    .apply(RequestOptions.circleCropTransform())
                    .into(civAvarta);
            tvUserName.setText(info.getUserName());
            if(info.getSex() > 0  && info.getSex() < 3){
                tvSex.setText(arraySex[info.getSex()-1]);
            }

            tvBirthday.setText(TimeUtils.getYmd(info.getBirthday()));
            tvEmail.setText(info.getEmail());
            tvMobile.setText(info.getPhone());
            tvSchool.setText(info.getSchool());
        }
    }

    public void showDialog(String title, int iconRes,String preValue){
        etValue.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this,iconRes),
                null,null,null);
        if(TextUtils.isEmpty(preValue)){
            etValue.setText("");
        }else {
            etValue.setText(preValue);
        }
        mDialog = new CommonDialogs(this)
                .setView(inputarea)
                .setTitle(title)
                .setNegativeButtonColor(R.color.blue)
                .setPositiveButton(getString(R.string.sure),dialogOkListener)
                .setNegativeButton(getString(R.string.cancel),null)
                .builder();
        mDialog.show();
    }

    public void showPswDialog(){
        mDialog = new CommonDialogs(this)
                .setView(pswLayout)
                .setTitle(getString(R.string.change_psw))
                .setNegativeButtonColor(R.color.blue)
                .setPositiveButton(getString(R.string.sure),pswOkListener)
                .setNegativeButton(getString(R.string.cancel),null)
                .builder();
        mDialog.show();
    }

    private View.OnClickListener pswOkListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            mPresenter.updatePassword(etPsw1.getText().toString()
                    ,etPsw2.getText().toString());
        }
    };

    /***
     * 头像选取方式对话框
     */
    public void showPictureDialog(){
        mDialog = MediaPickerHelper.getPicSelDialog(this,"头像",mPresenter);
        mDialog.show();
    }

    /***
     * 性别选择对话框
     */
    public void showSexDialog(){
        mDialog = new CommonDialogs(this)
                .setView(sexItemsLayout)
                .setTitle(getString(R.string.sex))
                .setNegativeButtonColor(R.color.blue)
                .setNegativeButton(getString(R.string.cancel),null)
                .builder();
        mDialog.show();
    }

    private View.OnClickListener dialogOkListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            switch (OptionIndex){
                case OPTION_USERNAE:
                    mPresenter.updateUserName(etValue.getText().toString());
                    break;
                case OPTION_BIRTHDAY:
                    mPresenter.updateBirthday(etValue.getText().toString());
                    break;
                case OPTION_EMAIL:
                    mPresenter.updateEmail(etValue.getText().toString());
                    break;
                case OPTION_MOBILE:
                    mPresenter.updateMobile(etValue.getText().toString());
                    break;
            }
        }
    };

    /**
     * 拉起拍照
     * @param
     */
    public void lunchCamera(Uri uri){
        MediaPickerHelper.lunchCamera(this,RQ_CAPTURE,uri);
    }

    public void lunchAlbum(){
        MediaPickerHelper.lunchAlbum(this,RQ_ALBUM);
    }


    @Override
    public void showLoading() {
        if(progressHUD!=null){
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQ_CAPTURE && resultCode == RESULT_OK) {  //拍照返回
            mPresenter.cameraBack(data);
        } else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {   //剪切返回
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mPresenter.cropBack(data);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        } else if(resultCode == RESULT_OK && requestCode == RQ_ALBUM){
            List<Uri> uris = Matisse.obtainResult(data);
            if(uris!= null && uris.size()>0){
                mPresenter.albumBack(uris.get(0));
            }

        }
    }

    /***
     * 剪切图片
     * @param source
     * @param dest
     */
    public void cropPic(Uri source,Uri dest){
        if(source == null || dest == null){
            return;
        }
        CropImage.activity(source)
                .setOutputUri(dest)
                .start(this);
    }
}

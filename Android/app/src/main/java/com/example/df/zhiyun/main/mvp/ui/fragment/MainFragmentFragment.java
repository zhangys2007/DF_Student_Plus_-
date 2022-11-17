package com.example.df.zhiyun.main.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.df.zhiyun.my.mvp.ui.activity.ProfileActivity;
import com.example.df.zhiyun.analy.mvp.ui.activity.ScoreTraceActivity;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.app.EventBusTags;
import com.example.df.zhiyun.app.utils.Base64BitmapTransformor;
import com.example.df.zhiyun.app.utils.DrawableUrlUtils;
import com.example.df.zhiyun.main.mvp.contract.MainFragmentContract;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.main.mvp.presenter.MainFragmentPresenter;
import com.example.df.zhiyun.mvp.ui.activity.HomeworkListActivity;
import com.example.df.zhiyun.mvp.ui.activity.SearchActivity;
import com.example.df.zhiyun.mvp.ui.holder.BannerHolder;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.main.di.component.DaggerMainFragmentComponent;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 主页面里对应首页子页面
 * <p>
 * Created by MVPArmsTemplate on 07/17/2019 13:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainFragmentFragment extends BaseLazyLoadFragment<MainFragmentPresenter> implements MainFragmentContract.View
    , OnItemClickListener , View.OnClickListener{
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.ts_marquee)
    TextSwitcher tsNews;
    @BindView(R.id.convenientBanner)
    ConvenientBanner mBanner;

    @BindView(R.id.cv_analy)
    CardView ivAnaly;
    @BindView(R.id.cv_chn)
    CardView ivChn;
    @BindView(R.id.cv_math)
    CardView ivMath;
    @BindView(R.id.cv_en)
    CardView ivEn;

    public static MainFragmentFragment newInstance() {
        MainFragmentFragment fragment = new MainFragmentFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMainFragmentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initMarquee();
        initBanner();

        bindUserInfo();
        initListener();
    }

    private void bindUserInfo(){
        if(AccountManager.getInstance().getUserInfo() != null){
            ivAvatar.setImageBitmap(Base64BitmapTransformor.getThumb(
                    AccountManager.getInstance().getUserInfo().getHeadImage(),getContext()));
        }
    }

    private void initListener(){
        ivAvatar.setOnClickListener(this);
        ivChn.setOnClickListener(this);
        ivEn.setOnClickListener(this);
        ivMath.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        ivAnaly.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_avatar:
                ArmsUtils.startActivity(ProfileActivity.class);
                break;
            case R.id.iv_search:
                SearchActivity.startSearchPage(getContext(), Api.SEARCH_HOMEWORK);
                break;
            case R.id.cv_chn:
                startHomeworkListPage(Api.SUBJECT_CHINESE);
                break;
            case R.id.cv_math:
                startHomeworkListPage(Api.SUBJECT_MATH);
                break;
            case R.id.cv_en:
                startHomeworkListPage(Api.SUBJECT_ENGLISH);
                break;
            case R.id.cv_analy:
//                ArmsUtils.startActivity(AnalyActivity.class);
                ArmsUtils.startActivity(ScoreTraceActivity.class);
                break;
        }
    }

    @Nullable
    @Override
    public Context getPageContext() {
        return this.getContext();
    }

    @Subscriber(tag = EventBusTags.UPDATE_USERINFO)
    private void updateUserWithTag(Integer value) {
        bindUserInfo();
    }

    private void startHomeworkListPage(int type){
        HomeworkListActivity.launchActivity(this.getContext(),type);
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

    /***
     * 初始化跑马灯
     */
    private void initMarquee(){
        tsNews.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView tv=new TextView(getActivity());
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
                lp.gravity = Gravity.CENTER;
                tv.setLayoutParams(lp);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(12.f);
                tv.setTextColor(ContextCompat.getColor(getActivity(),R.color.text_grey));
                return tv;
            }
        });

        Animation in = AnimationUtils.loadAnimation(getActivity(), R.anim.push_up_in);
        Animation out = AnimationUtils.loadAnimation(getActivity(), R.anim.push_up_out);
        tsNews.setInAnimation(in);
        tsNews.setOutAnimation(out);
        mPresenter.startMarquee();
    }

    @Override
    public void marqueeNext(String msg) {
        tsNews.setText(msg);
    }


    /***
     * 初始化banner
     */
    private void initBanner() {
        List<String> imgUrls= new ArrayList<>();
        imgUrls.add(DrawableUrlUtils.imageTranslateUri(mContext,R.mipmap.banner_1));
        imgUrls.add(DrawableUrlUtils.imageTranslateUri(mContext,R.mipmap.banner_2));
        imgUrls.add(DrawableUrlUtils.imageTranslateUri(mContext,R.mipmap.banner_3));
        imgUrls.add(DrawableUrlUtils.imageTranslateUri(mContext,R.mipmap.banner_4));
        imgUrls.add(DrawableUrlUtils.imageTranslateUri(mContext,R.mipmap.banner_5));
        mBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new BannerHolder(itemView, mContext);
            }

            @Override
            public int getLayoutId() {
                return R.layout.item_banner;
            }
        }, imgUrls)
                .setPageIndicator(new int[] { R.mipmap.dot_inact, R.mipmap.dot })
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPointViewVisible(true)
                .setOnItemClickListener(this)
        ;
    }

    /***
     * 点击了banner第几个广告
     * @param position
     */
    @Override
    public void onItemClick(int position) {

    }

    @Override
    protected void lazyLoadData() {
        mPresenter.requestData();
    }

    @Override
    public void onResume() {
        super.onResume();
        mBanner.startTurning(3000);
    }

    @Override
    public void onPause() {
        super.onPause();
        mBanner.stopTurning();
    }
}

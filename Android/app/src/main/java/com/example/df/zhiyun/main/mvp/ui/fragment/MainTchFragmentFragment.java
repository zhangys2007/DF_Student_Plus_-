package com.example.df.zhiyun.main.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.common.mvp.ui.widget.MarqueVIew;
import com.example.df.zhiyun.main.mvp.model.entity.FocusStd;
import com.example.df.zhiyun.main.mvp.model.entity.MsgItem;
import com.example.df.zhiyun.main.mvp.model.entity.PushData;
import com.example.df.zhiyun.main.mvp.model.entity.TodoItem;
import com.example.df.zhiyun.my.mvp.ui.activity.PostsActivity;
import com.jess.arms.base.BaseLazyLoadFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import com.example.df.zhiyun.app.EventBusTags;
import com.example.df.zhiyun.app.utils.DrawableUrlUtils;
import com.example.df.zhiyun.main.di.component.DaggerMainTchFragmentComponent;
import com.example.df.zhiyun.main.mvp.contract.MainTchFragmentContract;
import com.example.df.zhiyun.main.mvp.presenter.MainTchFragmentPresenter;

import com.example.df.zhiyun.R;

import com.example.df.zhiyun.mvp.ui.holder.BannerHolder;
import com.kaopiz.kprogresshud.KProgressHUD;

import javax.inject.Inject;
import javax.inject.Named;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:主页面里对应首页子页面 教师
 * <p>
 * Created by MVPArmsTemplate on 08/06/2019 10:01
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainTchFragmentFragment extends BaseLazyLoadFragment<MainTchFragmentPresenter>
        implements MainTchFragmentContract.View
        , OnItemClickListener, View.OnClickListener{
    @BindView(R.id.iv_notice)
    ImageView ivNotice;

    @BindView(R.id.ts_marquee)
    MarqueVIew tsMarque;
    @BindView(R.id.convenientBanner)
    ConvenientBanner mBanner;

    @BindView(R.id.tv_correct_hw)
    TextView tvCorrectHw;
    @BindView(R.id.tv_correct_paper)
    TextView tvCorrectPaper;
    @BindView(R.id.tv_study_analy)
    TextView tvStudyAnaly;
    @BindView(R.id.tv_cls_error)
    TextView tvClsError;
    @BindView(R.id.tv_all_push)
    TextView tvAllPush;

    @BindView(R.id.recyclerView_focus)
    RecyclerView recyclerView;
    @BindView(R.id.recyclerView_todo)
    RecyclerView recyclerViewTodo;
    @BindView(R.id.recyclerView_push)
    RecyclerView recyclerViewPush;

    @Inject
    RecyclerView.ItemDecoration mItemDecoration;

    @Inject
    @Named(MainTchFragmentContract.View.KEY_FOCUS)
    BaseQuickAdapter mAdapter;
    @Inject
    @Named(MainTchFragmentContract.View.KEY_FOCUS)
    RecyclerView.LayoutManager layoutManager;

    @Inject
    @Named(MainTchFragmentContract.View.KEY_TOD)
    BaseQuickAdapter mAdapterTodo;
    @Inject
    @Named(MainTchFragmentContract.View.KEY_TOD)
    RecyclerView.LayoutManager layoutManagerTodo;

    @Inject
    @Named(MainTchFragmentContract.View.KEY_PUSH)
    BaseQuickAdapter mAdapterPush;
    @Inject
    @Named(MainTchFragmentContract.View.KEY_PUSH)
    RecyclerView.LayoutManager layoutManagerPush;

    @Inject
    KProgressHUD progressHUD;

    public static MainTchFragmentFragment newInstance() {
        MainTchFragmentFragment fragment = new MainTchFragmentFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMainTchFragmentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_tch, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initMarquee();
        initBanner();
        initListener();
        initLayoutManager();
    }

    private void initLayoutManager(){
//        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(layoutManager);
//        mAdapter.setOnItemClickListener(hwItemClickListener);
        mAdapter.setEnableLoadMore(false);
        recyclerView.setAdapter(mAdapter);

        recyclerViewTodo.addItemDecoration(mItemDecoration);
        recyclerViewTodo.setLayoutManager(layoutManagerTodo);
//        mAdapter.setOnItemClickListener(hwItemClickListener);
        mAdapterTodo.setEnableLoadMore(false);
        recyclerViewTodo.setAdapter(mAdapterTodo);

        recyclerViewPush.setLayoutManager(layoutManagerPush);
//        mAdapter.setOnItemClickListener(hwItemClickListener);
        mAdapterPush.setEnableLoadMore(false);
        recyclerViewPush.setAdapter(mAdapterPush);
    }


    private void initListener(){
        ivNotice.setOnClickListener(this);
        tvCorrectHw.setOnClickListener(this);
        tvCorrectPaper.setOnClickListener(this);
        tvStudyAnaly.setOnClickListener(this);
        tvClsError.setOnClickListener(this);
        tvAllPush.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_notice:
                // TODO: 2020-04-22 点击铃铛
                break;
            case R.id.tv_correct_hw:
                break;
            case R.id.tv_correct_paper:
                break;
            case R.id.tv_study_analy:
                break;
            case R.id.tv_cls_error:
                break;
            case R.id.tv_all_push:
                ArmsUtils.startActivity(PostsActivity.class);
                break;
        }
    }

    @Nullable
    @Override
    public Context getPageContext() {
        return this.getContext();
    }


    @Override
    public void setData(@Nullable Object data) {

    }

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
    public void onDestroy() {
        super.onDestroy();
        if(progressHUD != null){
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

    }

    /***
     * 初始化跑马灯
     */
    private void initMarquee(){
        tsMarque.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                View item = LayoutInflater.from(getContext()).inflate(R.layout.item_marque_msg,null);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);
                item.setLayoutParams(lp);
                return item;
            }
        });

        Animation in = AnimationUtils.loadAnimation(getActivity(), R.anim.push_up_in);
        Animation out = AnimationUtils.loadAnimation(getActivity(), R.anim.push_up_out);
        tsMarque.setInAnimation(in);
        tsMarque.setOutAnimation(out);
    }

    /**
     * 更新跑马灯的文字
     * @param msg
     */
    @Override
    public void marqueeNext(MsgItem msg) {
        View item = tsMarque.getNextView();
        TextView tvMsgContent = item.findViewById(R.id.tv_msg_content);
        TextView tvMsgTime = item.findViewById(R.id.tv_msg_time);
        tvMsgContent.setText(msg.getSendContent());
        tvMsgTime.setText(TimeUtils.getMdhmLink(msg.getSendTime()));
        tsMarque.showNext();
    }

    /***
     * 初始化banner
     */
    private void initBanner() {
        List<String> imgUrls= new ArrayList<>();
        imgUrls.add(DrawableUrlUtils.imageTranslateUri(mContext,R.mipmap.banner_0));
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

    /**
     * 显示和隐藏走马灯
     * @param show
     */
    @Override
    public void showMarque(boolean show) {
        tsMarque.setVisibility(show?View.VISIBLE:View.GONE);
    }

    /**
     * 处理推送
     * @param data
     */
    @Override
    public void processPush(List<PushData> data) {
        mAdapterPush.setNewData(data);
    }

    /**
     * 处理待办
     * @param data
     */
    @Override
    public void processTodo(List<TodoItem> data) {

        mAdapterTodo.setNewData(data);
    }

    /**
     * 处理关注的学生
     * @param data
     */
    @Override
    public void processFollow(List<FocusStd> data) {
        mAdapter.setNewData(data);
    }
}

package com.example.df.zhiyun.correct.mvp.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.example.df.zhiyun.app.TypefaceHolder;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.correct.di.component.DaggerCorrectHWlistenComponent;
import com.example.df.zhiyun.correct.mvp.contract.CorrectHWContract;
import com.example.df.zhiyun.correct.mvp.contract.CorrectHWlistenContract;
import com.example.df.zhiyun.mvp.contract.INavigation;
import com.example.df.zhiyun.mvp.contract.IQuestionLookup;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.correct.mvp.presenter.CorrectHWlistenPresenter;
import com.example.df.zhiyun.correct.mvp.ui.adapter.CorrectHWAdapter;
import com.example.df.zhiyun.mvp.ui.widget.EmbedBottomSheetBehavior;
import com.example.df.zhiyun.mvp.ui.widget.ViewLastNextInitHelper;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.PLOnBufferingUpdateListener;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.PLOnPreparedListener;
import com.pili.pldroid.player.PlayerState;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import me.jessyan.progressmanager.ProgressListener;
import me.jessyan.progressmanager.body.ProgressInfo;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 听力题
 * <p>
 * Created by MVPArmsTemplate on 08/19/2019 11:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class CorrectHWlistenFragment extends BaseFragment<CorrectHWlistenPresenter> implements CorrectHWlistenContract.View
        ,View.OnClickListener, INavigation, IQuestionLookup {
    @BindString(R.string.sub_title_question)
    String strSubTitle;
    @BindView(R.id.tv_sep_content)
    HtmlTextView tvContent;
    @BindView(R.id.ll_content_bottom_sheet)
    NestedScrollView llBottomSheet;
    @BindView(R.id.ib_close)
    ImageButton ibClose;
    @BindView(R.id.tv_sub_title)
    TextView tvSubTitle;
    @BindView(R.id.tv_steam)
    TextView tvSteam;
    @BindView(R.id.fl_expand)
    FrameLayout flExpand;

    @BindView(R.id.tv_currTime)
    TextView tvCurrentTime;
    @BindView(R.id.seekBar)
    SeekBar sbProgress;
    @BindView(R.id.ib_play)
    ImageButton ibPlay;


    @BindView(R.id.vp_sep)
    ViewPager vpContainer;

    EmbedBottomSheetBehavior bottomSheetBehavior;
    CorrectHWAdapter mAdapter;

    private PLMediaPlayer mMediaPlayer;
    private AVOptions mAVOptions;
    private ProgressInfo mLastDownloadingInfo;


    public static CorrectHWlistenFragment newInstance(int index, int total, int nvType) {
        CorrectHWlistenFragment fragment = new CorrectHWlistenFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index",index);
        bundle.putInt("total",total);
        bundle.putInt(ViewLastNextInitHelper.KEY_NAVIGATION,nvType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getQuestionIndex() {
        return getArguments().getInt("index");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCorrectHWlistenComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_correct_listen, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Question data = QuestionAnswerHolder.getInstance().getQuestion(this);
        if(data == null){
            return;
        }
        bindData(data);
        if(getParentFragment() == null) {   //限制分屏题只能套一层
            initViewPager(data);
        }
        initMideoPlayer();

        bottomSheetBehavior = EmbedBottomSheetBehavior.from(llBottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);
        bottomSheetBehavior.setState(EmbedBottomSheetBehavior.STATE_EXPANDED);
        ibClose.setVisibility(View.VISIBLE);
        ibClose.setOnClickListener(this);
        flExpand.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fl_expand:
                bottomSheetBehavior.setState(EmbedBottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.ib_close:
                bottomSheetBehavior.setState(EmbedBottomSheetBehavior.STATE_COLLAPSED);
                break;
            case R.id.ib_play:
                if(mPresenter.isAudioFileReady()){
                    PlayerState state = mMediaPlayer.getPlayerState();
                    if(state == PlayerState.PLAYING){
                        mMediaPlayer.pause();
                    }else if(state == PlayerState.PAUSED || state == PlayerState.PREPARED){
                        mMediaPlayer.start();
                        ibPlay.setImageResource(R.mipmap.pause);
                    }else if(state == PlayerState.COMPLETED ){
                        playAudioFile(mPresenter.getAudioFile());
                    }
                }else{
                    Question data = QuestionAnswerHolder.getInstance().getQuestion(this);
                    mPresenter.downloadAudioFile(data.getHearingUrl(),listener);
                }
                break;
        }
    }

    @Override
    public void enablePlayButton(boolean value) {
        ibPlay.setEnabled(value);
    }

    @Override
    public void nextQuestion() {
        if(vpContainer.getCurrentItem() < vpContainer.getAdapter().getCount()-1){
            vpContainer.setCurrentItem(vpContainer.getCurrentItem()+1);
        }else{
            ((INavigation)getActivity()).nextQuestion();
        }
    }

    @Override
    public void lastQuestion() {
        if(0 < vpContainer.getCurrentItem()){
            vpContainer.setCurrentItem(vpContainer.getCurrentItem()-1);
        }else{
            ((INavigation)getActivity()).lastQuestion();
        }
    }

    @Override
    public void submitPaper() {
        ((INavigation)getActivity()).submitPaper();
    }

    public void bindData(Question data) {
        TypefaceHolder.getInstance().setHt(tvContent);
        RichTextViewHelper.setContent(tvContent,RichTextViewHelper.makeQuestionTitle(data.getQuestionNum(),data.getContent()));
        List<Question> listQuestion = data.getSubQuestion();
        updateSubTitle(0);
        if(TextUtils.isEmpty(data.getQuestionStem())){
            tvSteam.setVisibility(View.GONE);
        }else{
            tvSteam.setVisibility(View.VISIBLE);
            tvSteam.setText(RichTextViewHelper.ToDBC(data.getQuestionStem()));
        }
    }

    private void initMideoPlayer(){
        String baseUrl = ((CorrectHWContract.View)getActivity()).getListenerBaseUrl();
        if(baseUrl != null){
            mAVOptions = new AVOptions();
            mAVOptions.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
            AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
            audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

            if (mMediaPlayer == null) {
                mMediaPlayer = new PLMediaPlayer(getContext(), mAVOptions);
                mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
                mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
                mMediaPlayer.setOnErrorListener(mOnErrorListener);
                mMediaPlayer.setOnInfoListener(mOnInfoListener);
                mMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
                mMediaPlayer.setWakeMode(getContext(), PowerManager.PARTIAL_WAKE_LOCK);
            }

            ibPlay.setOnClickListener(this);
            sbProgress.setOnSeekBarChangeListener(seekBarChangeListener);
        }
    }

    ProgressListener listener = new ProgressListener() {
        @Override
        public void onProgress(ProgressInfo progressInfo) {
            // 如果你不屏蔽用户重复点击上传或下载按钮,就可能存在同一个 Url 地址,上一次的上传或下载操作都还没结束,
            // 又开始了新的上传或下载操作,那现在就需要用到 id(请求开始时的时间) 来区分正在执行的进度信息
            // 这里我就取最新的下载进度用来展示,顺便展示下 id 的用法

            if (mLastDownloadingInfo == null) {
                mLastDownloadingInfo = progressInfo;
            }

            //因为是以请求开始时的时间作为 Id ,所以值越大,说明该请求越新
            if (progressInfo.getId() < mLastDownloadingInfo.getId()) {
                return;
            } else if (progressInfo.getId() > mLastDownloadingInfo.getId()) {
                mLastDownloadingInfo = progressInfo;
            }

            int progress = mLastDownloadingInfo.getPercent();
            tvCurrentTime.setText(progress + "%");
            if (mLastDownloadingInfo.isFinish()) {
                tvCurrentTime.setText("加载完成");
            }
        }

        @Override
        public void onError(long id, Exception e) {
            tvCurrentTime.post(new Runnable() {
                @Override
                public void run() {
                    tvCurrentTime.setText("加载失败");
                }
            });
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        release();
        AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        audioManager.abandonAudioFocus(null);
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener(){

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            mMediaPlayer.seekTo(seekBar.getProgress());
        }
    };

    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    public void playAudioFile(File file) {
        try {
            mMediaPlayer.setDataSource(file.getAbsolutePath());
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PLOnPreparedListener mOnPreparedListener = new PLOnPreparedListener() {
        @Override
        public void onPrepared(int preparedTime) {
            mMediaPlayer.start();
        }
    };

    private PLOnInfoListener mOnInfoListener = new PLOnInfoListener() {
        @Override
        public void onInfo(int what, int extra) {
//            Timber.tag(TAG).e(  "OnInfo, what = " + what + ", extra = " + extra);
            switch (what) {
                case PLOnInfoListener.MEDIA_INFO_BUFFERING_START:
                    Timber.tag(TAG).e(  "buffer start");
                    break;
                case PLOnInfoListener.MEDIA_INFO_BUFFERING_END:
                case PLOnInfoListener.MEDIA_INFO_AUDIO_RENDERING_START:
                    Timber.tag(TAG).e(  "buffer end");
                    mPresenter.startCountTime();
                    ibPlay.setImageResource(R.mipmap.pause);
                    break;
                case PLOnInfoListener.MEDIA_INFO_STATE_CHANGED_PAUSED:
                    ibPlay.setImageResource(R.mipmap.play);
                    break;
                default:
                    break;
            }
        }
    };

    private PLOnBufferingUpdateListener mOnBufferingUpdateListener = new PLOnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(int percent) {
            Timber.tag(TAG).e( "onBufferingUpdate: " + percent + "%");
        }
    };

    private PLOnCompletionListener mOnCompletionListener = new PLOnCompletionListener() {
        @Override
        public void onCompletion() {
            Timber.tag(TAG).e( "Play Completed !");
            mPresenter.stopCountTime();
            ibPlay.setImageResource(R.mipmap.play);
        }
    };

    private PLOnErrorListener mOnErrorListener = new PLOnErrorListener() {
        @Override
        public boolean onError(int errorCode) {
            Timber.tag(TAG).e( "Error happened, errorCode = " + errorCode);
            switch (errorCode) {
                case PLOnErrorListener.ERROR_CODE_IO_ERROR:
                    /**
                     * SDK will do reconnecting automatically
                     */
                    Toast.makeText(getContext(), "IO Error !", Toast.LENGTH_SHORT);
                    return false;
                case PLOnErrorListener.ERROR_CODE_OPEN_FAILED:
                    Toast.makeText(getContext(), "failed to open player !", Toast.LENGTH_SHORT);
                    break;
                case PLOnErrorListener.ERROR_CODE_SEEK_FAILED:
                    Toast.makeText(getContext(), "failed to seek !", Toast.LENGTH_SHORT);
                    break;
                default:
                    Toast.makeText(getContext(), "unknown error !", Toast.LENGTH_SHORT);
                    break;
            }
            return true;
        }
    };

    public void initViewPager(Question data) {
//        if (mAdapter == null && data.getSubQuestion() != null && data.getSubQuestion().size()>0) {
        if (mAdapter == null ) {
            mAdapter = new CorrectHWAdapter(getChildFragmentManager(), data.getSubQuestion());
            mAdapter.setParentNavigationType(getArguments().getInt(ViewLastNextInitHelper.KEY_NAVIGATION,
                    ViewLastNextInitHelper.TYPE_NONE));
            vpContainer.setAdapter(mAdapter);
            vpContainer.setOffscreenPageLimit(0);
            vpContainer.addOnPageChangeListener(vpListener);
            updateSubTitle(1);
        }
    }

    private ViewPager.OnPageChangeListener vpListener = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            updateSubTitle(i+1);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private EmbedBottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new EmbedBottomSheetBehavior.BottomSheetCallback(){
        @Override
        public void onStateChanged(@NonNull View view, int i) {
            switch (i) {
                case BottomSheetBehavior.STATE_COLLAPSED:
                    ibClose.setVisibility(View.INVISIBLE);
                    break;
                case BottomSheetBehavior.STATE_DRAGGING:
                    break;
                case BottomSheetBehavior.STATE_EXPANDED:
                    ibClose.setVisibility(View.VISIBLE);
                    break;
                case BottomSheetBehavior.STATE_HIDDEN:
                    break;
                case BottomSheetBehavior.STATE_SETTLING:
                    break;
            }
        }

        @Override
        public void onSlide(@NonNull View view, float v) {

        }
    };


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

    private void updateSubTitle(int index){
        int total = vpContainer.getAdapter() ==null?0:vpContainer.getAdapter().getCount();
        tvSubTitle.setText(String.format(strSubTitle,index,total));
    }


    @Override
    public void updateTime() {
        long duration = mMediaPlayer.getDuration();
        long position = mMediaPlayer.getCurrentPosition();
        int bufferSize = mMediaPlayer.getHttpBufferSize().intValue();
        if (sbProgress.getMax() != duration) {
            sbProgress.setMax((int) duration);
        }
        sbProgress.setProgress((int) position);
        sbProgress.setSecondaryProgress(bufferSize);
        tvCurrentTime.setText(TimeUtils.formatMusicTime(position) + "/" + TimeUtils.formatMusicTime(duration));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {

        }else {
            if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
                mMediaPlayer.pause();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
            mMediaPlayer.pause();
        }
    }
}

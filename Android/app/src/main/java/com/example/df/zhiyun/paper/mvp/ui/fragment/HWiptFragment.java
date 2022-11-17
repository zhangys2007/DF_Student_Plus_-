package com.example.df.zhiyun.paper.mvp.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.example.df.zhiyun.app.TypefaceHolder;
import com.example.df.zhiyun.app.utils.FileUriUtil;
import com.example.df.zhiyun.app.utils.MediaPickerHelper;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.mvp.contract.INavigation;
import com.example.df.zhiyun.mvp.contract.IQuestionLookup;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;
import com.example.df.zhiyun.paper.di.component.DaggerHWiptComponent;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.mvp.contract.DoHomeworkContract;
import com.example.df.zhiyun.paper.mvp.contract.HWiptContract;
import com.example.df.zhiyun.mvp.model.entity.Answer;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.paper.mvp.presenter.HWiptPresenter;
import com.example.df.zhiyun.paper.mvp.ui.adapter.HWinputAdapter;
import com.example.df.zhiyun.mvp.ui.widget.CanvasDialog;
import com.example.df.zhiyun.mvp.ui.widget.ViewLastNextInitHelper;

import javax.inject.Inject;

import butterknife.BindView;

import com.example.df.zhiyun.R;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.PLOnBufferingUpdateListener;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.PLOnPreparedListener;
import com.pili.pldroid.player.PlayerState;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:作业里的填空题
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 17:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HWiptFragment extends BaseFragment<HWiptPresenter> implements HWiptContract.View, DoHomeworkContract , IQuestionLookup ,View.OnClickListener
        ,MediaPickerHelper.IMediaPicker,CanvasDialog.ICanvasEnvent{
    public static final int RQ_CAPTURE = 87;    //请求拍照
    public static final int RQ_ALBUM = 89;    //请求相册
    @BindView(R.id.tv_input_content)
    HtmlTextView tvContent;
    @BindView(R.id.recyclerView_input)
    RecyclerView recyclerView;

    @BindView(R.id.tv_steam)
    HtmlTextView tvSteam;

    @Inject
    BaseQuickAdapter mAdapter;
    //    @Inject
//    RecyclerView.LayoutManager layoutManager;
    @Inject
    RecyclerView.ItemDecoration itemDecoration;

    @BindView(R.id.et_input)
    EditText editText;
    @BindView(R.id.ll_audio)
    LinearLayout llAudio;
    @BindView(R.id.tv_currTime)
    TextView tvCurrentTime;
    @BindView(R.id.seekBar)
    SeekBar sbProgress;
    @BindView(R.id.ib_play)
    ImageButton ibPlay;

    private String mAudioPath;
    private String mStrAnswer;
    private PLMediaPlayer mMediaPlayer;
    private AVOptions mAVOptions;

    private int optionIndex = -1;  // 操作的是第几个item
    Dialog mDialog;
    Dialog mCameraDialog;
    private File mCameraFile;

    public static HWiptFragment newInstance(int index, int total, int nvType) {
        HWiptFragment fragment = new HWiptFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index",index);
        bundle.putInt("total",total);
        bundle.putInt(ViewLastNextInitHelper.KEY_NAVIGATION,nvType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHWiptComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(recyclerView != null){
            recyclerView.setAdapter(null);
        }

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

    @Override
    public void enablePlayButton(boolean value) {
        ibPlay.setEnabled(value);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ib_play){
            Timber.tag(TAG).d("ib_play");
            PlayerState state = mMediaPlayer.getPlayerState();
            if(state == PlayerState.PLAYING){
                mMediaPlayer.pause();
            }else if(state == PlayerState.PAUSED || state == PlayerState.PREPARED){
                mMediaPlayer.start();
                ibPlay.setImageResource(R.mipmap.pause);
            }else if(state == PlayerState.IDLE || state == PlayerState.COMPLETED ){
                playAudioPath(mAudioPath);
            }
        }
    }

    public void playAudioPath(String path) {
        try {
            mMediaPlayer.setDataSource(path);
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

    @Override
    public int getQuestionIndex() {
        return getArguments().getInt("index");
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hwipt, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Question data = QuestionAnswerHolder.getInstance().getQuestion(this);
        if(data == null){
            return;
        }
        bindData(data);
        if(TextUtils.isEmpty(data.getHearing())){
            initRecyclerView(data);
        }else{
            mAudioPath = data.getHearing();
            llAudio.setVisibility(View.VISIBLE);
            editText.setVisibility(View.VISIBLE);
            editText.addTextChangedListener(textWatcher);
            initVideoPlayer();
        }
        if(getParentFragment() == null){  //没被嵌套，不是分屏里的小题
            new ViewLastNextInitHelper().init(this,(INavigation)getActivity(),
                    getArguments().getInt(ViewLastNextInitHelper.KEY_NAVIGATION),false);
        }else{
            new ViewLastNextInitHelper().init(this,(INavigation)getParentFragment(),
                    getArguments().getInt(ViewLastNextInitHelper.KEY_NAVIGATION),false);
        }
    }

    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void initVideoPlayer(){
        if(!TextUtils.isEmpty(mAudioPath)){
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

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mStrAnswer = s.toString();
        }
    };

    public void bindData(Question data) {
        if(data == null){
            return;
        }
        if(TextUtils.isEmpty(data.getQuestionStem())){
            tvSteam.setVisibility(View.GONE);
        }else{
            tvSteam.setVisibility(View.VISIBLE);
//            tvSteam.setText(RichTextViewHelper.ToDBC(data.getQuestionStem()));
            RichTextViewHelper.setContent(tvSteam,data.getQuestionStem());
        }
        TypefaceHolder.getInstance().setHt(tvContent);
//        RichTextViewHelper.setContent(tvContent,RichTextViewHelper.makeQuestionTitle(
//                data.getQuestionNum(),data.getContent()));
        RichTextViewHelper.setContent(tvContent,RichTextViewHelper.makeQuestionTitle(
                data.getQuestionNum(),RichTextViewHelper.ToDBC(data.getContent())));
    }

    private void initRecyclerView(Question data){
        RecyclerView.LayoutManager  manager = new LinearLayoutManager(getContext());
        ((LinearLayoutManager) manager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemChildClickListener(itemChildClickListener);
        if(data != null){
            mAdapter.setNewData(data.getOptionList());
        }
    }

    private BaseQuickAdapter.OnItemChildClickListener itemChildClickListener =
            new BaseQuickAdapter.OnItemChildClickListener(){

                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    optionIndex = position;
                    switch (view.getId()){
                        case R.id.tv_canvas:
                            Timber.tag(TAG).d("click canvas");
                            if(((HWinputAdapter)mAdapter).isCanvasMethod(position)){
                                ((HWinputAdapter)mAdapter).setCanvasMethod(position,false);
                            }else{
                                mDialog = CanvasDialog.getCanvasDialog(getContext(),HWiptFragment.this);
                                mDialog.show();
                                ((HWinputAdapter)mAdapter).setCanvasMethod(position,true);
                            }

                            break;
//                    case R.id.tv_rechoice:
//                        Timber.tag(TAG).d("click rechoice");
//                        mDialog = CanvasDialog.getCanvasDialog(getContext(),iCanvasEnvent);
//                        mDialog.show();
//                        break;
                        case R.id.tv_camera:
                            mCameraDialog = MediaPickerHelper.getPicSelDialog(getContext(),"图片",HWiptFragment.this);
                            mCameraDialog.show();
                            ((HWinputAdapter)mAdapter).setCanvasMethod(position,true);
                            break;
                    }
                }
            };

    @Override
    public void onBitmapCreate(Bitmap bitmap) {
        if(bitmap != null){
            ((HWinputAdapter)mAdapter).insertBitmap(bitmap,optionIndex);
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
        if(message == null){
            return;
        }
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

    @Override
    public void initeWhiteAnswer(Answer answer) {
        if(answer == null){
            return;
        }

        if(TextUtils.isEmpty(mAudioPath)){
            ((HWinputAdapter)mAdapter).setInitAnsers(answer);
        }else{
            if(answer.getAnswer() != null && answer.getAnswer().size() > 0){
                mStrAnswer = answer.getAnswer().get(0);
                editText.setText(mStrAnswer);
            }
        }
    }

    @Override
    public Answer getAnswer() {
        Question question =  QuestionAnswerHolder.getInstance().getQuestion(this);
        Answer answer;

        if(TextUtils.isEmpty(mAudioPath)){
            answer =  ((HWinputAdapter)mAdapter).getAnswer();
        }else{
            answer = new Answer();
            List<String> strImgs = new ArrayList<>();
            strImgs.add(mStrAnswer);
            answer.setAnswer(strImgs);
        }
        answer.setQuestionId(question.getQuestionId());
        return answer;
    }


    @Override
    public void selectCamera() {
        mCameraFile = FileUriUtil.getTempPicFile(getContext());
        MediaPickerHelper.lunchCamera(this,RQ_CAPTURE, FileUriUtil.getUriFromFile(getContext(),mCameraFile));
    }

    @Override
    public void selectAlbum() {
        MediaPickerHelper.lunchAlbum(this,RQ_ALBUM,1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RQ_CAPTURE && resultCode == Activity.RESULT_OK) {  //拍照返回
            MediaPickerHelper.updateAlbum(getContext(),mCameraFile.getPath());
            mPresenter.onCameraBack(mCameraFile);
        } else if(resultCode == Activity.RESULT_OK && requestCode == RQ_ALBUM){
            mPresenter.onAlbumBack(Matisse.obtainResult(data));
        }
    }
}

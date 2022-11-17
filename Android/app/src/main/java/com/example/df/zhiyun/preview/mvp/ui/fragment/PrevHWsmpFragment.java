package com.example.df.zhiyun.preview.mvp.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.QuestionAnswerHolder;
import com.example.df.zhiyun.app.TypefaceHolder;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.mvp.contract.INavigation;
import com.example.df.zhiyun.mvp.contract.IQuestionLookup;
import com.example.df.zhiyun.preview.di.component.DaggerPrevHWsmpComponent;
import com.example.df.zhiyun.preview.mvp.contract.PrevHWsmpContract;
import com.example.df.zhiyun.preview.mvp.contract.PreviewHomeworkContract;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.mvp.model.entity.QuestionOption;
import com.example.df.zhiyun.preview.mvp.presenter.PrevHWsmpPresenter;
import com.example.df.zhiyun.mvp.ui.widget.ViewLastNextInitHelper;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description: 预览简答题
 * <p>
 * Created by MVPArmsTemplate on 08/13/2019 11:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class PrevHWsmpFragment extends BaseFragment<PrevHWsmpPresenter> implements PrevHWsmpContract.View , IQuestionLookup {
    public static final int RQ_CAPTURE = 87;    //请求拍照
    public static final int RQ_ALBUM = 89;    //请求相册
    @BindView(R.id.tv_input_content)
    HtmlTextView tvContent;
    @BindView(R.id.recyclerView_input)
    RecyclerView recyclerView;

    @BindView(R.id.tv_steam)
    TextView tvSteam;

//    @Inject
//    BaseQuickAdapter mAdapter;
//    @Inject
//    RecyclerView.LayoutManager layoutManager;
//    @Inject
//    RecyclerView.ItemDecoration itemDecoration;

    private int optionIndex = -1;  // 操作的是第几个item
    private Uri mCameraUri;
    Dialog mDialog;

    @Override
    public int getQuestionIndex() {
        return getArguments().getInt("index");
    }

    public static PrevHWsmpFragment newInstance(int index, int total, int nvType) {
        PrevHWsmpFragment fragment = new PrevHWsmpFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index",index);
        bundle.putInt("total",total);
        bundle.putInt(ViewLastNextInitHelper.KEY_NAVIGATION,nvType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerPrevHWsmpComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
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
        List<QuestionOption> questionOptionList = new ArrayList<>();
        QuestionOption questionOption = new QuestionOption();
        questionOptionList.add(questionOption);
        data.setOptionList(questionOptionList);

        initAnalyData(data);
        bindData(data);
//        initRecyclerView(data);
        if(getParentFragment() == null){  //没被嵌套，不是分屏里的小题
            new ViewLastNextInitHelper().init(this,(INavigation)getActivity(),
                    getArguments().getInt(ViewLastNextInitHelper.KEY_NAVIGATION),true);
        }else{
            new ViewLastNextInitHelper().init(this,(INavigation)getParentFragment(),
                    getArguments().getInt(ViewLastNextInitHelper.KEY_NAVIGATION),true);
        }
    }

    private void initAnalyData(Question data){
        recyclerView.setVisibility(View.GONE);
        View llAnaly = getView().findViewById(R.id.ll_pre_hw);
        llAnaly.setVisibility(View.VISIBLE);
        HtmlTextView tvAnswer = llAnaly.findViewById(R.id.tv_answer);
        HtmlTextView tvAnaly = llAnaly.findViewById(R.id.tv_analy);
        RichTextViewHelper.setContent(tvAnswer,data.getAnswer());
        RichTextViewHelper.setContent(tvAnaly,data.getAnalysis());

        Activity activity = getActivity();
        if(activity != null && ((PreviewHomeworkContract.View)activity).isHistoryPaper()){
            TextView tvMyTitle = llAnaly.findViewById(R.id.tv_my_title);
            HtmlTextView tvMyAnswer = llAnaly.findViewById(R.id.tv_my_answer);
            tvMyTitle.setVisibility(View.VISIBLE);
            tvMyAnswer.setVisibility(View.VISIBLE);
            if(TextUtils.isEmpty(data.getStudentAnswer())){
                RichTextViewHelper.setContent(tvMyAnswer,"未作答");
            }else{
                RichTextViewHelper.setContent(tvMyAnswer,data.getStudentAnswer());
            }
        }
    }


    public void bindData(Question data) {
        if(TextUtils.isEmpty(data.getQuestionStem())){
            tvSteam.setVisibility(View.GONE);
        }else{
            tvSteam.setVisibility(View.VISIBLE);
            tvSteam.setText(RichTextViewHelper.ToDBC(data.getQuestionStem()));
        }
        TypefaceHolder.getInstance().setHt(tvContent);
        RichTextViewHelper.setContent(tvContent,RichTextViewHelper.makeQuestionTitle(data.getQuestionNum(),data.getContent()));
    }

//    private void initRecyclerView(Question data){
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.addItemDecoration(itemDecoration);
//        recyclerView.setAdapter(mAdapter);
//        mAdapter.setNewData(data.getOptionList());
//        mAdapter.setOnItemChildClickListener(this);
//    }

//    @Override
//    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//        optionIndex = position;
//        switch (view.getId()){
//            case R.id.tv_canvas:
//                Timber.tag(TAG).d("click canvas");
//                mDialog = CanvasDialog.getCanvasDialog(getContext(),iCanvasEnvent);
//                mDialog.show();
//                break;
//            case R.id.tv_camera:
//                mDialog = MediaPickerHelper.getPicSelDialog(getContext(),"图片",this);
//                mDialog.show();
//                break;
//        }
//    }

//    private CanvasDialog.ICanvasEnvent iCanvasEnvent = new CanvasDialog.ICanvasEnvent(){
//        @Override
//        public void onBitmapCreate(Bitmap bitmap) {
//            if(bitmap != null){
//                ((HWsmpAdapter)mAdapter).insertBitmap(bitmap,optionIndex);
//            }
//        }
//    };

//    @Override
//    public void selectCamera() {
//        mCameraUri = FileUriUtil.getTempPicUri(getContext());
//        MediaPickerHelper.lunchCamera(this,RQ_CAPTURE,mCameraUri);
//    }
//
//    @Override
//    public void selectAlbum() {
//        MediaPickerHelper.lunchAlbum(this,RQ_ALBUM);
//    }


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

//    @Override
//    public void initeWhiteAnswer(Answer answer) {
//        if(answer == null){
//            return;
//        }
//        ((HWsmpAdapter)mAdapter).setInitAnsers(answer);
//    }
//
//    @Override
//    public Answer getAnswer() {
//        Question question = getArguments().getParcelable("data");
//        Answer answer =  ((HWsmpAdapter)mAdapter).getAnswer();
//        answer.setQuestionId(question.getQuestionId());
//        return answer;
//    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RQ_CAPTURE && resultCode == Activity.RESULT_OK) {  //拍照返回
//            parseImageUri(mCameraUri);
//        } else if(resultCode == Activity.RESULT_OK && requestCode == RQ_ALBUM){
//            List<Uri> uris = Matisse.obtainResult(data);
//            if(uris!= null && uris.size()>0){
//                parseImageUri(uris.get(0));
//            }
//        }
//    }
//
//    public void parseImageUri(Uri uri){
//        try {
//            float scale = 1.f/ DeviceUtils.getDensity(getContext());
//            Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
//            Matrix matrix = new Matrix();
//            matrix.postScale(scale, scale);
//            Bitmap mCanvasBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
//                    bitmap.getHeight(), matrix, true);
//            iCanvasEnvent.onBitmapCreate(mCanvasBitmap);
//        }catch (FileNotFoundException e){
//
//        }
//    }
}

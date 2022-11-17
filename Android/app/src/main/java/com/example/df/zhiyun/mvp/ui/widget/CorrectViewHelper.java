package com.example.df.zhiyun.mvp.ui.widget;

import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.AccountManager;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.app.utils.StringCocatUtil;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 批改作业集的view初始化帮助类
 */
public class CorrectViewHelper {
    HtmlTextView tvStandard;
    HtmlTextView tvStudent;
    TextView tvScoreSet;
    TextView tvScore;
    TextView tvUnread;
    TextView tvReaded;

    EditText etComment;

    TextView tvComment;
    TextView tvAvg;
    TextView tvKnowledge;
    HtmlTextView tvMeasure;
    HtmlTextView tvAnaly;

    LinearLayout llRemain;
    FrameLayout flMask;
    TextView tvExpand;


    OptionsPickerView optionsPickerView;

    private ICorrectEvent mICorrectEvent;
    private Fragment mFragment;

    public CorrectViewHelper(Fragment fragment, ICorrectEvent iCorrectEvent){
        mICorrectEvent = iCorrectEvent;
        mFragment = fragment;
    }

    /**
     * 初始化批改信息
     * @param data
     */
    public void initCorrectData(Question data){
        if(data == null ){
            return;
        }
        findView(data);
        bindData(data);
    }

    /**
     *
     * @param data
     */
    private void findView(Question data){
        if(mFragment == null){
            return;
        }

        View view = mFragment.getView();
        tvStandard = view.findViewById(R.id.tv_answer_standard);
        tvStudent = view.findViewById(R.id.tv_answer_student);
        tvScoreSet = view.findViewById(R.id.tv_score_set);
        tvScore = view.findViewById(R.id.tv_score);
        tvUnread = view.findViewById(R.id.tv_unread);
        tvReaded = view.findViewById(R.id.tv_readed);

        if(data.getStudentAnswerStatus() == Api.STUDENT_ANSWER_STATUS_UNREAD) {  //未批
            if(AccountManager.getInstance().getUserInfo().getRoleId() == Api.TYPE_TEACHER){   //当前教师
                View viewStub = ((ViewStub)view.findViewById(R.id.stub_comment)).inflate();
                etComment = viewStub.findViewById(R.id.et_comment);
            }else{ //当前学生

            }
        }else { //已批
            View viewStub = ((ViewStub)view.findViewById(R.id.stub_analy)).inflate();
            tvComment = viewStub.findViewById(R.id.tv_comment);
            tvAvg = viewStub.findViewById(R.id.tv_avg_score);
            tvKnowledge = viewStub.findViewById(R.id.tv_knowledge);
            tvMeasure = viewStub.findViewById(R.id.tv_gold);
            tvAnaly = viewStub.findViewById(R.id.tv_question_analy);

            llRemain = viewStub.findViewById(R.id.ll_ll_remain);
            flMask = viewStub.findViewById(R.id.fl_analy_mask);
            tvExpand = viewStub.findViewById(R.id.tv_expand);
        }
    }

    private void bindData(Question data){
        RichTextViewHelper.setContent(tvStandard,data.getAnswer());
        if(TextUtils.isEmpty(data.getStudentAnswer())){
            RichTextViewHelper.setContent(tvStudent,"未答");
        }else{
            RichTextViewHelper.setContent(tvStudent,data.getStudentAnswer());
        }


        if(data.getStudentAnswerStatus() == Api.STUDENT_ANSWER_STATUS_UNREAD){  //未批

            List<String> options = makeScoreOptions(data.getQuestionSum());
            if(options.size() > 0){
                optionsPickerView = PickViewHelper.getOptionPicker(mFragment.getContext(),options
                        ,optionsSelectListener);
            }

            if(AccountManager.getInstance().getUserInfo().getRoleId() == Api.TYPE_TEACHER){   //当前教师
                if(data.isScorreChanged()){
                    tvUnread.setVisibility(View.GONE);
                    tvReaded.setVisibility(View.VISIBLE);
                }else{
                    tvUnread.setVisibility(View.VISIBLE);
                    tvReaded.setVisibility(View.GONE);
                }

                String initScore = Integer.toString((int)(data.getStudentScore()));
                tvScoreSet.setText(initScore);
                tvScoreSet.setVisibility(View.VISIBLE);
                tvScoreSet.setOnClickListener(clickListener);
                etComment.addTextChangedListener(textWatcher);
                etComment.setText(data.getComment());
                etComment.setSelection(etComment.length());
            }else{ //当前学生
                tvUnread.setVisibility(View.VISIBLE);
            }
        }else{ //已批
            tvReaded.setVisibility(View.VISIBLE);
            tvScore.setVisibility(View.VISIBLE);
            tvScore.setText(""+data.getStudentScore());
            if(TextUtils.isEmpty(data.getComment())){
                tvComment.setText(StringCocatUtil.concat("批注: ","暂无"));
            }else{
                tvComment.setText(StringCocatUtil.concat("批注: ",data.getComment()));
            }
            tvAvg.setText(StringCocatUtil.concat("平均分: ",Float.toString(data.getAvg()),"分 (答对",
                    Integer.toString(data.getCorrectCount()),"人/答错",Integer.toString(data.getErrorCount())
                    ,"人/班级得分率",data.getScoring(),")"));
            tvKnowledge.setText(StringCocatUtil.concat("知识内容: ",data.getKnowledgeSystem()));

            RichTextViewHelper.setContent(tvMeasure,StringCocatUtil.concat("测量目标: ",data.getMeasureTarget()).toString());
            RichTextViewHelper.setContent(tvAnaly,StringCocatUtil.concat("解析: ",data.getAnalysis()).toString());

            hideDetailWhenStudent(data);
        }
    }

    /**
     * 当是学生时，先隐藏，如果点击展开后就一直展开
     */
    private void hideDetailWhenStudent(Question data){
//        if(AccountManager.getInstance().getUserInfo().getRoleId() != Api.TYPE_STUDENT){
//            return;
//        }

        if(!data.isExpand()){
            llRemain.setVisibility(View.GONE);
            flMask.setVisibility(View.VISIBLE);
            tvExpand.setOnClickListener(clickListener);
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(mICorrectEvent != null){
                mICorrectEvent.onCommentChange(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_score_set:
                    if(optionsPickerView != null ){
                        optionsPickerView.show();
                    }
                    break;
                case R.id.tv_expand:
                    if(llRemain != null && flMask != null){
                        flMask.setVisibility(View.GONE);
                        llRemain.setVisibility(View.VISIBLE);
                    }

                    if(mICorrectEvent != null){
                        mICorrectEvent.onExpand();
                    }
                    break;
            }
        }
    };

    private List<String> makeScoreOptions(float score){
        List<String> options = new ArrayList<>();
        if(score > 0){
            int max = (int)(Math.ceil(score));
            for(int i=0;i<= max;i++){
                options.add(""+i);
            }
        }
        return options;
    }

    private OnOptionsSelectListener optionsSelectListener = new OnOptionsSelectListener(){
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
            tvScoreSet.setText(""+options1);
            tvReaded.setVisibility(View.VISIBLE);
            tvUnread.setVisibility(View.INVISIBLE);
            if(mICorrectEvent != null){
                mICorrectEvent.onScoreChange(options1);
            }
        }
    };

    public void onDestroy(){
        if(optionsPickerView != null && optionsPickerView.isShowing()){
            optionsPickerView.dismiss();
        }

        if(etComment != null){
            etComment.removeTextChangedListener(textWatcher);
        }

    }

    public interface ICorrectEvent{
        void onScoreChange(int value);
        void onCommentChange(String value);
        void onExpand();
    }
}

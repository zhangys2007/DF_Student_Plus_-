package com.example.df.zhiyun.correct.mvp.ui.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.mvp.ui.adapter.BFragmentStatePagerAdapter;
import com.example.df.zhiyun.correct.mvp.ui.fragment.CorrectHWcomFragment;
import com.example.df.zhiyun.correct.mvp.ui.fragment.CorrectHWcpFragment;
import com.example.df.zhiyun.correct.mvp.ui.fragment.CorrectHWiptFragment;
import com.example.df.zhiyun.correct.mvp.ui.fragment.CorrectHWlistenFragment;
import com.example.df.zhiyun.correct.mvp.ui.fragment.CorrectHWmultFragment;
import com.example.df.zhiyun.correct.mvp.ui.fragment.CorrectHWselFragment;
import com.example.df.zhiyun.correct.mvp.ui.fragment.CorrectHWsepFragment;
import com.example.df.zhiyun.correct.mvp.ui.fragment.CorrectHWsmpFragment;
import com.example.df.zhiyun.mvp.ui.widget.ViewLastNextInitHelper;

import java.util.ArrayList;
import java.util.List;

/***
 * 预览各种题型的adapter
 */
public class CorrectHWAdapter extends BFragmentStatePagerAdapter {
    List<Question> datas ;
    int parentNvType = ViewLastNextInitHelper.TYPE_NONE;
    public CorrectHWAdapter(FragmentManager fragmentManager, List<Question> list) {
        super(fragmentManager);
        if(list == null){
            this.datas = new ArrayList<>();
        }else{
            this.datas = list;
        }
    }

    public void setParentNavigationType(int value){
        parentNvType = value;
    }

    @Override
    public Fragment getItem(int position) {
        Question question = datas.get(position);
        Fragment fragment ;
        int nvType = ViewLastNextInitHelper.parseNavigatioType(position,getCount(),parentNvType);

        if(TextUtils.equals(question.getQuestionType(),"" + Api.QUESTION_SELECT)){
            fragment = CorrectHWselFragment.newInstance(position,datas.size(),nvType);
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_INPUT)){
            fragment = CorrectHWiptFragment.newInstance(position,datas.size(),nvType);
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_BIG)){   //如果大题下没子问题就转化为简答题
            if(question.getSubQuestion() == null || question.getSubQuestion().size() == 0){
                fragment = CorrectHWsmpFragment.newInstance(position,datas.size(),nvType);
            }else{
                fragment = CorrectHWcpFragment.newInstance(position,datas.size(),nvType);
            }
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_SIMPLE)){
            fragment = CorrectHWsmpFragment.newInstance(position,datas.size(),nvType);
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_CHINESE_BIG)){
            fragment = CorrectHWsepFragment.newInstance(position,datas.size(),nvType);
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_COMPOSITION)){
            fragment = CorrectHWcomFragment.newInstance(position,datas.size(),nvType);
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_LISTEN)){
            fragment = CorrectHWlistenFragment.newInstance(position,datas.size(),nvType);
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_MULTIPLE_SELECT)){
            fragment = CorrectHWmultFragment.newInstance(position,datas.size(),nvType);
        }else{
            fragment = CorrectHWcomFragment.newInstance(position,datas.size(),nvType);
        }
        return fragment;
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return datas.get(position).getQuestionId();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    public ArrayList<Fragment> getFragmentList(){
        return mFragments;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }
}

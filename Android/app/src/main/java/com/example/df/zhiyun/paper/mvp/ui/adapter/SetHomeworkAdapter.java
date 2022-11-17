package com.example.df.zhiyun.paper.mvp.ui.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.mvp.ui.adapter.BFragmentStatePagerAdapter;
import com.example.df.zhiyun.paper.mvp.ui.fragment.HWcomFragment;
import com.example.df.zhiyun.paper.mvp.ui.fragment.HWmultFragment;

import java.util.ArrayList;
import java.util.List;

import com.example.df.zhiyun.paper.mvp.ui.fragment.HWcpFragment;
import com.example.df.zhiyun.paper.mvp.ui.fragment.HWiptFragment;
import com.example.df.zhiyun.paper.mvp.ui.fragment.HWlistenFragment;
import com.example.df.zhiyun.paper.mvp.ui.fragment.HWselFragment;
import com.example.df.zhiyun.paper.mvp.ui.fragment.HWsepFragment;
import com.example.df.zhiyun.paper.mvp.ui.fragment.HWsmpFragment;
import com.example.df.zhiyun.mvp.ui.widget.ViewLastNextInitHelper;

/***
 * 做作业  各种题型的adapter
 */
public class SetHomeworkAdapter extends BFragmentStatePagerAdapter {
    List<Question> datas ;
    int parentNvType = ViewLastNextInitHelper.TYPE_NONE;
    public SetHomeworkAdapter(FragmentManager fragmentManager, List<Question> list) {
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
            fragment = HWselFragment.newInstance(position,datas.size(),nvType);
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_INPUT)){
            fragment = HWiptFragment.newInstance(position,datas.size(),nvType);
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_BIG)){   //如果大题下没子问题就转化为简答题
            if(question.getSubQuestion() == null || question.getSubQuestion().size() == 0){
                fragment = HWsmpFragment.newInstance(position,datas.size(),nvType);
            }else{
                fragment = HWcpFragment.newInstance(position,datas.size(),nvType);
            }
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_SIMPLE)){
            fragment = HWsmpFragment.newInstance(position,datas.size(),nvType);
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_CHINESE_BIG)){
            fragment = HWsepFragment.newInstance(position,datas.size(),nvType);
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_COMPOSITION)){
            fragment = HWcomFragment.newInstance(position,datas.size(),nvType);
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_LISTEN)){
            fragment = HWlistenFragment.newInstance(position,datas.size(),nvType);
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_MULTIPLE_SELECT)){
            fragment = HWmultFragment.newInstance(position,datas.size(),nvType);
        }else{
            fragment = HWcomFragment.newInstance(position,datas.size(),nvType);
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

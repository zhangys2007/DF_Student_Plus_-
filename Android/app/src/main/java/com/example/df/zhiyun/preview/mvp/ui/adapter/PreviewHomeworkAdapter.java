package com.example.df.zhiyun.preview.mvp.ui.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;

import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.mvp.ui.adapter.BFragmentStatePagerAdapter;
import com.example.df.zhiyun.preview.mvp.ui.fragment.PrevHWcomFragment;
import com.example.df.zhiyun.preview.mvp.ui.fragment.PrevHWcpFragment;
import com.example.df.zhiyun.preview.mvp.ui.fragment.PrevHWiptFragment;
import com.example.df.zhiyun.preview.mvp.ui.fragment.PrevHWlistenFragment;
import com.example.df.zhiyun.preview.mvp.ui.fragment.PrevHWmultFragment;
import com.example.df.zhiyun.preview.mvp.ui.fragment.PrevHWselFragment;
import com.example.df.zhiyun.preview.mvp.ui.fragment.PrevHWsepFragment;
import com.example.df.zhiyun.preview.mvp.ui.fragment.PrevHWsmpFragment;
import com.example.df.zhiyun.mvp.ui.widget.ViewLastNextInitHelper;

import java.util.ArrayList;
import java.util.List;

/***
 * 预览各种题型的adapter
 */
public class PreviewHomeworkAdapter extends BFragmentStatePagerAdapter {
    List<Question> datas ;
    int parentNvType = ViewLastNextInitHelper.TYPE_NONE;
    public PreviewHomeworkAdapter(FragmentManager fragmentManager, List<Question> list) {
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
            fragment = PrevHWselFragment.newInstance(position,datas.size(),nvType);
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_INPUT)){
            fragment = PrevHWiptFragment.newInstance(position,datas.size(),nvType);
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_BIG)){   //如果大题下没子问题就转化为简答题
            if(question.getSubQuestion() == null || question.getSubQuestion().size() == 0){
                fragment = PrevHWsmpFragment.newInstance(position,datas.size(),nvType);
            }else{
                fragment = PrevHWcpFragment.newInstance(position,datas.size(),nvType);
            }
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_SIMPLE)){
            fragment = PrevHWsmpFragment.newInstance(position,datas.size(),nvType);
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_CHINESE_BIG)){
            fragment = PrevHWsepFragment.newInstance(position,datas.size(),nvType);
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_COMPOSITION)){
            fragment = PrevHWcomFragment.newInstance(position,datas.size(),nvType);
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_LISTEN)){
            fragment = PrevHWlistenFragment.newInstance(position,datas.size(),nvType);
        }else if(TextUtils.equals(question.getQuestionType(),"" +Api.QUESTION_MULTIPLE_SELECT)){
            fragment = PrevHWmultFragment.newInstance(position,datas.size(),nvType);
        }else{
            fragment = PrevHWcomFragment.newInstance(position,datas.size(),nvType);
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

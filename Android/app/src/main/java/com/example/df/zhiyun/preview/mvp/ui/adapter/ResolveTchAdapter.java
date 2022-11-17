package com.example.df.zhiyun.preview.mvp.ui.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.mvp.ui.adapter.BFragmentStatePagerAdapter;
import com.example.df.zhiyun.preview.mvp.ui.fragment.ResolveTchOthFragment;
import com.example.df.zhiyun.preview.mvp.ui.fragment.ResolveTchSelFragmentFragment;

import java.util.ArrayList;
import java.util.List;

/***
 * 各种题型解析的adapter
 */
public class ResolveTchAdapter extends BFragmentStatePagerAdapter {
    List<Question> datas ;

    public ResolveTchAdapter(FragmentManager fragmentManager, List<Question> list) {
        super(fragmentManager);
        if(list == null){
            this.datas = new ArrayList<>();
        }else{
            this.datas = list;
        }
    }


    @Override
    public Fragment getItem(int position) {
        if(position %2 == 0){
            return  ResolveTchSelFragmentFragment.newInstance(false);
        }else{
            return new ResolveTchOthFragment().newInstance(false);
        }
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return Integer.toString(position);
//        return datas.get(position).getQuestionId();
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

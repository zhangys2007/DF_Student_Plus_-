package com.example.df.zhiyun.mvp.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private List<View> viewpages;

    public ViewPagerAdapter(List<View> viewpages) {
        this.viewpages = viewpages;
    }

    @Override
    public int getCount() {
        return viewpages.size();
    }

    public void destroyItem(ViewGroup view, int position, Object object) {

        view.removeView(viewpages.get(position));

    }

    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewpages.get(position), 0);
        return viewpages.get(position);
    }

}
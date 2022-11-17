package com.example.df.zhiyun.mvp.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

/**
 * Created by huangyi on 17-7-2.
 */

public class PhotoViewAdapter extends PagerAdapter {
    Context mContext;
    List<String> mUrls;
    ImageLoader imageLoader ;

    public PhotoViewAdapter(Context context, List<String> urls){
        mContext = context;
        mUrls = urls;
        AppComponent appComponent = ArmsUtils.obtainAppComponentFromContext(context);
        imageLoader = appComponent.imageLoader();
    }

    @Override
    public int getCount() {
        return mUrls.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(container.getContext());
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageLoader.loadImage(mContext,
                ImageConfigImpl
                        .builder()
                        .url(mUrls.get(position))
                        .imageView(photoView)
                        .build());

        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
package com.example.df.zhiyun.mvp.ui.common;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.mvp.IPresenter;

/***
 * 实现懒加载功能的fragment
 */

public abstract class BasePageFragment<P extends IPresenter> extends BaseFragment<P> {
    //该页面是否已经准备完毕
    private boolean isPrepared;
    //该页面是否已经执行过懒加载
    private boolean isLazyLoaded;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoad();
    }

    private void lazyLoad(){
        if(getUserVisibleHint() && isPrepared && !isLazyLoaded){
            onLazyLoaded();
            isLazyLoaded = true;
        }
    }

    /***
     * 只在第一次页面可见的时候调用
     */
    public abstract void onLazyLoaded();
}

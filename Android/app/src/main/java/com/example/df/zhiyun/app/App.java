package com.example.df.zhiyun.app;

import com.jess.arms.base.BaseApplication;
//import com.lzx.starrysky.manager.MusicManager;
//import com.lzx.starrysky.notification.NotificationConstructor;
//import com.lzx.starrysky.playback.download.ExoDownload;

import com.example.df.zhiyun.di.component.DaggerUserInfoComponent;
import com.example.df.zhiyun.di.component.UserInfoComponent;

public class App extends BaseApplication {
    private UserInfoComponent mUserInfoComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mUserInfoComponent = DaggerUserInfoComponent.builder().build();

//        initMusicPlayer();
    }

    public UserInfoComponent getUserInfoComponent(){
        return mUserInfoComponent;
    }
}

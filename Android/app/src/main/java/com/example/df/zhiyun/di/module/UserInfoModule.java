package com.example.df.zhiyun.di.module;


import com.example.df.zhiyun.mvp.model.entity.UserInfo;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class UserInfoModule {
    @Singleton
    @Provides
    static UserInfo provideUserList() {

        return new UserInfo();
    }
}

package com.example.df.zhiyun.di.component;


import com.example.df.zhiyun.di.module.UserInfoModule;
import com.example.df.zhiyun.mvp.model.entity.UserInfo;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {UserInfoModule.class})
public interface UserInfoComponent {
    UserInfo getUserInfo();
}

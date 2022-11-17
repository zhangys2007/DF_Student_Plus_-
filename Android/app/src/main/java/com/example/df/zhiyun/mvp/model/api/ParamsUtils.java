package com.example.df.zhiyun.mvp.model.api;

import android.content.Context;
import android.text.TextUtils;

import com.example.df.zhiyun.mvp.model.entity.UserInfo;
import com.google.gson.Gson;
import com.jess.arms.base.BaseApplication;

import java.util.Map;

import com.example.df.zhiyun.app.AccountManager;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/***
 * 用于生成请求体,  在这里统一注入token
 */
public class ParamsUtils {
    public static RequestBody fromMap(Context context, Map<String,Object> params){
        String token = AccountManager.getInstance().getToken(context);
        if(!TextUtils.isEmpty(token)){
            params.put("token", token);
        }
        UserInfo info = AccountManager.getInstance().getUserInfo();

        if(info != null){
            params.put("schoolId", Integer.toString(info.getSchoolId()));
        }


        Gson gson = ((BaseApplication)context.getApplicationContext()).getAppComponent().gson();
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),gson.toJson(params));
    }

    //不自动注入token
    public static RequestBody fromMapRaw(Context context, Map<String,Object> params){
        Gson gson = ((BaseApplication)context.getApplicationContext()).getAppComponent().gson();
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),gson.toJson(params));
    }

    public static RequestBody fromMap(Gson gson, Map<String,Object> params){
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),gson.toJson(params));
    }
}

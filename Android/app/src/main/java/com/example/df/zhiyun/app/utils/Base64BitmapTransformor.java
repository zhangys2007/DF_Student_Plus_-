package com.example.df.zhiyun.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.ArmsUtils;

import java.io.ByteArrayOutputStream;

import com.example.df.zhiyun.R;


public class Base64BitmapTransformor {
    public static final String BASE64_HEAD = "data:image/png;base64,";


    public static Bitmap getBitmap(String data){
        if(TextUtils.isEmpty(data)){
            return null;
        }
        Bitmap decodedByte = null;

        try {
            data = data.replace(BASE64_HEAD,"");
            byte[] decodedString = Base64.decode(data, Base64.DEFAULT);
            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }catch (Exception e){

        }

        return decodedByte;
    }
    public static Bitmap getBitmap(String data,Context context){
        if(TextUtils.isEmpty(data)){
            return null;
        }
        Bitmap decodedByte = null;

        try {
            data = data.replace(BASE64_HEAD,"");
            byte[] decodedString = Base64.decode(data, Base64.DEFAULT);
            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }catch (Exception e){

        }

        return decodedByte;
    }

    //学生的头像
    public static Bitmap getThumb(String data, Context context){
        AppComponent mAppComponent = ArmsUtils.obtainAppComponentFromContext(context);
        Cache<String,Object> cache = mAppComponent.extras();

        Bitmap thumb = null;

        try {
            data = data.replace(BASE64_HEAD,"");
            byte[] decodedString = Base64.decode(data, Base64.DEFAULT);
            thumb = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);    // TODO: 2019-09-05 这里会引发oom，还不知道怎么改
        }catch (Exception e){

        }

        if(thumb == null){
            thumb = (Bitmap)(cache.get("default_thumb"));
            if(thumb == null){
                thumb =BitmapFactory.decodeResource(context.getResources(), R.mipmap.thumb);
                cache.put("default_thumb",thumb);
            }
        }
        return thumb;
    }

    //教师的头像
    public static Bitmap getThumbTch(String data, Context context){
        AppComponent mAppComponent = ArmsUtils.obtainAppComponentFromContext(context);
        Cache<String,Object> cache = mAppComponent.extras();

        Bitmap thumb = null;

        try {
            data = data.replace(BASE64_HEAD,"");
            byte[] decodedString = Base64.decode(data, Base64.DEFAULT);
            thumb = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }catch (Exception e){

        }

        if(thumb == null){
            thumb = (Bitmap)(cache.get("default_thumb_tch"));
            if(thumb == null){
                thumb =BitmapFactory.decodeResource(context.getResources(), R.mipmap.thumb_tch);
                cache.put("default_thumb_tch",thumb);
            }
        }
        return thumb;
    }

    public static String getString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
        return BASE64_HEAD + new String(encode);
    }

    public static String getCompressString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
        return BASE64_HEAD + new String(encode);
    }
}

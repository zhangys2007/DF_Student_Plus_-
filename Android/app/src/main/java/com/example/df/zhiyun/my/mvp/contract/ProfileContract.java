package com.example.df.zhiyun.my.mvp.contract;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.UserInfo;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/21/2019 15:09
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface ProfileContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void bindData();
        void lunchCamera(Uri uri);
        void lunchAlbum();
        //申请权限
        RxPermissions getRxPermissions();
        void cropPic(Uri source,Uri dest);

        Activity getMyActivity();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<UserInfo>> updateUserProfile(Map<String,Object> map);
        void updateLocalInfo(UserInfo info);
        Observable<BaseResponse<UserInfo>> uploadPic(Bitmap bitmap);
    }
}

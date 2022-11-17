package com.example.df.zhiyun.mvp.contract;


import android.support.v4.app.Fragment;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.ErrorDetail;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/02/2019 09:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface EQDetailContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        String KEY = "questionId";
        String KEY_SUBJID = "subjId";
        String KEY_HIDE_MY = "hide";
        void setFragment(Fragment fragment);
        void setStore(boolean store);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<List<ErrorDetail>>> getErrorDetail(String id, String subjId);
        Observable<BaseResponse> updateCollection(String questionId,boolean collecte);
    }
}

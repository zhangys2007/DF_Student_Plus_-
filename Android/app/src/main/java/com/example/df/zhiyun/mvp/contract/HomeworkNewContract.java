package com.example.df.zhiyun.mvp.contract;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.Homework;
import com.example.df.zhiyun.mvp.model.entity.Subject;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/22/2019 11:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface HomeworkNewContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        Context getPageContext();
        Fragment getFragment();
        void getInitData();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<List<Homework>>> getHomework(int page, int subjectId,
                                                             String beginTime, String endTime);

        Observable<BaseResponse<List<Subject>>> getSubject();
    }
}

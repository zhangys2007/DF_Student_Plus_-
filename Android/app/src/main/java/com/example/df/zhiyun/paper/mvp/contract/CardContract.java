package com.example.df.zhiyun.paper.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.example.df.zhiyun.mvp.model.adapterEntity.CardMultipleItem;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;

import java.util.List;

import io.reactivex.Observable;

import com.example.df.zhiyun.mvp.model.entity.PaperAnswerSet;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2019 12:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface CardContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        String KEY_STD_HW_ID = "studentHomeWorkId";
        String KEY_HW_ID = "homeWorkId";
        String KEY_UUID = "uuid";
        String KEY_SUBJECTID = "subjectId";
        String KEY_SCHOOLID = "schoolId";
        String KEY_JOB_TYPE = "jobType";
        String KEY_TIME_REMAIN = "timeRemain";

        void bindData(String title, List<CardMultipleItem> items);
        void showSubmitDialog();
        void showTimeoutDialog();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse> submitHomeWork(String studentHomeWorkId,String homeworkId,
                                                String uuid,int type,int subjectId,String schoolId);
        Observable<BaseResponse<PaperAnswerSet>> answerSet(String studentHomeWorkId,int subjectId,
                                                           String schoolId,int jobType);
    }
}

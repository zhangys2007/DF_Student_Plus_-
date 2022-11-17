package com.example.df.zhiyun.paper.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;
import com.example.df.zhiyun.mvp.model.entity.Answer;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.HomeworkSet;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/03/2019 14:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface SetHomeworkContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        String KEY_STD_HW_ID = "studentHomeWorkId";
        String KEY_HW_ID = "homeWorkId";
        String KEY_UUID = "uuid";
        String KEY_SUBJECTID = "subjectId";
        String KEY_SCHOOLID = "schoolId";
        String KEY_JOB_TYPE = "jobType";

        void initViewPager(List<Question> questions);
        void updateCountTime(long value);
        Answer getAnswerFromQuestion(int index);
        void initQuestionWithAnswer(int index, Answer answer);
        void showQuitDialog();
        RxPermissions getRxPermissions();
        void changeViewpagerSelect(int index);
        int getCurrentQuestionIndex();
        String getListenerBaseUrl();
        void disableOption();
        void updateTitle(String title);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<HomeworkSet>> studentDoHomework(String studentHomeWorkId, String uuid,
                                                                int subjectId,int jobType, String schoolId);
        Observable<BaseResponse> submitQuestion(String studentHomeWorkId,String questionId,String uuid,
                                                int type, Answer answer,String schoolId);
        Observable<BaseResponse> submitHomeWork(String studentHomeWorkId,String homeworkId,
                                                String uuid,int type,int subjectId,String schoolId);
    }
}

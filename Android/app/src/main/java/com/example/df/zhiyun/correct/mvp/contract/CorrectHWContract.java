package com.example.df.zhiyun.correct.mvp.contract;

import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.HomeworkSet;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

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
public interface CorrectHWContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        public static final String KEY_ID = "homeworkId";
        public static final String KEY_TYPE = "type";
        public static final String KEY_STUDENT_COUNT = "studentCount";
        public static final String KEY_CORRECT = "correct";

        void changeViewpagerSelect(int index);
        void initViewPager(List<Question> questions);
        int getCurrentQuestionIndex();
        String getListenerBaseUrl();
        boolean isCorrected();
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<HomeworkSet>> homeworkCorrecting(int type, String studentHomeworkId, int mStudentCount);
        Observable<BaseResponse> questionCorrect(Question question, String subjectId, String studentHomeworkId);

    }
}

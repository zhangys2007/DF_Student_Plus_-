package com.example.df.zhiyun.mvp.contract;

import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.ScoreCompear;
import com.example.df.zhiyun.mvp.model.entity.ScoreCompearLayer;
import com.example.df.zhiyun.mvp.model.entity.ScoreCompearLevel;
import com.example.df.zhiyun.mvp.model.entity.ScoreCompearSumary;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 13:36
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface WholeCompearContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        String KEY_HOMEWORK_ID = "homeworkId";
        String KEY_FZ = "fz";
        String KEY_TYPE = "type";
        String KEY_SCHOOLID = "school";
        String KEY_GRADEID = "gradeId";
        String KEY_SUBJID = "subjId";

        void bindSumary(List<ScoreCompearSumary> list);

        void bindLevel(List<ScoreCompearLevel> list);

        void bindLayout(List<ScoreCompearLayer> list);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<ScoreCompear>> getScoreCompear(int fzPaperId, int type, int gradeId, int schoolId);
    }
}

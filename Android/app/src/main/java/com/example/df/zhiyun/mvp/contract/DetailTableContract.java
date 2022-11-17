package com.example.df.zhiyun.mvp.contract;


import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.DetailTable;
import com.example.df.zhiyun.mvp.model.entity.KnowledgeGrasp;
import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

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
public interface DetailTableContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        String KEY_HOMEWORK_ID = "homeworkId";
        String KEY_FZ = "fz";
        String KEY_TYPE = "type";
        String KEY_SCHOOLID = "school";
        String KEY_GRADEID = "gradeId";
        String KEY_SUBJID = "subjId";
    }

    interface Model extends IModel {
        Observable<BaseResponse<List<DetailTable>>> getDetailTable(String homeworkId, int fzPaperId, int type, int gradeId, int schoolId, int subjectId);
    }
}

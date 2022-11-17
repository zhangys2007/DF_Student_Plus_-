package com.example.df.zhiyun.plan.mvp.contract;

import com.example.df.zhiyun.mvp.model.entity.AnalysisDict;
import com.example.df.zhiyun.mvp.model.entity.BaseResponse;
import com.example.df.zhiyun.mvp.model.entity.FilterGrade;
import com.example.df.zhiyun.mvp.model.entity.HomeworkState;
import com.example.df.zhiyun.mvp.model.entity.PlanUsage;
import com.example.df.zhiyun.mvp.model.entity.Tag;
import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 10/30/2019 15:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public interface PlanUsingContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void initPickViewData(List<Tag> tags,boolean isClass);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        Observable<BaseResponse<AnalysisDict>> findAnalysisDict();
        Observable<BaseResponse<List<PlanUsage>>> planUsage(String userName, Map<String,Object> gradeClassId, int type, int page) ;
    }
}

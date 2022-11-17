package com.example.df.zhiyun.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.df.zhiyun.mvp.ui.widget.flexiblerichtextview.htmltextview.HtmlTextView;

import java.util.List;

import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.RichTextViewHelper;
import com.example.df.zhiyun.app.utils.TimeUtils;
import com.example.df.zhiyun.mvp.model.adapterEntity.SearchMultipleItem;
import com.example.df.zhiyun.mvp.model.api.Api;
import com.example.df.zhiyun.mvp.model.entity.Homework;
import com.example.df.zhiyun.mvp.model.entity.Question;
import com.example.df.zhiyun.mvp.model.entity.QuestionOption;

/**
 * 首页错题列表和我的收藏共用这个adapter, 各种选择题，填空题，都用选择题来渲染
 */
public class SearchAdapter extends BaseMultiItemQuickAdapter<SearchMultipleItem, BaseViewHolder> {
    String strCount;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public SearchAdapter(List<SearchMultipleItem> data) {
        super(data);
        addItemType(Api.SEARCH_HOMEWORK, R.layout.item_homework);
        addItemType(Api.SEARCH_PAPER, R.layout.item_question_select);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchMultipleItem item) {
        switch (helper.getItemViewType()) {
            case Api.SEARCH_HOMEWORK:
                renderHomework(helper,item);
                break;
            default:
                renderPaper(helper,item);
                break;
        }
    }

    private void renderHomework(BaseViewHolder helper, SearchMultipleItem data){
        Homework item = (Homework)data.getData();
        helper.setText(R.id.tv_do,mContext.getString(item.getStatus()==0?R.string.undo:R.string.done))
                .setText(R.id.tv_subjuet_name, item.getSubject())
                .setText(R.id.tv_correct,mContext.getString(item.getCorrectionStatus()==0?R.string.uncorrected:R.string.corrected))
                .setText(R.id.tv_homewwork_name,item.getHomeworkName());

        try {
            helper.setText(R.id.tv_time_start, TimeUtils.getYmdhm(Long.parseLong(item.getBeginTime())))
                    .setText(R.id.tv_time_end, TimeUtils.getYmdhm(Long.parseLong(item.getEndTime())));
        }catch (NumberFormatException e){

        }
    }


    private void renderPaper(BaseViewHolder helper, SearchMultipleItem item){
        Question question = (Question)(item.getData());
        StringBuilder strContent = new StringBuilder();
        if(question.getOptionList() != null){
            for(int index=0 ; index < question.getOptionList().size();index++){
                QuestionOption questionOption = question.getOptionList().get(index);
                strContent.append(questionOption.getOption()).append("   ").append(questionOption.getOptionContent());
                if(index < question.getOptionList().size()-1){
                    strContent.append('\n');
                }
            }
        }

        helper
//                .setText(R.id.tv_content, strContent.toString())
//                .setGone(R.id.tv_content, TextUtils.isEmpty(strContent.toString()))
                .setText(R.id.tv_from,question.getSource());

        HtmlTextView tvName = (HtmlTextView)helper.getView(R.id.tv_name);
        RichTextViewHelper.setContent(tvName,question.getContent());
    }
}

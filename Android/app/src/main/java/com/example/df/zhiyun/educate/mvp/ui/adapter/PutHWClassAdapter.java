/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.df.zhiyun.educate.mvp.ui.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.df.zhiyun.R;
import com.example.df.zhiyun.app.utils.CompoundDrawableUtil;
import com.example.df.zhiyun.common.mvp.model.entity.CommonExpandableItem;
import com.example.df.zhiyun.educate.mvp.model.entity.BelongClass;
import com.example.df.zhiyun.mvp.model.entity.PutStudent;
import com.jess.arms.base.DefaultAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法   布置作业时选择班级列表
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class PutHWClassAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int LEVEL_class = 0;
    public static final int LEVEL_student = 1;

    private Map<String,BelongClass> selClass = new HashMap<>();
    private Map<String,String> selStudent = new HashMap<>();

    /**
     * 展开或者收缩班级
     * @param position
     */
    public void expanClassItem(int position){
        CommonExpandableItem data = (CommonExpandableItem)getData().get(position);
        if(data.getLevel() == 0){ //班级项
            if(data.isExpanded()){
                collapse(position);
            }else{
                expand(position);
            }
        }
    }

    /**
     * 选择或者取消班级选择
     * @param position
     */
    public void selCls(int position){
        CommonExpandableItem data = (CommonExpandableItem)getData().get(position);
        BelongClass belongClass = (BelongClass)data.getData();

        if(belongClass.getIsEndPut() == 1){  //已全部布完就不能再操作
            return;
        }

        if(belongClass.getIsArrangement() > 0){  //已给部分学生布置鹿就不能再按整体布置
            return;
        }


        String id = belongClass.getClassId();
        if(selClass.containsKey(id)){
            selClass.remove(id);
            selAllStd(data,false);
        }else{
            selClass.put(id,belongClass);
            selAllStd(data,true);
        }

        if(data.isExpanded()){
            notifyItemRangeChanged(position+1,data.getSubItems().size());
        }
        notifyItemChanged(position);
    }

    /**
     * 选择或者取消整个班级的学生
     * @param data
     */
    private void selAllStd(CommonExpandableItem data,boolean sel){
        List<CommonExpandableItem> subItems = data.getSubItems();
        if(subItems == null || subItems.size() == 0){
            return;
        }

        for(CommonExpandableItem item : subItems){
            PutStudent studentItem = (PutStudent) item.getData();
            if(sel){
                selStudent.put(studentItem.getId(),studentItem.getId());
            }else{
                selStudent.remove(studentItem.getId());
            }
        }
    }

    /**
     * 选择或者取消学生选择
     * @param position
     */
    public void selStd(int position){
        CommonExpandableItem data = (CommonExpandableItem)getData().get(position);
        PutStudent studentItem = (PutStudent)(data.getData());
        String id = studentItem.getId();

        if(studentItem.getIsput() > 0){ //已经布置过了
            return;
        }

        if(selStudent.containsKey(id)){
            selStudent.remove(id);
        }else{
            selStudent.put(id,id);
        }
        notifyItemChanged(position);
    }


    public Map<String,Object> getSelectedParams(){
        Map<String,Object> params = new HashMap<>();

        if(selClass.size() >0 ){
            StringBuilder builder = new StringBuilder("");
            for(String cId: selClass.keySet()){
                if(builder.length()>0){
                    builder.append(",");
                }
                builder.append(cId);
            }
            params.put("classes",builder.toString());
            params.put("students","");
        }else if(selStudent.size() > 0){
            String key0 = (String) selStudent.keySet().toArray()[0];
            params.put("classId",selStudent.get(key0));

            StringBuilder cbuilder = new StringBuilder("");
            for(Map.Entry<String, String> entry:selStudent.entrySet()){
                if(cbuilder.length()>0){
                    cbuilder.append(",");
                }
                cbuilder.append(entry.getKey());
            }
            params.put("students",cbuilder.toString());
            params.put("classes","");
        }

        return params;
    }

    public PutHWClassAdapter(@Nullable List<MultiItemEntity> data) {
        super(data);
        addItemType(LEVEL_class, R.layout.item_class_sel);
        addItemType(LEVEL_student, R.layout.item_put_hw_detail_class_member);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        CommonExpandableItem commonExpandableItem = (CommonExpandableItem)item;
        switch (commonExpandableItem.getLevel()){
            case LEVEL_class:
                renderClassItem(helper,commonExpandableItem);
                break;
            case LEVEL_student:
                renderStudentItem(helper,commonExpandableItem);
                break;
        }
    }

    //渲染班级
    private void renderClassItem(BaseViewHolder helper, CommonExpandableItem item){
        BelongClass belongClass = (BelongClass)item.getData();

        TextView tvName = helper.getView(R.id.tv_class);
        tvName.setText(belongClass.getClassName());

        TextView tvNumb = helper.getView(R.id.tv_total);
        tvNumb.setText("共10人");

        if(item.isExpanded()){
            CompoundDrawableUtil.setCompoundDrawable(mContext,tvNumb,0,0,R.mipmap.arrow_up_grey,0);
        }else{
            CompoundDrawableUtil.setCompoundDrawable(mContext,tvNumb,0,0,R.mipmap.arrow_down_grey,0);
        }

        if(selClass.containsKey(belongClass.getClassId())){
            helper.setImageResource(R.id.iv_cls_sel,R.mipmap.ic_dot_hook);
        }else{
            helper.setImageResource(R.id.iv_cls_sel,R.mipmap.ic_dot_unhook);
        }

        helper.addOnClickListener(R.id.iv_cls_sel);
    }

    //渲染学生
    private void renderStudentItem(BaseViewHolder helper, CommonExpandableItem item){
        PutStudent studentItem = (PutStudent)(item.getData());

        TextView rbStudent = helper.getView(R.id.tv_std);
        rbStudent.setText(studentItem.getRealName());

        if(studentItem.getIsput() > 0){
            rbStudent.setTextColor(ContextCompat.getColor(mContext,R.color.text_999));
            helper.setImageResource(R.id.iv_std_sel,R.mipmap.ic_dot_hook);
        }else if(selStudent.containsKey(studentItem.getId())){
            rbStudent.setTextColor(ContextCompat.getColor(mContext,R.color.text_333));
            helper.setImageResource(R.id.iv_std_sel,R.mipmap.ic_dot_hook);
        }else{
            rbStudent.setTextColor(ContextCompat.getColor(mContext,R.color.text_333));
            helper.setImageResource(R.id.iv_std_sel,R.mipmap.ic_dot_unhook);
        }

        helper.addOnClickListener(R.id.iv_std_sel);
    }
}

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
package com.example.df.zhiyun.app;

/**
 * ================================================
 * 放置 AndroidEventBus 的 Tag, 便于检索
 * Arms 核心库现在并不会依赖某个 EventBus, 要想使用 EventBus, 还请在项目中自行依赖对应的 EventBus
 * 现在支持两种 EventBus, greenrobot 的 EventBus 和畅销书 《Android源码设计模式解析与实战》的作者 何红辉 所作的 AndroidEventBus
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#3.5">EventBusTags wiki 官方文档</a>
 * Created by JessYan on 8/30/2016 16:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface EventBusTags {
    String UPDATE_USERINFO = "update_unserinfo";   //用户头像
    String UPDATE_EXER_NUMB = "update_exer_numb";
    String UPDATE_PERSON_CENTER = "update_person_center";  //用户消息数
    String REQUEST_PERSON_CENTER = "request_person_center";
    String UPDATE_QUESTION_VIEWPAGER = "update_question_viewpager";
    String UPDATE_PUT_REMOVE_HW = "update_put_remove_hw";  //刷新布置作业
    String UPDATE_HW_LIST = "update_hw_list";  //刷新提交作业后的最新作业列表
    String UPDATE_FRAGMENT = "update_fragment";  //刷新首页fragment
    String UPDATE_SUB_QUESTION_VIEWPAGER = "update_sub_question_viewpager";
}

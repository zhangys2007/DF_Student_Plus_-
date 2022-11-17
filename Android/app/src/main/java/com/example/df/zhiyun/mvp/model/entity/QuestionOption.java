package com.example.df.zhiyun.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Auto-generated: 2019-07-26 12:25:45
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class QuestionOption implements Parcelable {
    private String optionContent;
    private String option;

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }
    public String getOptionContent() {
        return optionContent;
    }

    public void setOption(String option) {
        this.option = option;
    }
    public String getOption() {
        return option;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.optionContent);
        dest.writeString(this.option);
    }

    public QuestionOption() {
    }

    protected QuestionOption(Parcel in) {
        this.optionContent = in.readString();
        this.option = in.readString();
    }

    public static final Parcelable.Creator<QuestionOption> CREATOR = new Parcelable.Creator<QuestionOption>() {
        @Override
        public QuestionOption createFromParcel(Parcel source) {
            return new QuestionOption(source);
        }

        @Override
        public QuestionOption[] newArray(int size) {
            return new QuestionOption[size];
        }
    };
}

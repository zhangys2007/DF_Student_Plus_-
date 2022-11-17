package com.example.df.zhiyun.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class VersionItem implements Parcelable {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    public VersionItem() {
    }

    protected VersionItem(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<VersionItem> CREATOR = new Parcelable.Creator<VersionItem>() {
        @Override
        public VersionItem createFromParcel(Parcel source) {
            return new VersionItem(source);
        }

        @Override
        public VersionItem[] newArray(int size) {
            return new VersionItem[size];
        }
    };
}

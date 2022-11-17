package com.example.df.zhiyun.mvp.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class VersionPublisher implements Parcelable {
    private String id;
    private String dictKey;
    private String dictName;
    private String name;
    private List<VersionItem> list;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public List<VersionItem> getList() {
        return list;
    }

    public void setList(List<VersionItem> list) {
        this.list = list;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.dictKey);
        dest.writeString(this.dictName);
        dest.writeString(this.name);
        dest.writeTypedList(this.list);
    }

    public VersionPublisher() {
    }

    protected VersionPublisher(Parcel in) {
        this.id = in.readString();
        this.dictKey = in.readString();
        this.dictName = in.readString();
        this.name = in.readString();
        this.list = in.createTypedArrayList(VersionItem.CREATOR);
    }

    public static final Parcelable.Creator<VersionPublisher> CREATOR = new Parcelable.Creator<VersionPublisher>() {
        @Override
        public VersionPublisher createFromParcel(Parcel source) {
            return new VersionPublisher(source);
        }

        @Override
        public VersionPublisher[] newArray(int size) {
            return new VersionPublisher[size];
        }
    };
}

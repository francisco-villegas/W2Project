package com.example.francisco.w2project;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kuliza-195 on 12/23/16.
 */


public class SubTitle implements Parcelable {

    private String name;
    private int img;

    public SubTitle(String name, int img) {
        this.name = name;
        this.img = img;
    }

    protected SubTitle(Parcel in) {
        name = in.readString();
        img = in.readInt();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public static final Creator<SubTitle> CREATOR = new Creator<SubTitle>() {
        @Override
        public SubTitle createFromParcel(Parcel in) {
            return new SubTitle(in);
        }

        @Override
        public SubTitle[] newArray(int size) {
            return new SubTitle[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(img);
    }
}

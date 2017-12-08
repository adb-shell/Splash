package com.karthik.splash.Models.PhotosLists;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by karthikrk on 20/12/15.
 */
public class PhotoLinks implements Parcelable {
    @SerializedName("self")
    public String self;
    @SerializedName("html")
    public String html;
    @SerializedName("download")
    public String download;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.self);
        dest.writeString(this.html);
        dest.writeString(this.download);
    }

    public PhotoLinks() {
    }

    protected PhotoLinks(Parcel in) {
        this.self = in.readString();
        this.html = in.readString();
        this.download = in.readString();
    }

    public static final Parcelable.Creator<PhotoLinks> CREATOR = new Parcelable.Creator<PhotoLinks>() {
        @Override
        public PhotoLinks createFromParcel(Parcel source) {
            return new PhotoLinks(source);
        }

        @Override
        public PhotoLinks[] newArray(int size) {
            return new PhotoLinks[size];
        }
    };
}

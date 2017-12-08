package com.karthik.splash.Models.PhotosLists;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by karthikrk on 20/02/16.
 */
public class Categories implements Parcelable {

    @SerializedName("id")
    public String id;

    @SerializedName("title")
    public String title;

    @SerializedName("photo_count")
    public String photo_count;

    @SerializedName("links")
    public Links links;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.photo_count);
        dest.writeParcelable(this.links, flags);
    }

    public Categories() {
    }

    protected Categories(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.photo_count = in.readString();
        this.links = in.readParcelable(Links.class.getClassLoader());
    }

    public static final Parcelable.Creator<Categories> CREATOR = new Parcelable.Creator<Categories>() {
        @Override
        public Categories createFromParcel(Parcel source) {
            return new Categories(source);
        }

        @Override
        public Categories[] newArray(int size) {
            return new Categories[size];
        }
    };
}

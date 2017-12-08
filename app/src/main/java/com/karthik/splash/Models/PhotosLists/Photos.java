package com.karthik.splash.Models.PhotosLists;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by karthikrk on 20/12/15.
 */
public class Photos implements Parcelable {
    @SerializedName("id")
    public String id;

    @SerializedName("created_at")
    public String createdTime;

    @SerializedName("height")
    public String height;

    @SerializedName("width")
    public String width;

    @SerializedName("color")
    public String color;

    @SerializedName("likes")
    public String numberLikes;

    @SerializedName("user")
    public User user;

    @SerializedName("urls")
    public Urls urls;

    @SerializedName("categories")
    public ArrayList<Categories> categories = new ArrayList<>();

    @SerializedName("links")
    public PhotoLinks links;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.createdTime);
        dest.writeString(this.height);
        dest.writeString(this.width);
        dest.writeString(this.color);
        dest.writeString(this.numberLikes);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.urls, flags);
        dest.writeTypedList(this.categories);
        dest.writeParcelable(this.links, flags);
    }

    public Photos() {
    }

    protected Photos(Parcel in) {
        this.id = in.readString();
        this.createdTime = in.readString();
        this.height = in.readString();
        this.width = in.readString();
        this.color = in.readString();
        this.numberLikes = in.readString();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.urls = in.readParcelable(Urls.class.getClassLoader());
        this.categories = in.createTypedArrayList(Categories.CREATOR);
        this.links = in.readParcelable(PhotoLinks.class.getClassLoader());
    }

    public static final Parcelable.Creator<Photos> CREATOR = new Parcelable.Creator<Photos>() {
        @Override
        public Photos createFromParcel(Parcel source) {
            return new Photos(source);
        }

        @Override
        public Photos[] newArray(int size) {
            return new Photos[size];
        }
    };
}

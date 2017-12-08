package com.karthik.splash.Models.PhotosLists;

import com.google.gson.annotations.SerializedName;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by karthikrk on 20/12/15.
 */
public class User implements Parcelable {
    @SerializedName("id")
    public String id;
    @SerializedName("username")
    public String username;
    @SerializedName("total_photos")
    public String totalPhotos;
    @SerializedName("total_collections")
    public String totalCollection;
    @SerializedName("name")
    public String name;
    @SerializedName("links")
    public Links links;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.username);
        dest.writeString(this.totalPhotos);
        dest.writeString(this.totalCollection);
        dest.writeString(this.name);
        dest.writeParcelable(this.links, flags);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.username = in.readString();
        this.totalPhotos = in.readString();
        this.totalCollection = in.readString();
        this.name = in.readString();
        this.links = in.readParcelable(Links.class.getClassLoader());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}

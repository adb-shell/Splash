package com.karthik.splash.Models.UserProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.karthik.splash.Models.PhotosLists.Links;

/**
 * Created by karthikrk on 30/11/17.
 */

public class Profile {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("first_name")
    @Expose
    public String firstName;
    @SerializedName("last_name")
    @Expose
    public String lastName;
    @SerializedName("twitter_username")
    @Expose
    public String twitterUsername;
    @SerializedName("portfolio_url")
    @Expose
    public Object portfolioUrl;
    @SerializedName("bio")
    @Expose
    public String bio;
    @SerializedName("location")
    @Expose
    public Object location;
    @SerializedName("total_likes")
    @Expose
    public Integer totalLikes;
    @SerializedName("total_photos")
    @Expose
    public Integer totalPhotos;
    @SerializedName("total_collections")
    @Expose
    public Integer totalCollections;
    @SerializedName("followed_by_user")
    @Expose
    public Boolean followedByUser;
    @SerializedName("downloads")
    @Expose
    public Integer downloads;
    @SerializedName("uploads_remaining")
    @Expose
    public Integer uploadsRemaining;
    @SerializedName("instagram_username")
    @Expose
    public String instagramUsername;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("links")
    @Expose
    public Links links;
}

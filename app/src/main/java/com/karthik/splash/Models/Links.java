package com.karthik.splash.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by karthikrk on 20/12/15.
 */
public class Links {
    @SerializedName("self")
    public String self;
    @SerializedName("html")
    public String html;
    @SerializedName("photos")
    public String photos;
    @SerializedName("likes")
    public String likes;
}

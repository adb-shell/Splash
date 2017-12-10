package com.karthik.splash.Models.LikePhoto;


import com.google.gson.annotations.SerializedName;

import com.karthik.splash.Models.PhotosLists.Links;


public class Photo{

	@SerializedName("urls")
	public Urls urls;

	@SerializedName("color")
	public String color;

	@SerializedName("width")
	public int width;

	@SerializedName("description")
	public String description;

	@SerializedName("links")
	public Links links;

	@SerializedName("id")
	public String id;

	@SerializedName("liked_by_user")
	public boolean likedByUser;

	@SerializedName("height")
	public int height;

	@SerializedName("likes")
	public int likes;
}
package com.karthik.splash.Models.LikePhoto;


import com.google.gson.annotations.SerializedName;


public class LikeResponse{

	@SerializedName("photo")
	public Photo photo;

	@SerializedName("user")
	public User user;
}
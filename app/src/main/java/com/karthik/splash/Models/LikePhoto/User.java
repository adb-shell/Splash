package com.karthik.splash.Models.LikePhoto;


import com.google.gson.annotations.SerializedName;

import com.karthik.splash.Models.PhotosLists.Links;


public class User{

	@SerializedName("name")
	private String name;

	@SerializedName("links")
	private Links links;

	@SerializedName("id")
	private String id;

	@SerializedName("username")
	private String username;
}
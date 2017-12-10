package com.karthik.splash.Models.PhotoDetail;


import com.google.gson.annotations.SerializedName;


public class CategoriesItem{

	@SerializedName("photo_count")
	private int photoCount;

	@SerializedName("links")
	private Links links;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;
}
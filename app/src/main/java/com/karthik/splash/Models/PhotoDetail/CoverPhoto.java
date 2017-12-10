package com.karthik.splash.Models.PhotoDetail;

import java.util.List;

import com.google.gson.annotations.SerializedName;


public class CoverPhoto{

	@SerializedName("urls")
	private Urls urls;

	@SerializedName("color")
	private String color;

	@SerializedName("width")
	private int width;

	@SerializedName("description")
	private String description;

	@SerializedName("links")
	private Links links;

	@SerializedName("id")
	private String id;

	@SerializedName("categories")
	private List<CategoriesItem> categories;

	@SerializedName("liked_by_user")
	private boolean likedByUser;

	@SerializedName("user")
	private User user;

	@SerializedName("height")
	private int height;

	@SerializedName("likes")
	private int likes;
}
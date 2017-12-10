package com.karthik.splash.Models.PhotoDetail;


import com.google.gson.annotations.SerializedName;


public class CurrentUserCollectionsItem{

	@SerializedName("cover_photo")
	private CoverPhoto coverPhoto;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("curated")
	private boolean curated;

	@SerializedName("links")
	private Links links;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("published_at")
	private String publishedAt;

	@SerializedName("user")
	private User user;
}
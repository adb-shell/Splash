package com.karthik.splash.Models.PhotoDetail;


import com.google.gson.annotations.SerializedName;


public class Links{

	@SerializedName("portfolio")
	private String portfolio;

	@SerializedName("self")
	private String self;

	@SerializedName("html")
	private String html;

	@SerializedName("photos")
	private String photos;

	@SerializedName("likes")
	private String likes;
}
package com.karthik.splash.Models.PhotoDetail;


import com.google.gson.annotations.SerializedName;


public class Urls{

	@SerializedName("small")
	private String small;

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("raw")
	private String raw;

	@SerializedName("regular")
	private String regular;

	@SerializedName("full")
	private String full;
}
package com.karthik.splash.Models.PhotoDetail;


import com.google.gson.annotations.SerializedName;


public class User{

	@SerializedName("total_photos")
	public int totalPhotos;

	@SerializedName("twitter_username")
	public String twitterUsername;

	@SerializedName("last_name")
	public String lastName;

	@SerializedName("bio")
	public String bio;

	@SerializedName("total_likes")
	public int totalLikes;

	@SerializedName("portfolio_url")
	public String portfolioUrl;

	@SerializedName("profile_image")
	public ProfileImage profileImage;

	@SerializedName("name")
	public String name;

	@SerializedName("location")
	public String location;

	@SerializedName("total_collections")
	public int totalCollections;

	@SerializedName("links")
	public Links links;

	@SerializedName("id")
	public String id;

	@SerializedName("first_name")
	public String firstName;

	@SerializedName("username")
	public String username;
}
package com.karthik.splash.Models.PhotoDetail;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class PhotoDetailInfo {

	@SerializedName("current_user_collections")
	public List<CurrentUserCollectionsItem> currentUserCollections;

	@SerializedName("color")
	public String color;

	@SerializedName("created_at")
	public String createdAt;

	@SerializedName("description")
	public String description;

	@SerializedName("liked_by_user")
	public boolean likedByUser;

	@SerializedName("urls")
	public Urls urls;

	@SerializedName("updated_at")
	public String updatedAt;

	@SerializedName("downloads")
	public int downloads;

	@SerializedName("width")
	public int width;

	@SerializedName("location")
	public Location location;

	@SerializedName("links")
	public Links links;

	@SerializedName("id")
	public String id;

	@SerializedName("categories")
	public List<CategoriesItem> categories;

	@SerializedName("user")
	public User user;

	@SerializedName("height")
	public int height;

	@SerializedName("likes")
	public int likes;

	@SerializedName("exif")
	public Exif exif;
}
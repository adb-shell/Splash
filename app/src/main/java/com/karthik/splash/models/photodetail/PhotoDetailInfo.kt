package com.karthik.splash.models.photodetail

import com.google.gson.annotations.SerializedName


data class PhotoDetailInfo(@SerializedName("current_user_collections")
                           var currentUserCollections: List<CurrentUserCollectionsItem>? = null,
                           @SerializedName("color")
                           var color: String? = null,
                           @SerializedName("created_at")
                           var createdAt: String? = null,
                           @SerializedName("description")
                           var description: String? = null,
                           @SerializedName("liked_by_user")
                           var likedByUser: Boolean = false,
                           @SerializedName("urls")
                           var urls: Urls? = null,
                           @SerializedName("updated_at")
                           var updatedAt: String? = null,
                           @SerializedName("downloads")
                           var downloads: Int = 0,
                           @SerializedName("width")
                           var width: Int = 0,
                           @SerializedName("location")
                           var location: Location? = null,
                           @SerializedName("links")
                           var links: Links? = null,
                           @SerializedName("id")
                           var id: String? = null,
                           @SerializedName("categories")
                           var categories: List<CategoriesItem>? = null,
                           @SerializedName("user")
                           var user: User? = null,
                           @SerializedName("height")
                           var height: Int = 0,
                           @SerializedName("likes")
                           var likes: Int = 0,
                           @SerializedName("exif")
                           var exif: Exif? = null)
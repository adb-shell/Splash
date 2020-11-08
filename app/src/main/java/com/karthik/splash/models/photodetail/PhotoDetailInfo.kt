package com.karthik.splash.models.photodetail

import com.google.gson.annotations.SerializedName


data class PhotoDetailInfo(
        @SerializedName("color")
        var color: String? = null,
        @SerializedName("created_at")
        var createdAt: String? = null,
        @SerializedName("description")
        var description: String? = null,
        @SerializedName("liked_by_user")
        var likedByUser: Boolean = false,
        @SerializedName("updated_at")
        var updatedAt: String? = null,
        @SerializedName("downloads")
        var downloads: Int = 0,
        @SerializedName("width")
        var width: Int = 0,
        @SerializedName("location")
        var location: Location? = null,
        @SerializedName("id")
        var id: String? = null,
        @SerializedName("user")
        var user: User? = null,
        @SerializedName("height")
        var height: Int = 0,
        @SerializedName("likes")
        var likes: Int = 0
)

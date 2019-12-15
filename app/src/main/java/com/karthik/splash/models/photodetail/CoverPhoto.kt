package com.karthik.splash.models.photodetail

import com.google.gson.annotations.SerializedName


data class CoverPhoto(@SerializedName("urls") private val urls: Urls? = null,
                 @SerializedName("color") private val color: String? = null,
                @SerializedName("width") private val width: Int = 0,
                @SerializedName("description") private val description: String? = null,
                @SerializedName("links") private val links: Links? = null,
                @SerializedName("id") private val id: String? = null,
                @SerializedName("categories") private val categories: List<CategoriesItem>? = null,
                @SerializedName("liked_by_user") private val likedByUser: Boolean = false,
                @SerializedName("user") private val user: User? = null,
                @SerializedName("height") private val height: Int = 0,
                @SerializedName("likes") private val likes: Int = 0)
package com.karthik.splash.models.photodetail


import com.google.gson.annotations.SerializedName


data class CategoriesItem(
        @SerializedName("photocount") private val photoCount: Int = 0,
        @SerializedName("links") private val links: Links? = null,
        @SerializedName("id") private val id: Int = 0,
        @SerializedName("title") private val title: String? = null
)
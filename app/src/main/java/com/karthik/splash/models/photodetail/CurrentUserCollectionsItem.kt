package com.karthik.splash.models.photodetail


import com.google.gson.annotations.SerializedName


data class CurrentUserCollectionsItem(
        @SerializedName("cover_photo") private val coverPhoto: CoverPhoto? = null,
        @SerializedName("updated_at") private val updatedAt: String? = null,
        @SerializedName("curated") private val curated: Boolean = false,
        @SerializedName("links") private val links: Links? = null,
        @SerializedName("id") private val id: Int = 0,
        @SerializedName("title") private val title: String? = null,
        @SerializedName("published_at") private val publishedAt: String? = null,
        @SerializedName("user") private val user: User? = null
)
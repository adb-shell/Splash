package com.karthik.splash.models.likephoto


import com.google.gson.annotations.SerializedName

import com.karthik.splash.models.photoslists.Links


data class User(
        @SerializedName("name") private val name: String? = null,
        @SerializedName("links") private val links: Links? = null,
        @SerializedName("id") private val id: String? = null,
        @SerializedName("username") private val username: String? = null
)
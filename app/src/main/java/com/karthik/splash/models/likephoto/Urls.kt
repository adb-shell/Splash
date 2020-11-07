package com.karthik.splash.models.likephoto


import com.google.gson.annotations.SerializedName

data class Urls(
        @SerializedName("small") private val small: String? = null,
        @SerializedName("thumb") private val thumb: String? = null,
        @SerializedName("raw") private val raw: String? = null,
        @SerializedName("regular") private val regular: String? = null,
        @SerializedName("full") private val full: String? = null
)

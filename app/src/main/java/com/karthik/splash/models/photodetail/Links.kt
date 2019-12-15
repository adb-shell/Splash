package com.karthik.splash.models.photodetail


import com.google.gson.annotations.SerializedName


class Links(@SerializedName("portfolio")
            private val portfolio: String? = null,
            @SerializedName("self")
            private val self: String? = null,
            @SerializedName("html")
            private val html: String? = null,
            @SerializedName("photos")
            private val photos: String? = null,
            @SerializedName("likes")
            private val likes: String? = null)
package com.karthik.network.photodetailscreen.models


import com.google.gson.annotations.SerializedName


data class LikeResponse(
        @SerializedName("photo") var photo: Photo? = null,
)

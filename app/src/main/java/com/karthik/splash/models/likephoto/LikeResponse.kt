package com.karthik.splash.models.likephoto


import com.google.gson.annotations.SerializedName


data class LikeResponse(@SerializedName("photo") var photo: Photo? = null,
                        @SerializedName("user") var user: User? = null)
package com.karthik.splash.models.photodetail


import com.google.gson.annotations.SerializedName


data class Exif(@SerializedName("exposure_time")
                private val exposureTime: String? = null,
                @SerializedName("aperture")
                private val aperture: String? = null,
                @SerializedName("focal_length")
                private val focalLength: String? = null,
                @SerializedName("iso")
                private val iso: Int = 0,
                @SerializedName("model")
                private val model: String? = null,
                @SerializedName("make")
                private val make: String? = null)
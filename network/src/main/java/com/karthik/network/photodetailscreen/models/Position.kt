package com.karthik.network.photodetailscreen.models


import com.google.gson.annotations.SerializedName


data class Position(
        @SerializedName("latitude")
        var latitude: Double = 0.toDouble(),
        @SerializedName("longitude")
        var longitude: Double = 0.toDouble()
)
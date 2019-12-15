package com.karthik.splash.models.photodetail


import com.google.gson.annotations.SerializedName


data class Location(@SerializedName("country")
                    var country: String? = null,
                    @SerializedName("city")
                    var city: String? = null,
                    @SerializedName("position")
                    var position: Position? = null)
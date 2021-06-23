package com.karthik.network.home.bottomliketab.models


import com.google.gson.annotations.SerializedName

/**
 * Created by karthikrk on 20/12/15.
 */

data class PhotoLinks(
        @SerializedName("self")
        var self: String? = null,
        @SerializedName("html")
        var html: String? = null,
        @SerializedName("download")
        var download: String? = null
)

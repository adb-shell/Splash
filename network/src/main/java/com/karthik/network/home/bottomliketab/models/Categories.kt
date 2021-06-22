package com.karthik.network.home.bottomliketab.models

import com.google.gson.annotations.SerializedName

/**
 * Created by karthikrk on 20/02/16.
 */

data class Categories(
        @SerializedName("id")
        var id: String? = null,
        @SerializedName("title")
        var title: String? = null,
        @SerializedName("photo_count")
        var photocount: String? = null,
        @SerializedName("links")
        var links: Links? = null
)

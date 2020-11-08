package com.karthik.splash.models.photoslists

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by karthikrk on 20/12/15.
 */
@Parcelize
data class PhotoLinks(
        @SerializedName("self")
        var self: String? = null,
        @SerializedName("html")
        var html: String? = null,
        @SerializedName("download")
        var download: String? = null
) : Parcelable

package com.karthik.splash.models.PhotosLists

import com.google.gson.annotations.SerializedName

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by karthikrk on 20/12/15.
 */
@Parcelize
data class User(@SerializedName("id")
                var id: String? = null,
                @SerializedName("username")
                var username: String? = null,
                @SerializedName("total_photos")
                var totalPhotos: String? = null,
                @SerializedName("total_collections")
                var totalCollection: String? = null,
                @SerializedName("name")
                var name: String? = null,
                @SerializedName("links")
                var links: Links? = null) : Parcelable

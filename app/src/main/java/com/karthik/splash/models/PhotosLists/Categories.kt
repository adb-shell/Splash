package com.karthik.splash.models.PhotosLists

import com.google.gson.annotations.SerializedName

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by karthikrk on 20/02/16.
 */
@Parcelize
data class Categories(@SerializedName("id")
                      var id: String? = null,
                      @SerializedName("title")
                      var title: String? = null,
                      @SerializedName("photo_count")
                      var photo_count: String? = null,
                      @SerializedName("links")
                      var links: Links? = null):Parcelable

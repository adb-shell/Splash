package com.karthik.splash.models.PhotosLists

import com.google.gson.annotations.SerializedName

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

import java.util.ArrayList

/**
 * Created by karthikrk on 20/12/15.
 */
@Parcelize
data class Photos(@SerializedName("id")
                  var id: String,
                  @SerializedName("created_at")
                  var createdTime: String? = null,
                  @SerializedName("height")
                  var height: String? = null,
                  @SerializedName("width")
                  var width: String? = null,
                  @SerializedName("color")
                  var color: String? = null,
                  @SerializedName("likes")
                  var numberLikes: String? = null,
                  @SerializedName("user")
                  var user: User? = null,
                  @SerializedName("urls")
                  var urls: Urls? = null,
                  @SerializedName("categories")
                  var categories: ArrayList<Categories>? = ArrayList(),
                  @SerializedName("links")
                  var links: PhotoLinks? = null):Parcelable

package com.karthik.splash.models.PhotosLists

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by karthikrk on 20/12/15.
 */
@Parcelize
data class Urls(var full: String? = null,
                var regular: String? = null,
                var small: String? = null,
                var thumb: String? = null) : Parcelable

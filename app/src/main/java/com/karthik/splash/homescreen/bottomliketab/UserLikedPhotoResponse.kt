package com.karthik.splash.homescreen.bottomliketab

import com.karthik.splash.models.photoslists.Photos
import java.util.ArrayList

sealed class UserLikedPhotoResponse {
    data class UserLikedPhoto(val photos: ArrayList<Photos>):UserLikedPhotoResponse()
    data class UserLikedPhotoErrorState(val e: Throwable):UserLikedPhotoResponse()
}

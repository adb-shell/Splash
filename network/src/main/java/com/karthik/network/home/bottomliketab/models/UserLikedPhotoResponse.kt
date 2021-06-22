package com.karthik.network.home.bottomliketab.models


import java.util.ArrayList

sealed class UserLikedPhotoResponse {
    data class UserLikedPhoto(val photos: ArrayList<Photos>):UserLikedPhotoResponse()
    data class UserLikedPhotoErrorState(val e: Throwable):UserLikedPhotoResponse()
}

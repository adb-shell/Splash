package com.karthik.splash.homescreen.bottomliketab.network

import com.karthik.splash.models.photoslists.Photos
import java.util.ArrayList

interface IBottomLikeTabRepository {
    fun getUserLikedPhotos(
            successhander: (ArrayList<Photos>) -> Unit,
            errorhandler: (e: Throwable) -> Unit
    )

    fun clearResources()
}
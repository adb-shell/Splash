package com.karthik.splash.photodetailscreen

import com.karthik.splash.models.likephoto.LikeResponse
import com.karthik.splash.models.photodetail.PhotoDetailInfo
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import retrofit2.Retrofit

class PhotoDetailScreenNetworkLayer(retrofit: Retrofit) {
    private val photoService: PhotoService

    init {
        photoService = retrofit.create(PhotoService::class.java)
    }

    fun getPhotoInfo(id:String): Single<PhotoDetailInfo> = photoService.getPhotoInfo(id)
            .observeOn(AndroidSchedulers.mainThread())

    fun likePhoto(id:String):Single<LikeResponse> = photoService.likePhoto(id)
            .observeOn(AndroidSchedulers.mainThread())
}
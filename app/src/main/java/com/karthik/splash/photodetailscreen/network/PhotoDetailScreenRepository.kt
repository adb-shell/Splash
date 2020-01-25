package com.karthik.splash.photodetailscreen.network

import androidx.lifecycle.MutableLiveData
import com.karthik.splash.models.likephoto.LikeResponse
import com.karthik.splash.models.photodetail.PhotoDetailInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class PhotoDetailScreenRepository(retrofit: Retrofit) {
    private val photoService: PhotoService
    private val disposable = CompositeDisposable()
    val photoDetailsNetworkState:MutableLiveData<PhotoDetailsNetworkState> = MutableLiveData()

    init {
        photoService = retrofit.create(PhotoService::class.java)
    }

    fun getPhotoInfo(id:String,
                     successhander:(detail: PhotoDetailInfo)->Unit){
        disposable.add(photoService.getPhotoInfo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object:DisposableSingleObserver<PhotoDetailInfo>(){
                    override fun onSuccess(detail: PhotoDetailInfo){
                        photoDetailsNetworkState.postValue(PhotoDetailsNetworkState.PhotoDetailsNetworkLoadSuccess)
                        successhander(detail)
                    }
                    override fun onError(e: Throwable) =
                            photoDetailsNetworkState.postValue(PhotoDetailsNetworkState.PhotoDetailsNetworkLoadError(e))
                }))
    }

    fun likePhoto(id:String,
                  successhander: (likeResponse: LikeResponse) -> Unit){
        disposable.add(photoService.likePhoto(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object:DisposableSingleObserver<LikeResponse>(){
                    override fun onSuccess(likeResponse: LikeResponse) {
                        photoDetailsNetworkState.postValue(PhotoDetailsNetworkState.PhotoDetailsNetworkLoadSuccess)
                        successhander(likeResponse)
                    }
                    override fun onError(e: Throwable)=
                            photoDetailsNetworkState.postValue(PhotoDetailsNetworkState.PhotoLikeNetworkLoadError(e))
                }))
    }


    fun clearResources(){
        if(!disposable.isDisposed){
            disposable.dispose()
        }
    }
}
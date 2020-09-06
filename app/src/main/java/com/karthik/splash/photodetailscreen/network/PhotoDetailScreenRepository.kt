package com.karthik.splash.photodetailscreen.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.karthik.splash.models.likephoto.LikeResponse
import com.karthik.splash.models.photodetail.PhotoDetailInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class PhotoDetailScreenRepository(retrofit: Retrofit) {
    private val photoService: PhotoService = retrofit.create(PhotoService::class.java)
    private val disposable = CompositeDisposable()
    private val internalState: MutableLiveData<PhotoDetailsNetworkState> = MutableLiveData()
    val photoDetailsNetworkState: LiveData<PhotoDetailsNetworkState> = internalState

    fun getPhotoInfo(id:String,
                     successhander:(detail: PhotoDetailInfo)->Unit){
        disposable.add(photoService.getPhotoInfo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object:DisposableSingleObserver<PhotoDetailInfo>(){
                    override fun onSuccess(detail: PhotoDetailInfo){
                        internalState.postValue(PhotoDetailsNetworkState.PhotoDetailsNetworkLoadSuccess)
                        successhander(detail)
                    }
                    override fun onError(e: Throwable) =
                        internalState.postValue(PhotoDetailsNetworkState.PhotoDetailsNetworkLoadError(e))
                }))
    }

    fun likePhoto(id:String,
                  successhander: (likeResponse: LikeResponse) -> Unit){
        disposable.add(photoService.likePhoto(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object:DisposableSingleObserver<LikeResponse>(){
                    override fun onSuccess(likeResponse: LikeResponse) {
                        internalState.postValue(PhotoDetailsNetworkState.PhotoDetailsNetworkLoadSuccess)
                        successhander(likeResponse)
                    }
                    override fun onError(e: Throwable)=
                        internalState.postValue(PhotoDetailsNetworkState.PhotoLikeNetworkLoadError(e))
                }))
    }


    fun clearResources(){
        if(!disposable.isDisposed){
            disposable.dispose()
        }
    }
}
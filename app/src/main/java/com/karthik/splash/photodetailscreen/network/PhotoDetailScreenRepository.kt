package com.karthik.splash.photodetailscreen.network

import androidx.lifecycle.MutableLiveData
import com.karthik.splash.models.likephoto.LikeResponse
import com.karthik.splash.models.photodetail.PhotoDetailInfo
import com.karthik.splash.photodetailscreen.IPhotoDetailScreenRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class PhotoDetailScreenRepository(private val photoService: PhotoService): IPhotoDetailScreenRepository {
    private val disposable = CompositeDisposable()
    private val internalState: MutableLiveData<PhotoDetailsNetworkState> = MutableLiveData()

    override fun getPhotoDetailNetworkState() = internalState

    override fun getPhotoInfo(id:String,
                              successhander:(detail: PhotoDetailInfo)->Unit,
                              errorhandler:(e: Throwable)->Unit){
        disposable.add(photoService.getPhotoInfo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object:DisposableSingleObserver<PhotoDetailInfo>(){
                    override fun onSuccess(detail: PhotoDetailInfo){
                        internalState.postValue(PhotoDetailsNetworkState.PhotoDetailsNetworkLoadSuccess)
                        successhander(detail)
                    }
                    override fun onError(e: Throwable) {
                        internalState.postValue(PhotoDetailsNetworkState.PhotoDetailsNetworkLoadError(e))
                        errorhandler(e)
                    }
                }))
    }

    override fun likePhoto(id:String,
                           successhander: (likeResponse: LikeResponse) -> Unit,
                           errorhandler:(e: Throwable)->Unit){
        disposable.add(photoService.likePhoto(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object:DisposableSingleObserver<LikeResponse>(){
                    override fun onSuccess(likeResponse: LikeResponse) {
                        internalState.postValue(PhotoDetailsNetworkState.PhotoDetailsNetworkLoadSuccess)
                        successhander(likeResponse)
                    }
                    override fun onError(e: Throwable){
                        internalState.postValue(PhotoDetailsNetworkState.PhotoLikeNetworkLoadError(e))
                        errorhandler(e)
                    }
                }))
    }


    override fun clearResources(){
        if(!disposable.isDisposed){
            disposable.dispose()
        }
    }
}
package com.karthik.splash.RestServices.NetworkLayer;

import com.karthik.splash.Models.PhotoDetail.PhotoDetailInfo;
import com.karthik.splash.RestServices.Services.PhotoService;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by karthikrk on 09/12/17.
 */

public class PhotoNetworkLayer {
    private PhotoService photoService;

    @Inject
    public PhotoNetworkLayer(Retrofit retrofit){
        photoService = retrofit.create(PhotoService.class);
    }

    public Single<PhotoDetailInfo> getPhotoInfo(String id){
       return photoService.getPhotoInfo(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

package com.karthik.splash.Modules;

import com.karthik.splash.Contracts.PhotoDetailContract;
import com.karthik.splash.Presenters.PhotoDetailPresenter;
import com.karthik.splash.RestServices.NetworkLayer.PhotoNetworkLayer;
import com.karthik.splash.Storage.Cache;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by karthikrk on 09/12/17.
 */
@Module
public class PhotoDetailModule {

    private PhotoDetailContract.PhotoDetailView view;

    public PhotoDetailModule(PhotoDetailContract.PhotoDetailView view){
        this.view =view;
    }

    @Provides
    PhotoDetailContract.PhotoDetailView providesView(){
        return view;
    }

    @Provides
    PhotoNetworkLayer providesPhotoInfo(Retrofit retrofit){
        return new PhotoNetworkLayer(retrofit);
    }

    @Provides
    PhotoDetailContract.PhotoDetailPresenter providesPhotoDetailPresenter(PhotoDetailContract.PhotoDetailView view,
                                                                          PhotoNetworkLayer networkLayer,
                                                                          Cache cache){
        return new PhotoDetailPresenter(view,networkLayer,cache);
    }
}

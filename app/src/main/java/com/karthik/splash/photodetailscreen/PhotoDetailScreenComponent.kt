package com.karthik.splash.photodetailscreen



import dagger.Subcomponent

/**
 * Created by karthikrk on 09/12/17.
 */

@PhotoDetailScreenScope
@Subcomponent(modules = [PhotoDetailScreenModule::class])
interface PhotoDetailScreenComponent {
    fun inject(PhotoDetailScreen: PhotoDetailScreen)
}

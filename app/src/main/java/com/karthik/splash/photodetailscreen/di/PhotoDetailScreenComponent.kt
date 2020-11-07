package com.karthik.splash.photodetailscreen.di


import com.karthik.splash.photodetailscreen.PhotoDetailScreen
import dagger.Subcomponent

/**
 * Created by karthikrk on 09/12/17.
 */

@PhotoDetailScreenScope
@Subcomponent(modules = [PhotoDetailScreenModule::class])
interface PhotoDetailScreenComponent {
    fun inject(photoDetailScreen: PhotoDetailScreen)
}

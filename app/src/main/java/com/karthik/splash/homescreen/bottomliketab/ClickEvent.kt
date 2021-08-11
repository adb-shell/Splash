package com.karthik.splash.homescreen.bottomliketab

import com.karthik.network.home.bottomliketab.models.Photos

sealed class ClickEvent{
    object LoginEvent: ClickEvent()
    data class PhotoClickEvent(val photos: Photos) : ClickEvent()
}
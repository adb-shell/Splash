package com.karthik.splash.homescreen

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.karthik.splash.R

sealed class BottomNavigationScreens(
    val route: String,
    @StringRes val resourceId: Int,
    @DrawableRes val icon: Int
) {
    object Home : BottomNavigationScreens(
        route = "home",
        resourceId = R.string.title_home,
        icon = R.drawable.bottom_nav_home
    )

    object Like : BottomNavigationScreens(
        route = "like",
        resourceId = R.string.title_likes,
        icon = R.drawable.bottom_nav_like
    )

    object Settings : BottomNavigationScreens(
        route = "settings",
        resourceId = R.string.title_settings,
        icon = R.drawable.bottom_nav_settings
    )
}
